package teamdropin.server.domain.box.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
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
import teamdropin.server.domain.box.dto.box.QBoxSearchDto;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static teamdropin.server.domain.box.entity.QBox.box;
import static teamdropin.server.domain.box.entity.QBoxImage.boxImage;
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

        JPQLQuery<String> subQuery =
                queryFactory.select(boxImage.boxImageUrl)
                        .from(boxImage)
                        .where(boxImage.box.eq(box), boxImage.imageIndex.eq(1));

        List<BoxSearchDto> content = queryFactory
                .select(new QBoxSearchDto(
                        box.id.as("boxId"),
                        box.name,
                        box.location,
                        box.boxLikes.size(),
                        box.viewCount,
                        subQuery
                        )
                ).from(box)
                .leftJoin(box.boxImageList)
                .leftJoin(box.boxLikes)
                .where(searchEq(condition))
                .orderBy(boxSort(condition), box.createdDate.desc())
                .groupBy(box)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(box.count())
                .from(box)
                .leftJoin(box.boxImageList)
                .leftJoin(box.boxImageList)
                .where(searchEq(condition))
                .groupBy(box);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private BooleanExpression searchEq(BoxSearchCondition condition){
        String search = condition.getSearch();
        return hasText(search) ? box.name.contains(search)
                .or(box.location.contains(search)) : null;
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
