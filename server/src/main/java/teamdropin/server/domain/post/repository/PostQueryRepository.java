package teamdropin.server.domain.post.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import teamdropin.server.domain.comment.entity.QComment;
import teamdropin.server.domain.post.dto.PostSearchCondition;
import teamdropin.server.domain.post.dto.PostSearchDto;
import teamdropin.server.domain.post.dto.QPostSearchDto;


import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static teamdropin.server.domain.comment.entity.QComment.comment;
import static teamdropin.server.domain.like.entity.QLike.like;
import static teamdropin.server.domain.member.entity.QMember.member;
import static teamdropin.server.domain.post.entity.QPost.post;

@Repository
@Slf4j
public class PostQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<PostSearchDto> search(PostSearchCondition condition, Pageable pageable) {



        List<PostSearchDto> content = queryFactory
                .select(new QPostSearchDto(
                        post.id.as("postId"),
                        post.title,
                        post.body,
                        post.viewCount,
                        post.category,
                        post.postLikes.size(),
                        post.member.nickname,
                        post.comments.size(),
                        post.createdDate,
                        post.member.profileImageUrl)
                ).from(post)
                .where(searchEq(condition))
                .orderBy(postSort(condition), post.createdDate.desc())
                .groupBy(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(post.count())
                .from(post)
                .where(searchEq(condition));

        log.info("get Count = {}", count);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private BooleanExpression searchEq(PostSearchCondition condition) {
        String search = condition.getSearch();
        return hasText(search) ? post.member.nickname.contains(search)
                .or(post.title.contains(search))
                .or(post.body.contains(search)) : null;
    }

    private OrderSpecifier<?> postSort(PostSearchCondition condition) {
        if (condition.getOrderBy() != null) {
            String orderBy = condition.getOrderBy();
            if (orderBy.equals("like-count")){
                return new OrderSpecifier<>(Order.DESC, post.postLikes.size());
            } else if (orderBy.equals("view-count")){
                return new OrderSpecifier<>(Order.DESC, post.viewCount);
            }
        }
        return new OrderSpecifier<>(Order.DESC, post.createdDate);
    }
}
