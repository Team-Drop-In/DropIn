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
import teamdropin.server.domain.box.dto.box.*;
import teamdropin.server.domain.box.dto.boxImage.CreateBoxImageRequestDto;
import teamdropin.server.domain.box.entity.BoxImage;
import teamdropin.server.domain.box.repository.BoxImageRepository;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.mapper.BoxMapper;
import teamdropin.server.domain.box.repository.BoxQueryRepository;
import teamdropin.server.domain.box.repository.BoxRepository;
import teamdropin.server.domain.box.repository.BoxTagRepository;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.like.repository.LikeRepository;
import teamdropin.server.domain.like.service.LikeService;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.review.entity.Review;
import teamdropin.server.domain.review.repository.ReviewRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoxService {


    private final BoxRepository boxRepository;
    private final BoxQueryRepository boxQueryRepository;
    private final BoxTagRepository boxTagRepository;
    private final BoxImageRepository boxImageRepository;
    private final LikeRepository likeRepository;
    private final ReviewRepository reviewRepository;
    private final BoxTagService boxTagService;
    private final BoxMapper boxMapper;
    private final S3Uploader s3Uploader;
    private final LikeService likeService;


    @Transactional(readOnly = false)
    public Long createBox(Member member, BoxCreateRequestDto boxCreateRequestDto, List<MultipartFile> multipartFileList) throws IOException {

        if (multipartFileList != null) {
            int maxImageCount = 5;
            if (multipartFileList.size() > 5) {
                throw new BusinessLogicException(ExceptionCode.UPLOAD_IMAGE_LIMIT_EXCEEDED);
            }
        }


        Box box = boxMapper.toEntity(boxCreateRequestDto);
        box.addMember(member);
        boxRepository.save(box);
        boxTagService.createBoxTag(boxCreateRequestDto.getTagList(),box.getId());

        List<CreateBoxImageRequestDto> boxImageDtoList = boxCreateRequestDto.getImageInfo();
        for (CreateBoxImageRequestDto boxImageDto : boxImageDtoList) {
            if(multipartFileList != null){
                for(MultipartFile image : multipartFileList){
                    String originalImageName = image.getOriginalFilename();
                    originalImageName = Normalizer.normalize(originalImageName, Normalizer.Form.NFC);
                    if(boxImageDto.getImageName().equals(originalImageName)){
                        String newImageUrl = s3Uploader.upload(image, "box");
                        BoxImage boxImage = new BoxImage(newImageUrl, boxImageDto.getImageIndex(),box);
                        boxImageRepository.save(boxImage);
                    }
                }
            }
            if(boxImageDto.getImageName().equals("no_image")){
                BoxImage boxImage = new BoxImage("no_image", boxImageDto.getImageIndex(), box);
                boxImageRepository.save(boxImage);
            }
        }
        return box.getId();
    }

    @Transactional(readOnly = false)
    public GetBoxResponseDto getBox(Long boxId, Member member){
        Box box =  findVerifyBox(boxId);
        box.viewCountUp();
        return boxQueryRepository.getBoxQuery(boxId, member);
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
        List<Review> reviews = reviewRepository.findByBoxId(box.getId());
        likeRepository.deleteAllByReviews(reviews);
        boxImageRepository.deleteAllByBoxId(boxId);
        boxTagRepository.deleteAllByBoxId(boxId);
        likeRepository.deleteAllByBoxId(boxId);
        reviewRepository.deleteAllReviewByBoxId(boxId);
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

        boxTagService.updateAndDeleteBoxTag(updateBoxRequestDto.getTagList(), boxId);

        List<BoxImage> boxImageList = box.getBoxImageList();
        Map<Long, String> imageInfoDto = updateBoxRequestDto.getImageInfo();
        ArrayList<Long> updateIndex = new ArrayList();
        for (BoxImage boxImage : boxImageList) {
            for (Map.Entry<Long, String> imageInfoDtoEntrySet : imageInfoDto.entrySet()) {
                if (boxImage.getBoxImageUrl().equals(imageInfoDtoEntrySet.getValue())) {
                    updateIndex.add(imageInfoDtoEntrySet.getKey());
                }
            }
            log.info("imageInfo 1 ={}", imageInfoDto.get(1));
            log.info("imageInfo ={}", imageInfoDto.get(boxImage.getImageIndex()));
            log.info("boxImage.getIndex = {}", boxImage.getImageIndex());
            if (updateIndex.contains(boxImage.getImageIndex())) {
                String boxImageUrl = imageInfoDto.get(boxImage.getImageIndex());
                boxImage.updateBoxImage(boxImageUrl, boxImage.getImageIndex());
            } else if (imageInfoDto.get(boxImage.getImageIndex()).equals("no_image")) {
                String imageInfoDtoUrl = imageInfoDto.get(boxImage.getImageIndex());
                boxImage.updateBoxImage(imageInfoDtoUrl, boxImage.getImageIndex());
            } else if (multipartFileList != null) {
                for (MultipartFile imageFile : multipartFileList) {
                    String originalImageName = imageFile.getOriginalFilename();
                    originalImageName = Normalizer.normalize(originalImageName, Normalizer.Form.NFC);
                    if (imageInfoDto.get(boxImage.getImageIndex()).equals(originalImageName)) {
                        String newFileName = s3Uploader.upload(imageFile, "box");
                        boxImage.updateBoxImage(newFileName, boxImage.getImageIndex());
                    }
                }
            }
        }
    }

    public List<Box> findLikeBoxList(Long memberId){
        List<Like> likeBoxList = likeService.findLikeBoxList(memberId);
        List<Long> boxIds = likeBoxList.stream().map(like -> like.getBox().getId()).collect(Collectors.toList());
        List<Box> boxList = boxRepository.findAllById(boxIds);

        return boxList;
    }

    public Page<BoxSearchDto> getSearchBoxes(BoxSearchCondition condition, Pageable pageable){
        return boxQueryRepository.search(condition,pageable);
    }
}

