// package com.castlery.config.security;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.config.Customizer;
// import
// org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
// import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
// import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//  //  @Autowired
//  //    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService;
//
//  @Autowired
//  private CustomOAuth2UserService customOAuth2UserService;
//
//  @Autowired
//  private CustomUserDetailsService customUserDetailsService;
//
//  @Autowired
//  private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
//
//  @Autowired
//  private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    //    http
//    //        .authorizeRequests(authorizeRequests ->
//    //            authorizeRequests
//    //                //                .mvcMatchers("/", "/public/**").permitAll()
//    //                .anyRequest().authenticated()
//    //        )
//    //        .formLogin(withDefaults())
//    //        .oauth2Login(withDefaults())
//    //        .oauth2Client(withDefaults());
//
//    http.cors()
//        .and()
//        .csrf()
//        .disable()
//        .formLogin(Customizer.withDefaults())
//        //        .disable()
//        .httpBasic()
//        .disable()
//        .exceptionHandling()
//        .and()
//        .authorizeRequests()
//        .antMatchers(
//            "/",
//            "/error",
//            "/favicon.ico",
//            "/**/*.png",
//            "/**/*.gif",
//            "/**/*.svg",
//            "/**/*.jpg",
//            "/**/*.html",
//            "/**/*.css",
//            "/**/*.js")
//        .permitAll()
//        .antMatchers("/auth/**", "/oauth2/**")
//        .permitAll()
//        .anyRequest()
//        .authenticated()
//        .and()
//        //        .oauth2Login(withDefaults()).oauth2Client(withDefaults()).
//        .oauth2Login(
//            oauth2hLogin ->
// oauth2hLogin.userInfoEndpoint().oidcUserService(this.oidcUserService())
//                .and().successHandler(oAuth2AuthenticationSuccessHandler)
//                .failureHandler(oAuth2AuthenticationFailureHandler))
//        .userDetailsService(customUserDetailsService);
//    //        .authorizationEndpoint()
//    //        .baseUri("/oauth2/authorize")
//    //        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
//    //        .and()
//    //        .redirectionEndpoint()
//    //        .baseUri("/oauth2/callback/*")
//    //        .and()
//    //        .successHandler()
//    //        .failureHandler(oAuth2AuthenticationFailureHandler);
//
//  }
//
//  private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//    final OidcUserService delegate = new OidcUserService();
//
//    return (userRequest) -> {
//      // Delegate to the default implementation for loading a user
//      OidcUser oidcUser = delegate.loadUser(userRequest);
//      System.out.println("save local user");
//      return oidcUser;
//      //      OAuth2AccessToken accessToken = userRequest.getAccessToken();
//      //      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//      //
//      //      // TODO
//      //      // 1) Fetch the authority information from the protected resource using accessToken
//      //      // 2) Map the authority information to one or more GrantedAuthority's and add it to
// mappedAuthorities
//      //      System.out.println("save local user");
//      //      // 3) Create a copy of oidcUser but use the mappedAuthorities instead
//      //      oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(),
//      //          oidcUser.getUserInfo());
//      //
//      //      return oidcUser;
//    };
//  }
// }
