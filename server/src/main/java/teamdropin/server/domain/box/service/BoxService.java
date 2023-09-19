package teamdropin.server.domain.box.service;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamdropin.server.aws.service.S3Uploader;
import teamdropin.server.domain.box.boxImage.BoxImage;
import teamdropin.server.domain.box.boxImage.BoxImageRepository;
import teamdropin.server.domain.box.dto.BoxCreateRequestDto;
import teamdropin.server.domain.box.dto.UpdateBoxRequestDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.mapper.BoxMapper;
import teamdropin.server.domain.box.repository.BoxRepository;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoxService {


    private final BoxRepository boxRepository;
    private final BoxImageRepository boxImageRepository;
    private final BoxMapper boxMapper;
    private final S3Uploader s3Uploader;


    @Transactional(readOnly = false)
    public Long createBox(Member member, BoxCreateRequestDto boxCreateRequestDto, List<MultipartFile> multipartFileList) throws IOException {

        Box box = boxMapper.toEntity(boxCreateRequestDto);
        box.addMember(member);
        boxRepository.save(box);

        if(multipartFileList != null) {
            int maxImageCount = 5;
            if(multipartFileList.size() > 5){
                throw new BusinessLogicException(ExceptionCode.UPLOAD_IMAGE_LIMIT_EXCEEDED);
            }
            Map<String, Integer> imageInfoDto = boxCreateRequestDto.getImageInfo();
            Map<String, String> imageUrlList = s3Uploader.uploadImages(multipartFileList, "box");
            for (String key :imageInfoDto.keySet()) {
                log.info("imageInfoDto key getByte = {}", key.getBytes());
            }
            for (Map.Entry<String, String> imageUrlInfo : imageUrlList.entrySet()) {
                String newImageUrl = imageUrlInfo.getKey();
                String originalImageName = imageUrlInfo.getValue();
                log.info("originalImageName getByte = {}", new String(originalImageName.getBytes(StandardCharsets.UTF_8)));
                log.info("Normalizer originalImageName getByte = {}", Normalizer.normalize(originalImageName, Normalizer.Form.NFC).getBytes());
                log.info("Not Normalizer originalImageName getByte = {}", originalImageName.getBytes());

                originalImageName = Normalizer.normalize(originalImageName, Normalizer.Form.NFC);
                int imageIdx = imageInfoDto.get(originalImageName);
                BoxImage boxImage = new BoxImage(newImageUrl, imageIdx, box);
            }
        }
        return box.getId();
    }

    public Box getBox(Long boxId) {
        return findVerifyBox(boxId);
    }

    public Page<Box> getAllBoxes(Pageable pageable) {
        return boxRepository.findAll(pageable);
    }

    @SneakyThrows
    @Transactional(readOnly = false)
    public void deleteBox(Long boxId) {
        Box box = findVerifyBox(boxId);
        List<BoxImage> boxImageList = box.getBoxImageList();
        for (BoxImage boxImage : boxImageList) {
            s3Uploader.deleteFile(boxImage.getBoxImageUrl());
        }
        boxRepository.delete(box);
    }

    public Box findVerifyBox(Long boxId){
        return boxRepository.findById(boxId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOX_NOT_FOUND));
    }

    public void updateBox(Long boxId, List<MultipartFile> multipartFileList, UpdateBoxRequestDto updateBoxRequestDto) {
        Box box = findVerifyBox(boxId);
        box.updateBox(updateBoxRequestDto.getName(),
                updateBoxRequestDto.getLocation(),
                updateBoxRequestDto.getPhoneNumber(),
                updateBoxRequestDto.getCost(),
                updateBoxRequestDto.getArea(),
                updateBoxRequestDto.isBarbellDrop(),
                updateBoxRequestDto.getUrl(),
                updateBoxRequestDto.getDetail());

        HashMap<Integer, String> imageInfo = updateBoxRequestDto.getImageInfo();
        List<BoxImage> boxImageList = box.getBoxImageList();
        for (BoxImage boxImage : boxImageList) {
            if (imageInfo.containsValue(boxImage.getBoxImageUrl())) {

            }
        }
    }
}
