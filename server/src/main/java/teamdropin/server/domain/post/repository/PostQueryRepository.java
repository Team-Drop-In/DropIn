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
import teamdropin.server.domain.post.dto.PostSearchCondition;
import teamdropin.server.domain.post.dto.PostSearchDto;
import teamdropin.server.domain.post.dto.QPostSearchDto;


import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
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


    private BooleanExpression searchEq(PostSearchCondition condition){
        if(condition.getSearchType() != null) {
            String searchType = condition.getSearchType();
            if(searchType.equals("all")){
                return searchPostTitle(condition).or(searchPostBody(condition)).or(searchWriter(condition));
            }
            if (searchType.equals("post-title")){
                return searchPostTitle(condition);
            }
            if (searchType.equals("post-body")){
                return searchPostBody(condition);
            }
            if (searchType.equals("nickname")){
                return searchWriter(condition);
            }
        }
        return null;
    }

    private BooleanExpression searchPostTitle(PostSearchCondition condition){
        String searchKeyword = condition.getSearchKeyword();
        return hasText(searchKeyword) ? post.title.contains(searchKeyword) : null;
    }

    private BooleanExpression searchPostBody(PostSearchCondition condition){
        String searchKeyword = condition.getSearchKeyword();
        return hasText(searchKeyword) ? post.body.contains(searchKeyword) : null;
    }

    private BooleanExpression searchWriter(PostSearchCondition condition){
        String searchKeyword = condition.getSearchKeyword();
        return hasText(searchKeyword) ? post.member.nickname.contains(searchKeyword) : null;
    }

    private OrderSpecifier<?> postSort(PostSearchCondition condition) {
        if (condition.getSortCondition() != null) {
            String sortCondition = condition.getSortCondition();
            if(sortCondition.equals("latest")){
                return new OrderSpecifier<>(Order.DESC,post.createdDate);
            }
            if (sortCondition.equals("like-count")){
                return new OrderSpecifier<>(Order.DESC, post.postLikes.size());
            } else if (sortCondition.equals("view-count")){
                return new OrderSpecifier<>(Order.DESC, post.viewCount);
            }
        }
        return new OrderSpecifier<>(Order.DESC, post.createdDate);
    }
}
