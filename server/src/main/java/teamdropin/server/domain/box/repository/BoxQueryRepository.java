package teamdropin.server.domain.box.repository;

import com.querydsl.core.types.Expression;
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
import teamdropin.server.domain.box.dto.boxTag.BoxTagResponseDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.entity.BoxImage;
import teamdropin.server.domain.box.entity.BoxTag;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.like.entity.LikeCategory;
import teamdropin.server.domain.member.dto.GetWriterResponseDto;
import teamdropin.server.domain.member.dto.LoginUserInfoDto;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.review.dto.ReviewResponseDto;
import teamdropin.server.domain.review.entity.Review;

import javax.persistence.EntityManager;
import java.util.ArrayList;
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

    public Page<BoxSearchDto> search(BoxSearchCondition condition, Pageable pageable) {

        JPQLQuery<Long> boxLikeCount =
                JPAExpressions.select(like.count())
                        .from(like)
                        .where(like.box.id.eq(box.id));

        JPQLQuery<String> mainImage =
                JPAExpressions.select(boxImage.boxImageUrl)
                        .from(boxImage)
                        .where(boxImage.box.id.eq(box.id).and(boxImage.imageIndex.eq(1L)));

        JPQLQuery<Long> reviewCount =
                JPAExpressions.select(review.count())
                        .from(review)
                        .where(review.box.id.eq(box.id));

        List<BoxSearchDto> content = queryFactory
                .select(Projections.constructor(
                        BoxSearchDto.class,
                        box.id,
                        box.name,
                        box.location,
                        boxLikeCount,
                        box.viewCount,
                        reviewCount,
                        mainImage
                ))
                .from(box)
                .leftJoin(boxTag).on(box.id.eq(boxTag.box.id))
                .where(searchEq(condition), searchBarbellDrop(condition))
                .orderBy(boxSort(condition), box.createdDate.desc())
                .groupBy(box)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> boxIds = content.stream().map(BoxSearchDto::getId).collect(Collectors.toList());


        Map<Long, List<BoxTag>> boxTagMap = queryFactory
                .selectFrom(boxTag)
                .where(boxTag.box.id.in(boxIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(boxTag -> boxTag.getBox().getId()));

        content.forEach(boxSearchDto -> {
            Long boxId = boxSearchDto.getId();
            List<BoxTag> boxTags = boxTagMap.get(boxId);
            List<BoxTagResponseDto> tagList = getTagList(boxTags);
            boxSearchDto.setTagList(tagList);
        });

        JPAQuery<Long> count = queryFactory
                .select(box.count())
                .from(box)
                .where(searchEq(condition), searchBarbellDrop(condition));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private List<BoxTagResponseDto> getTagList(List<BoxTag> boxTags){
        if(boxTags != null && !boxTags.isEmpty()){
            return boxTags.stream()
                    .map(boxTag -> new BoxTagResponseDto(boxTag.getTagName()))
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
        return hasText(searchKeyword) ? box.id.eq(boxTag.box.id).and(boxTag.tagName.contains(searchKeyword)) : null;
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
            }
            if(sortCondition.equals("view-count")){
                return new OrderSpecifier<>(Order.DESC, box.viewCount);
            }
            if(sortCondition.equals("review-count")){
                return new OrderSpecifier<>(Order.DESC, box.reviews.size());
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

        List<BoxTag> boxTags = queryFactory
                .selectFrom(boxTag)
                .where(boxTag.box.id.eq(boxId))
                .fetch();

        Expression<GetWriterResponseDto> reviewWriter = Projections.constructor(
                GetWriterResponseDto.class,
                review.member.id,
                review.member.nickname,
                review.member.profileImageUrl
        );

        LoginUserInfoDto loginUserInfoDto = null;

        if(member != null){
            loginUserInfoDto = new LoginUserInfoDto(
                    member.getId(),
                    member.getNickname(),
                    member.getProfileImageUrl()
            );
        }

        List<BoxTagResponseDto> boxTagResponseDtoList = getTagList(boxTags);


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

        List<ReviewResponseDto> reviewResponseDtoList = queryFactory
                .select(Projections.constructor(
                        ReviewResponseDto.class,
                        review.id,
                        reviewWriter,
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

        getBoxResponseDto.setTagList(boxTagResponseDtoList);
        getBoxResponseDto.setBoxImages(boxImageResponseDtoList);
        getBoxResponseDto.setReviews(reviewResponseDtoList);
        getBoxResponseDto.setLoginUserInfo(loginUserInfoDto);

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
