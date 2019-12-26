package com.castlery.config.security;

import com.castlery.config.oauth2.CustomAuthorizationRequestResolver;
import com.castlery.modules.web.dto.Constant;
import com.castlery.modules.web.service.handler.OAuth2AuthenticationFailureHandler;
import com.castlery.modules.web.service.handler.OAuth2AuthenticationSuccessHandler;
import java.util.Arrays;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Profile("OAuth2")
public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private HttpSession session;

  @Autowired
  private ClientRegistrationRepository clientRegistrationRepository;

  @Autowired
  private OAuth2AuthenticationSuccessHandler successHandler;

  @Autowired
  private OAuth2AuthenticationFailureHandler failureHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.cors()
        .and()
        .csrf()
        .disable()
        .formLogin(
            formLogin -> formLogin.loginPage("/login").permitAll())
        .httpBasic()
        .disable()
        .exceptionHandling()
        .and()
        .authorizeRequests()
        .anyRequest()
        .permitAll()
        .and()
        .oauth2Login(
            oauth2hLogin ->
                oauth2hLogin
                    .userInfoEndpoint()
                    .oidcUserService(this.oidcUserService())
                    .and()
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .authorizationEndpoint()
                    .authorizationRequestResolver(
                        new CustomAuthorizationRequestResolver(this.clientRegistrationRepository,
                            session)));
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
    final OidcUserService delegate = new OidcUserService();

    return (userRequest) -> {
      //      userRequest.getAdditionalParameters()
      // Delegate to the default implementation for loading a user
      OidcUser oidcUser = delegate.loadUser(userRequest);
      session
          .setAttribute(Constant.AZURE_USER_EMAIL, oidcUser.getClaim(Constant.AZURE_UNIQUE_NAME));
      //      User databaseUser = userRepository.findByEmail();
      //      if (null == databaseUser){
      //        User.builder().email("")
      //        userRepository.save()
      //      }
      return oidcUser;
      //      OAuth2AccessToken accessToken = userRequest.getAccessToken();
      //      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
      //
      //      // TODO
      //      // 1) Fetch the authority information from the protected resource using accessToken
      //      // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities
      //      System.out.println("save local user");
      //      // 3) Create a copy of oidcUser but use the mappedAuthorities instead
      //      oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(),
      //          oidcUser.getUserInfo());
      //
      //      return oidcUser;
    };
  }
}
