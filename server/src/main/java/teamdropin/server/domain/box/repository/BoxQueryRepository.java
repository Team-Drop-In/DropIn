package teamdropin.server.domain.box.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.box.dto.box.BoxSearchCondition;
import teamdropin.server.domain.box.dto.box.BoxSearchDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.entity.BoxImage;
import teamdropin.server.domain.box.entity.QBoxImage;
import teamdropin.server.domain.boxTag.entity.BoxTag;
import teamdropin.server.domain.boxTag.entity.QBoxTag;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.like.entity.LikeCategory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.dsl.Expressions.list;
import static org.springframework.util.StringUtils.hasText;
import static teamdropin.server.domain.box.entity.QBox.box;
import static teamdropin.server.domain.box.entity.QBoxImage.boxImage;
import static teamdropin.server.domain.boxTag.entity.QBoxTag.*;
import static teamdropin.server.domain.like.entity.QLike.like;

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
                .leftJoin(box.boxImageList)
                .leftJoin(box.boxImageList)
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
        String search = condition.getSearch();
        return hasText(search) ? box.name.contains(search)
                .or(box.location.contains(search))
                .or(boxTag.tagName.contains(search)) : null;
    }

    private BooleanExpression searchBarbellDrop(BoxSearchCondition condition){
        String barbellDrop = condition.getBarbellDrop();
        return hasText(barbellDrop) ? box.barbellDrop.eq(true) : null;
    }

    private OrderSpecifier<?> boxSort(BoxSearchCondition condition){
        if(condition.getOrderBy() != null){
            String orderBy = condition.getOrderBy();
            if(orderBy.equals("like-count")){
                return new OrderSpecifier<>(Order.DESC, box.boxLikes.size());
            } else if(orderBy.equals("view-count")){
                return new OrderSpecifier<>(Order.DESC, box.viewCount);
            }
        }
        return new OrderSpecifier<>(Order.DESC, box.createdDate);
    }
}
