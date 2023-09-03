package teamdropin.server.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(Member member, Post post) {
        post.addMember(member);
        postRepository.save(post);
    }
}
