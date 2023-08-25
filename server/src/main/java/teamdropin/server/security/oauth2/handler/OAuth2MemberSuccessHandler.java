package teamdropin.server.security.oauth2.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import teamdropin.server.domain.member.entity.Gender;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;
import teamdropin.server.security.jwt.JwtService;
import teamdropin.server.security.jwt.JwtTokenizer;
import teamdropin.server.security.utils.CustomAuthorityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final JwtService jwtService;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberService memberService;
    private final MemberRepository memberRepository;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("authorities={}", authentication.getAuthorities());
        Member member = memberRepository.findByUsername(String.valueOf(oAuth2User.getAttributes().get("email")))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

//        saveMember(member);
        redirect(request, response, member);
    }

//    private Member toOauthMember(OAuth2User oAuth2User ) {
//        return Member.builder()
//                .username(String.valueOf(oAuth2User.getAttributes().get("email")))
//                .password(String.valueOf(oAuth2User.getAttributes().get("sub")))
//                .name(String.valueOf(oAuth2User.getAttributes().get("name")))
//                .gender(Gender.NOT_SELECT)
//                .nickname(memberService.createRandomNickname())
//                .roles(authorityUtils.createUserRoles())
//                .build();
//    }

//    private void saveMember(Member member){
//        try {
//            memberService.join(member);
//        } catch (IllegalStateException e){
//            return;
//        }
//    }

    private void redirect(HttpServletRequest request,
                          HttpServletResponse response, Member member) throws IOException{
        String accessToken = jwtService.delegateAccessToken(member);
        String refreshToken = jwtService.delegateRefreshToken(member);

        String uri = createURI(accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request,response,uri);

    }

    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(80)
                .path("/receive-token.html")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
