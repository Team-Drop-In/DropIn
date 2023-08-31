package teamdropin.server.security.oauth2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import teamdropin.server.domain.member.entity.Gender;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;
import teamdropin.server.security.oauth2.OAuthAttributes;
import teamdropin.server.security.utils.CustomAuthorityUtils;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberService memberService;

    private final MemberRepository memberRepository;
    private final CustomAuthorityUtils authorityUtils;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = oAuth2UserRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrPass(registrationId, attributes);

        DefaultOAuth2User oauth2User = new DefaultOAuth2User(authorityUtils.createAuthorities(member.getRoles())
                , attributes.getAttributes()
                , attributes.getNameAttributeKey());
        return oauth2User;
    }

    private Member saveOrPass(String registrationId, OAuthAttributes attributes) {
        Member member =  Member.builder()
                .username(attributes.getEmail())
                .name(attributes.getName())
                .nickname(memberService.createRandomNickname())
                .oauthProvider(registrationId)
                .gender(Gender.NOT_SELECT)
                .roles(authorityUtils.createUserRoles())
                .build();

        memberService.validateDuplicateEmail(member.getUsername());
        memberRepository.save(member);
        return member;
    }
}
