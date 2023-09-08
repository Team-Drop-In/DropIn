package teamdropin.server.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import teamdropin.server.domain.like.postLike.entity.PostLike;
import teamdropin.server.domain.like.postLike.repository.PostLikeRepository;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.domain.post.dto.UpdatePostRequestDto;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.post.mapper.PostMapper;
import teamdropin.server.domain.post.repository.PostRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostMapper postMapper;
    private final MemberService memberService;

    @Transactional
    public Long createPost(Member member, Post post) {
        post.addMember(member);
        postRepository.save(post);
        return post.getId();
    }

    public Post getPost(Long id, Member member) {
        Post post = findVerifiedPost(id);
        post.viewCountUp();
//        int likeCount =  postLikeRepository.countByPostId(post.getId());
        List<PostLike> postLikes = postLikeRepository.findByPostId(post.getId());
        post.addPostLikes(postLikes);
        return post;
    }

    @Transactional
    public Post updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto, Member member){
        Post post = findVerifiedPost(postId);
        if(!post.getMember().getId().equals(member.getId())){
            throw new BusinessLogicException(ExceptionCode.USER_NOT_AUTHORIZED);
        }
        post.updatePostInfo(updatePostRequestDto.getTitle(), updatePostRequestDto.getBody(), updatePostRequestDto.getCategory());
        return post;
    }

    @Transactional
    public void deletePost(Long id, Member member){
        Post findPost = findVerifiedPost(id);
        if(!findPost.getMember().getId().equals(member.getId())){
            throw new BusinessLogicException(ExceptionCode.USER_NOT_AUTHORIZED);
        }
        postRepository.delete(findPost);
    }

    private Post findVerifiedPost(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }

    public Page<Post> getAllPosts(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size,
                                                    Sort.by("id").descending()));
    }
}
