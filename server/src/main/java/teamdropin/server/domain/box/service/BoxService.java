package teamdropin.server.domain.box.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamdropin.server.aws.service.S3Uploader;
import teamdropin.server.domain.box.entity.BoxImage;
import teamdropin.server.domain.box.repository.BoxImageRepository;
import teamdropin.server.domain.box.dto.BoxCreateRequestDto;
import teamdropin.server.domain.box.dto.UpdateBoxRequestDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.mapper.BoxMapper;
import teamdropin.server.domain.box.repository.BoxRepository;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.like.repository.LikeRepository;
import teamdropin.server.domain.like.service.LikeService;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import java.io.IOException;
import java.text.Normalizer;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoxService {


    private final BoxRepository boxRepository;
    private final BoxImageRepository boxImageRepository;

    private final LikeRepository likeRepository;
    private final BoxMapper boxMapper;
    private final S3Uploader s3Uploader;
    private final LikeService likeService;


    @Transactional(readOnly = false)
    public Long createBox(Member member, BoxCreateRequestDto boxCreateRequestDto, List<MultipartFile> multipartFileList) throws IOException {

        Box box = boxMapper.toEntity(boxCreateRequestDto);
        box.addMember(member);
        boxRepository.save(box);
        if(multipartFileList == null){
            int maxImageCount = 5;
            for(int i = 1; i<=maxImageCount; i++) {
                BoxImage boxImage = new BoxImage("no_image", i, box);
                boxImageRepository.save(boxImage);
            }
        }

        if (multipartFileList != null) {
            int maxImageCount = 5;
            if (multipartFileList.size() > 5) {
                throw new BusinessLogicException(ExceptionCode.UPLOAD_IMAGE_LIMIT_EXCEEDED);
            }
            HashMap<Integer, String> imageInfoDto = boxCreateRequestDto.getImageInfo();
            Map<String, String> imageUrlList = s3Uploader.uploadImages(multipartFileList, "box");
            for (Map.Entry<String, String> imageUrlInfo : imageUrlList.entrySet()) {
                String newImageUrl = imageUrlInfo.getKey();
                String originalImageName = imageUrlInfo.getValue();
                originalImageName = Normalizer.normalize(originalImageName, Normalizer.Form.NFC);
                for (Map.Entry<Integer, String> imageInfoDtoEntrySet : imageInfoDto.entrySet()) {
                    if (imageInfoDtoEntrySet.getValue().equals(originalImageName)) {
                        int imageIdx = imageInfoDtoEntrySet.getKey();
                        BoxImage boxImage = new BoxImage(newImageUrl, imageIdx, box);
                        boxImageRepository.save(boxImage);
                    }
                }
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

    public Box findVerifyBox(Long boxId) {
        return boxRepository.findById(boxId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOX_NOT_FOUND));
    }

    @SneakyThrows
    @Transactional(readOnly = false)
    public void updateBox(Long boxId, List<MultipartFile> multipartFileList, UpdateBoxRequestDto updateBoxRequestDto) {

        if (multipartFileList != null) {
            int maxImageCount = 5;
            if (multipartFileList.size() > maxImageCount) {
                throw new BusinessLogicException(ExceptionCode.UPLOAD_IMAGE_LIMIT_EXCEEDED);
            }
        }
        Box box = findVerifyBox(boxId);
        box.updateBox(updateBoxRequestDto.getName(),
                updateBoxRequestDto.getLocation(),
                updateBoxRequestDto.getPhoneNumber(),
                updateBoxRequestDto.getCost(),
                updateBoxRequestDto.getArea(),
                updateBoxRequestDto.isBarbellDrop(),
                updateBoxRequestDto.getUrl(),
                updateBoxRequestDto.getDetail());
        List<BoxImage> boxImageList = box.getBoxImageList();
        Map<Integer, String> imageInfoDto = updateBoxRequestDto.getImageInfo();
        ArrayList<Integer> updateIndex = new ArrayList();
        for (BoxImage boxImage : boxImageList) {
            for (Map.Entry<Integer, String> imageInfoDtoEntrySet : imageInfoDto.entrySet()) {
                if (boxImage.getBoxImageUrl().equals(imageInfoDtoEntrySet.getValue())) {
                    updateIndex.add(imageInfoDtoEntrySet.getKey());
                }
            }
        }
        for (int i = 1; i <= imageInfoDto.size(); i++) {
            BoxImage boxImage = boxImageRepository.findById((long) i).orElseThrow();
            if (updateIndex.contains(i)) {
                String boxImageUrl = imageInfoDto.get(i);
                boxImage.updateBoxImage(boxImageUrl, i);
            } else if (imageInfoDto.get(i).equals("no_image")) {
                String imageInfoDtoUrl = imageInfoDto.get(i);
                boxImage.updateBoxImage(imageInfoDtoUrl, i);
            } else if (!multipartFileList.isEmpty()) {
                for (MultipartFile image : multipartFileList) {
                    String originalImageName = image.getOriginalFilename();
                    originalImageName = Normalizer.normalize(originalImageName, Normalizer.Form.NFC);
                    if (imageInfoDto.get(i).equals(originalImageName)) {
                        String newFileName = s3Uploader.upload(image, "box");
                        boxImage.updateBoxImage(newFileName, i);
                    }
                }
            }
        }
    }

    public List<Box> findLikeBoxList(Long memberId){
        List<Like> likeBoxList = likeService.findLikeBoxList(memberId);
        List<Box> boxList = new ArrayList<>();
        for (Like like : likeBoxList) {
            boxList.add(like.getBox());
        }
        return boxList;
    }
}

