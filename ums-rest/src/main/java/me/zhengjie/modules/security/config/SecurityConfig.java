  // package me.zhengjie.modules.security.config;
  //
  // import java.util.Map;
  // import me.zhengjie.modules.security.service.JwtUserDetailsService;
  // import org.springframework.beans.factory.annotation.Autowired;
  // import org.springframework.context.ApplicationContext;
  // import org.springframework.context.annotation.Bean;
  // import org.springframework.context.annotation.Configuration;
  // import org.springframework.security.authentication.AuthenticationManager;
  // import
  // org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
  // import
  // org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
  // import org.springframework.security.config.annotation.web.builders.HttpSecurity;
  // import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
  // import
  // org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
  // import org.springframework.security.config.core.GrantedAuthorityDefaults;
  // import org.springframework.security.config.http.SessionCreationPolicy;
  // import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
  // import org.springframework.security.crypto.password.PasswordEncoder;
  // import org.springframework.web.method.HandlerMethod;
  // import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
  // import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
  //
  // @Configuration
  // @EnableWebSecurity
  // @EnableGlobalMethodSecurity(prePostEnabled = true)
  // public class SecurityConfig extends WebSecurityConfigurerAdapter {
  //
  //  //    private final JwtAuthenticationEntryPoint unauthorizedHandler;
  //
  //  private final JwtUserDetailsService jwtUserDetailsService;
  //
  //  private final ApplicationContext applicationContext;
  //
  //  // 自定义基于JWT的安全过滤器
  //  //    private final JwtAuthorizationTokenFilter authenticationTokenFilter;
  //
  //  //  @Value("${jwt.header}")
  //  //  private String tokenHeader;
  //
  //  public SecurityConfig(
  //      JwtUserDetailsService jwtUserDetailsService, ApplicationContext applicationContext) {
  //    this.jwtUserDetailsService = jwtUserDetailsService;
  //    this.applicationContext = applicationContext;
  //  }
  //
  //  @Autowired
  //  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  //    auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
  //  }
  //
  //  @Bean
  //  GrantedAuthorityDefaults grantedAuthorityDefaults() {
  //    // Remove the ROLE_ prefix
  //    return new GrantedAuthorityDefaults("");
  //  }
  //
  //  @Bean
  //  public PasswordEncoder passwordEncoderBean() {
  //    return new BCryptPasswordEncoder();
  //  }
  //
  //  @Bean
  //  @Override
  //  public AuthenticationManager authenticationManagerBean() throws Exception {
  //    return super.authenticationManagerBean();
  //  }
  //
  //  @Override
  //  protected void configure(HttpSecurity httpSecurity) throws Exception {
  //    // 搜寻 匿名标记 url： PreAuthorize("hasAnyRole('anonymous')") 和
  // PreAuthorize("@el.check('anonymous')")
  //    // 和 AnonymousAccess
  //    Map<RequestMappingInfo, HandlerMethod> handlerMethodMap =
  //        applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
  //    httpSecurity
  //        // 禁用 CSRF
  //        .csrf()
  //        .disable()
  //        // 授权异常
  //        //
  // .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
  //        // 不创建会话
  //        .sessionManagement()
  //        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  //        .and()
  //        .authorizeRequests()
  //        .antMatchers("/")
  //        .permitAll();
  //
  //            // 过滤请求
  //            .authorizeRequests()
  //            .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js")
  //            .anonymous()
  //            .antMatchers("/")
  //            .permitAll()
  //            // swagger start
  //            .antMatchers("/swagger-ui.html")
  //            .permitAll()
  //            .antMatchers("/swagger-resources/**")
  //            .permitAll()
  //            .antMatchers("/webjars/**")
  //            .permitAll()
  //            .antMatchers("/*/api-docs")
  //            .permitAll();
  //     swagger end
  //     文件
  //                    .antMatchers("/avatar/**").permitAll()
  //                    .antMatchers("/file/**").permitAll()
  //                    // 放行OPTIONS请求
  //                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
  //                    .antMatchers("/druid/**").permitAll()
  //                    // 自定义匿名访问所有url放行 ： 允许 匿名和带权限以及登录用户访问
  //                    .antMatchers(anonymousUrls.toArray(new String[0])).permitAll()
  //                    // 所有请求都需要认证
  //                    .anyRequest().permitAll()
  //                    // 防止iframe 造成跨域
  //                    .and().headers().frameOptions().disable();
  //            httpSecurity
  //                    .addFilterBefore(authenticationTokenFilter,
  //     UsernamePasswordAuthenticationFilter.class);
  //  }
  // }
