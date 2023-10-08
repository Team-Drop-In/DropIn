package teamdropin.server.domain.box.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.box.dto.box.BoxSearchCondition;
import teamdropin.server.domain.box.dto.box.BoxSearchDto;
import teamdropin.server.domain.box.dto.box.GetBoxResponseDto;
import teamdropin.server.domain.box.dto.boxImage.BoxImageResponseDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.entity.BoxImage;
import teamdropin.server.domain.box.entity.BoxTag;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.like.entity.LikeCategory;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.review.dto.ReviewResponseDto;
import teamdropin.server.domain.review.entity.Review;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.dsl.Expressions.list;
import static org.springframework.util.StringUtils.hasText;
import static teamdropin.server.domain.box.entity.QBox.box;
import static teamdropin.server.domain.box.entity.QBoxImage.boxImage;
import static teamdropin.server.domain.box.entity.QBoxTag.*;
import static teamdropin.server.domain.comment.entity.QComment.comment;
import static teamdropin.server.domain.like.entity.QLike.like;
import static teamdropin.server.domain.review.entity.QReview.review;

@Repository
public class BoxQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public BoxQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<BoxSearchDto> search(BoxSearchCondition condition, Pageable pageable){

        List<Box> boxes = queryFactory
                .selectFrom(box)
                .where(searchEq(condition), searchBarbellDrop(condition))
                .orderBy(boxSort(condition), box.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        List<Long> boxIds = boxes.stream().map(Box::getId).collect(Collectors.toList());
        Map<Long, List<Like>> boxLikeMap = queryFactory
                .selectFrom(like)
                .where(like.likeCategory.eq(LikeCategory.BOX), like.box.id.in(boxIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(like -> like.getBox().getId()));

        Map<Long, List<BoxImage>> boxImageMap = queryFactory
                .selectFrom(boxImage)
                .where(boxImage.box.id.in(boxIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(boxImage -> boxImage.getBox().getId()));

        Map<Long, List<BoxTag>> boxTagMap = queryFactory
                .selectFrom(boxTag)
                .where(boxTag.box.id.in(boxIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(boxTag -> boxTag.getBox().getId()));


        List<BoxSearchDto> content = boxes.stream().map(box -> {
            List<Like> boxLikes = boxLikeMap.get(box.getId());
            List<BoxImage> boxImages = boxImageMap.get(box.getId());
            List<BoxTag> boxTags = boxTagMap.get(box.getId());

            return new BoxSearchDto(
                    box.getId(),
                    box.getName(),
                    box.getLocation(),
                    boxLikes != null ? boxLikes.size() : 0,
                    box.getViewCount(),
                    getMainImageUrl(boxImages),
                    getTagList(boxTags)
            );
        }).collect(Collectors.toList());


        JPAQuery<Long> count = queryFactory
                .select(box.count())
                .from(box)
                .where(searchEq(condition))
                .groupBy(box.id, boxTag.id);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private String getMainImageUrl(List<BoxImage> boxImages) {
        if(boxImages != null && !boxImages.isEmpty()){
            return boxImages.stream()
                    .filter(boxImage -> boxImage.getImageIndex() == 1)
                    .map(BoxImage::getBoxImageUrl)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private List<String> getTagList(List<BoxTag> boxTags){
        if(boxTags != null && !boxTags.isEmpty()){
            return boxTags.stream()
                    .map(BoxTag::getTagName)
                    .collect(Collectors.toList());
        }
        return null;
    }

    private BooleanExpression searchEq(BoxSearchCondition condition){
        if(condition.getSearchType() != null){
            String searchType = condition.getSearchType();
            if(searchType.equals("all")){
                return searchBoxName(condition).or(searchBoxLocation(condition)).or(searchTagName(condition));
            }
            if(searchType.equals("box-name")){
                return searchBoxName(condition);
            }
            if(searchType.equals("box-location")){
                return searchBoxLocation(condition);
            }
            if(searchType.equals("tag-name")){
                return searchTagName(condition);
            }
        }
        return null;
    }

    private BooleanExpression searchBoxName(BoxSearchCondition condition){
        String searchKeyword = condition.getSearchKeyword();
        return hasText(searchKeyword) ? box.name.contains(searchKeyword) : null;
    }

    private BooleanExpression searchBoxLocation(BoxSearchCondition condition){
        String searchKeyword = condition.getSearchKeyword();
        return hasText(searchKeyword) ? box.location.contains(searchKeyword) : null;
    }

    private BooleanExpression searchTagName(BoxSearchCondition condition){
        String searchKeyword = condition.getSearchKeyword();
        return hasText(searchKeyword) ? boxTag.tagName.contains(searchKeyword) : null;
    }

    private BooleanExpression searchBarbellDrop(BoxSearchCondition condition){
        String barbellDrop = condition.getBarbellDrop();
        return hasText(barbellDrop) ? box.barbellDrop.eq(Boolean.valueOf(barbellDrop)) : null;
    }

    private OrderSpecifier<?> boxSort(BoxSearchCondition condition){
        if(condition.getSortCondition() != null){
            String sortCondition = condition.getSortCondition();
            if(sortCondition.equals("latest")){
                return new OrderSpecifier<>(Order.DESC, box.createdDate);
            }
            if(sortCondition.equals("like-count")){
                return new OrderSpecifier<>(Order.DESC, box.boxLikes.size());
            } else if(sortCondition.equals("view-count")){
                return new OrderSpecifier<>(Order.DESC, box.viewCount);
            }
        }
        return new OrderSpecifier<>(Order.DESC, box.createdDate);
    }

    public GetBoxResponseDto getBoxQuery(Long boxId, Member member) {

        JPQLQuery<Long> boxLikeCount =
                JPAExpressions.select(like.count())
                        .from(like)
                        .where(like.box.id.eq(box.id));

        JPQLQuery<Long> reviewLikeCount =
                JPAExpressions.select(like.count())
                        .from(like)
                        .where(like.review.id.eq(review.id));

        List<String> tagList = queryFactory
                .select(boxTag.tagName)
                .from(boxTag)
                .where(boxTag.box.id.eq(boxId))
                .fetch();

        List<BoxImageResponseDto> boxImageResponseDtoList = queryFactory
                .select(Projections.constructor(
                        BoxImageResponseDto.class,
                        boxImage.id,
                        boxImage.imageIndex,
                        boxImage.boxImageUrl
                ))
                .from(boxImage)
                .where(boxImage.box.id.eq(boxId))
                .fetch();

        List<ReviewResponseDto > reviewResponseDtoList = queryFactory
                .select(Projections.constructor(
                        ReviewResponseDto.class,
                        review.id,
                        review.member.nickname,
                        review.body,
                        reviewLikeCount,
                        checkReviewLike(member),
                        checkReviewWriter(member),
                        review.createdDate
                ))
                .from(review)
                .where(review.box.id.eq(boxId))
                .fetch();

        GetBoxResponseDto getBoxResponseDto =  queryFactory
                .select(Projections.constructor(
                        GetBoxResponseDto.class,
                        box.id,
                        box.name,
                        box.location,
                        box.phoneNumber,
                        box.cost,
                        box.area,
                        box.barbellDrop,
                        box.url,
                        box.detail,
                        boxLikeCount,
                        box.viewCount,
                        checkBoxLike(member)
                ))
                .from(box)
                .where(box.id.eq(boxId))
                .groupBy(box.id)
                .fetchOne();

        getBoxResponseDto.setTagList(tagList);
        getBoxResponseDto.setBoxImages(boxImageResponseDtoList);
        getBoxResponseDto.setReviews(reviewResponseDtoList);

        return getBoxResponseDto;
    }

    private BooleanExpression checkBoxLike(Member member){
        if(member == null){
            return Expressions.FALSE;
        }
        return JPAExpressions.select(like.id)
                .from(like)
                .where(like.member.id.eq(member.getId()).and(like.box.id.eq(box.id)))
                .exists();
    }

    private BooleanExpression checkReviewLike(Member member){
        if(member == null){
            return Expressions.FALSE;
        }
        return JPAExpressions.select(like.id)
                .from(like)
                .where(like.member.id.eq(member.getId()).and(like.review.id.eq(review.id)))
                .exists();
    }

    private BooleanExpression checkReviewWriter(Member member){
        if(member == null){
            return Expressions.FALSE;
        }
        return JPAExpressions.select(review.id)
                .from(review)
                .where(review.member.id.eq(member.getId()))
                .exists();
    }

}
