package teamdropin.server.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.security.filter.JwtExceptionResponseFilter;
import teamdropin.server.security.handler.MemberAccessDeniedHandler;
import teamdropin.server.security.handler.MemberAuthenticationEntryPoint;
import teamdropin.server.security.jwt.JwtService;
import teamdropin.server.security.jwt.JwtTokenizer;
import teamdropin.server.security.filter.CustomAuthenticationFilter;
import teamdropin.server.security.filter.CustomVerificationFilter;
import teamdropin.server.security.handler.MemberAuthenticationFailureHandler;
import teamdropin.server.security.handler.MemberAuthenticationSuccessHandler;
import teamdropin.server.security.oauth2.handler.OAuth2MemberSuccessHandler;
import teamdropin.server.security.utils.CustomAuthorityUtils;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberService memberService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new CustomFilterConfigurer())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())
                .accessDeniedHandler(new MemberAccessDeniedHandler())
                .and()
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer,jwtService, authorityUtils, memberService,memberRepository)))
                .authorizeHttpRequests()
                .antMatchers("/api/member/my-page").hasAnyRole("USER")
                .antMatchers( "/api/post").hasAnyRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/post/{id}").hasAnyRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/post/{id}").hasAnyRole("USER")
                .antMatchers("/api/post/{id}/comment/**").hasAnyRole("USER")
                .antMatchers("/api/post/like").hasAnyRole("USER")
                .antMatchers("/api/comment/like").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST,"/api/box").hasRole("MANAGER")
                .antMatchers(HttpMethod.DELETE, "/api/box/{id}").hasRole("MANAGER")
                .antMatchers(HttpMethod.PUT,"/api/box/{id}").hasRole("MANAGER")
                .anyRequest()
                .permitAll();
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://dropinproject.netlify.app"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.addExposedHeader("Refresh");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager, jwtTokenizer,jwtService);
            customAuthenticationFilter.setFilterProcessesUrl("/api/login");
            customAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            customAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            CustomVerificationFilter customVerificationFilter = new CustomVerificationFilter(jwtTokenizer, authorityUtils);
            JwtExceptionResponseFilter jwtExceptionResponseFilter = new JwtExceptionResponseFilter();

            builder
                    .addFilter(customAuthenticationFilter)
                    .addFilterAfter(customVerificationFilter, CustomAuthenticationFilter.class)
                    .addFilterAfter(customVerificationFilter, OAuth2LoginAuthenticationFilter.class)
                    .addFilterAfter(jwtExceptionResponseFilter,CustomVerificationFilter.class);
        }
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return (web) -> web.ignoring().antMatchers("/api/check-duplicate/**", "/api/email/**");
//     }
}
