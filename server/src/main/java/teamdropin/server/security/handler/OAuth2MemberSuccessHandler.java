package teamdropin.server.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.entity.OauthProvider;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.security.auth.JwtService;
import teamdropin.server.security.auth.JwtTokenizer;
import teamdropin.server.security.utils.CustomAuthorityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final JwtService jwtService;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberService memberService;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Member member = oauthMember(oAuth2User);

        saveMember(member);
        redirect(request, response, member);
    }

    private Member oauthMember(OAuth2User oAuth2User) {
        Member member = Member.builder()
                .username(String.valueOf(oAuth2User.getAttributes().get("email")))
                .password(String.valueOf(oAuth2User.getAttributes().get("sub")))
                .name(String.valueOf(oAuth2User.getAttributes().get("name")))
                .roles(authorityUtils.createUserRoles())
                .build();
        return member;
    }

    private void saveMember(Member member){
        try {
            memberService.join(member);
        } catch (IllegalStateException e){
            return;
        }
    }

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
