package teamdropin.server.domain.post.repository;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamdropin.server.domain.comment.dto.CommentResponseDto;
import teamdropin.server.domain.member.dto.GetWriterResponseDto;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.dto.GetPostResponseDto;
import teamdropin.server.domain.post.dto.PostSearchCondition;
import teamdropin.server.domain.post.dto.PostSearchDto;


import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static teamdropin.server.domain.comment.entity.QComment.comment;
import static teamdropin.server.domain.like.entity.QLike.like;
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

        Expression<GetWriterResponseDto> writer = Projections.constructor(
                GetWriterResponseDto.class,
                post.member.id,
                post.member.nickname
        );

        List<PostSearchDto> content = queryFactory
                .select(Projections.constructor(
                        PostSearchDto.class,
                        post.id,
                        post.title,
                        post.body,
                        post.viewCount,
                        post.category,
                        post.postLikes.size(),
                        writer,
                        post.comments.size(),
                        post.createdDate,
                        post.member.profileImageUrl
                        ))
                .from(post)
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

    public GetPostResponseDto getPost(Long postId, Member member){
        JPQLQuery<Long> postLikeCount =
                JPAExpressions.select(like.count())
                        .from(like)
                        .where(like.post.id.eq(post.id));

        JPQLQuery<Long> commentLikeCount =
                JPAExpressions.select(like.count())
                        .from(like)
                        .where(like.comment.id.eq(comment.id));

        Expression<GetWriterResponseDto> postWriter = Projections.constructor(
                GetWriterResponseDto.class,
                post.member.id,
                post.member.nickname
        );

        Expression<GetWriterResponseDto> commentWriter = Projections.constructor(
                GetWriterResponseDto.class,
                comment.member.id,
                comment.member.nickname
        );



        List<CommentResponseDto> commentResponseDtos = queryFactory
                .select(Projections.constructor(
                        CommentResponseDto.class,
                        comment.id,
                        commentWriter,
                        comment.body,
                        checkCommentLike(member),
                        checkCommentWriter(member),
                        commentLikeCount,
                        comment.createdDate,
                        comment.member.profileImageUrl))
                .from(comment)
                .where(comment.post.id.eq(postId))
                .fetch();


        GetPostResponseDto getPostResponseDto = queryFactory
                .select(Projections.constructor(
                        GetPostResponseDto.class,
                        post.id,
                        post.title,
                        post.body,
                        post.viewCount ,
                        post.category,
                        postWriter,
                        postLikeCount,
                        checkPostLike(member),
                        checkPostWriter(member),
                        post.createdDate,
                        post.member.profileImageUrl))
                .from(post)
                .where(post.id.eq(postId))
                .groupBy(post)
                .fetchOne();

        getPostResponseDto.setComments(commentResponseDtos);

        return getPostResponseDto;
    }

    private BooleanExpression checkPostLike(Member member){
        if(member == null){
            return Expressions.FALSE;
        }
        return JPAExpressions.select(like.id)
                .from(like)
                .where(like.member.id.eq(member.getId()).and(like.post.id.eq(post.id)))
                .exists();
    }

    private BooleanExpression checkPostWriter(Member member){
        if(member == null){
            return Expressions.FALSE;
        }
        return JPAExpressions.select(post.id)
                .from(post)
                .where(post.member.id.eq(member.getId()))
                .exists();
    }

    private BooleanExpression checkCommentLike(Member member){
        if(member == null){
            return Expressions.FALSE;
        }
        return JPAExpressions.select(like.id)
                .from(like)
                .where(like.member.id.eq(member.getId()).and(like.comment.id.eq(comment.id)))
                .exists();
    }

    private BooleanExpression checkCommentWriter(Member member){
        if(member == null){
            return Expressions.FALSE;
        }
        return JPAExpressions.select(comment.id)
                .from(comment)
                .where(comment.member.id.eq(member.getId()))
                .exists();
    }
}
