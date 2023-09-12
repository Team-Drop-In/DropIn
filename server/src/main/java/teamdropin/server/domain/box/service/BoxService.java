package teamdropin.server.domain.box.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamdropin.server.aws.service.S3Uploader;
import teamdropin.server.domain.box.boxImage.BoxImage;
import teamdropin.server.domain.box.boxImage.BoxImageRepository;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.mapper.BoxMapper;
import teamdropin.server.domain.box.repository.BoxRepository;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoxService {


    private final BoxRepository boxRepository;
    private final BoxImageRepository boxImageRepository;
    private final BoxMapper boxMapper;
    private final S3Uploader s3Uploader;


    @Transactional(readOnly = false)
    public Long createBox(Member member, Box box, List<MultipartFile> multipartFileList) throws IOException {

        box.addMember(member);
        boxRepository.save(box);

        if(multipartFileList != null) {
            int imageIdx = 0;
            int maxImageCount = 5;
            if(multipartFileList.size() > 5){
                throw new BusinessLogicException(ExceptionCode.UPLOAD_IMAGE_LIMIT_EXCEEDED);
            }
            List<String> imageUrlList = s3Uploader.uploadImages(multipartFileList, "box");
            for (String imageUrl : imageUrlList) {
                BoxImage boxImage = new BoxImage(imageUrl,++imageIdx, box);
                boxImageRepository.save(boxImage);
            }
        }
        return box.getId();
    }

    public Box getBox(Long boxId) {
        return boxRepository.findById(boxId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOX_NOT_FOUND));
    }

    public Page<Box> getAllBoxes(Pageable pageable) {
        return boxRepository.findAll(pageable);

    }
}
