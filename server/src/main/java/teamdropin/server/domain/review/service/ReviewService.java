package teamdropin.server.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.repository.BoxRepository;
import teamdropin.server.domain.like.repository.LikeRepository;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.review.dto.CreateReviewRequestDto;
import teamdropin.server.domain.review.dto.UpdateReviewRequestDto;
import teamdropin.server.domain.review.entity.Review;
import teamdropin.server.domain.review.mapper.ReviewMapper;
import teamdropin.server.domain.review.repository.ReviewRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final BoxRepository boxRepository;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = false)
    public Long createReview(CreateReviewRequestDto createReviewRequestDto, Member member, Long boxId) {

        Box box = boxRepository.findById(boxId).orElseThrow(()-> new BusinessLogicException(ExceptionCode.BOX_NOT_FOUND));

        Review review = reviewMapper.toEntity(createReviewRequestDto);
        review.addMember(member);
        review.addBox(box);

        reviewRepository.save(review);
        return review.getId();
    }

    @Transactional(readOnly = false)
    public void updateReview(@Valid UpdateReviewRequestDto updateReviewRequestDto, Long boxId, Long reviewId, Member member){
        Review review = reviewRepository.findByBoxIdAndId(boxId,reviewId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));
        if(!review.getMember().getId().equals(member.getId())){
            throw new BusinessLogicException(ExceptionCode.USER_NOT_AUTHORIZED);
        }
        review.updateReviewInfo(updateReviewRequestDto.getBody());
    }

    @Transactional(readOnly = false)
    public void deleteReview(Long boxId, Long reviewId, Member member) {
        Review review = reviewRepository.findByBoxIdAndMemberIdAndId(boxId, member.getId(), reviewId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));
        if(!review.getMember().getId().equals(member.getId())){
            throw new BusinessLogicException(ExceptionCode.USER_NOT_AUTHORIZED);
        }
        likeRepository.deleteAllByReviewId(review.getId());
        reviewRepository.delete(review);
    }

}
