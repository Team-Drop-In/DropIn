package teamdropin.server.domain.post.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import teamdropin.server.domain.comment.dto.CommentResponseDto;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.comment.mapper.CommentMapper;
import teamdropin.server.domain.post.dto.GetAllPostResponseDto;
import teamdropin.server.domain.post.dto.GetPostResponseDto;
import teamdropin.server.domain.post.dto.UpdatePostRequestDto;
import teamdropin.server.domain.post.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentMapper commentMapper;

    public GetPostResponseDto postToGetPostResponseDto(Post post){
        return GetPostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .viewCount(post.getViewCount())
                .category(post.getCategory())
                .nickname(post.getMember().getNickname())
                .comments(commentMapper.commentsToCommentResponseDtoList(post.getComments()))
                .likeCount(post.getPostLikes().size())
                .checkLike(false)
                .build();
    }

    public GetAllPostResponseDto postToGetAllPostResponseDto(Post post){
        return GetAllPostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .viewCount(post.getViewCount())
                .category(post.getCategory())
                .likeCount(post.getPostLikes().size())
                .nickname(post.getMember().getNickname())
                .commentCount(post.getComments().size())
                .build();
    }

    public List<GetAllPostResponseDto> postToGetAllPostResponseDtoList(List<Post> posts){
        List<GetAllPostResponseDto> resultList =
                posts.stream().map(this::postToGetAllPostResponseDto).collect(Collectors.toList());
        return resultList;
    }
}
