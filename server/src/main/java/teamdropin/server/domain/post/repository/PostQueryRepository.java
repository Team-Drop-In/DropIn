package teamdropin.server.domain.post.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.post.dto.PostSearchCondition;
import teamdropin.server.domain.post.dto.PostSearchDto;
import teamdropin.server.domain.post.dto.QPostSearchDto;

import javax.persistence.EntityManager;
import java.util.List;

import static teamdropin.server.domain.comment.entity.QComment.*;
import static teamdropin.server.domain.member.entity.QMember.member;
import static teamdropin.server.domain.post.entity.QPost.post;

@Repository
public class PostQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public PostQueryRepository(EntityManager em){
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<PostSearchDto> search(PostSearchCondition condition, Pageable pageable){

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
                        post.createdDate))
                .from(post)
                .leftJoin(post.member, member)
                .leftJoin(post.comments, comment)
                .where(
                        post.member.nickname.contains(condition.getSearch()).or(
                        post.title.contains(condition.getSearch())).or(
                        post.body.contains(condition.getSearch())).or(
                        comment.body.contains(condition.getSearch())
                        )
                )
                .orderBy(post.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(post.count())
                .from(post)
                .leftJoin(post.member, member)
                .leftJoin(post.comments, comment)
                .where(
                post.member.nickname.contains(condition.getSearch()).or(
                post.title.contains(condition.getSearch())).or(
                post.body.contains(condition.getSearch())).or(
                comment.body.contains(condition.getSearch())));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}
