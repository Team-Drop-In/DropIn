package teamdropin.server.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamdropin.server.domain.comment.mapper.CommentMapper;
import teamdropin.server.domain.like.repository.LikeRepository;
import teamdropin.server.domain.like.service.LikeService;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.domain.post.dto.PostSearchCondition;
import teamdropin.server.domain.post.dto.PostSearchDto;
import teamdropin.server.domain.post.dto.UpdatePostRequestDto;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.post.mapper.PostMapper;
import teamdropin.server.domain.post.repository.PostQueryRepository;
import teamdropin.server.domain.post.repository.PostRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final MemberService memberService;
    private final LikeService likeService;
    private final PostQueryRepository postQueryRepository;


    @Transactional
    public Long createPost(Member member, Post post) {
        post.addMember(member);
        postRepository.save(post);
        return post.getId();
    }

    public Post getPost(Long postId, Member member) {
        Post post = findVerifiedPost(postId);
        post.viewCountUp();


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

    public Page<PostSearchDto> getSearchPosts(PostSearchCondition condition, Pageable pageable){
        return postQueryRepository.search(condition,pageable);
    }

    public long countWritePost(Long memberId){
        return postRepository.countByMemberId(memberId);
    }
}
