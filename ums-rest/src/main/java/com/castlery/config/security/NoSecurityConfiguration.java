package com.castlery.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Profile("noSecurity")
@Import(SecurityProblemSupport.class)
@EnableWebSecurity
public class NoSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityProblemSupport problemSupport;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .httpBasic()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(problemSupport)
        .accessDeniedHandler(problemSupport)
        .and()
        .authorizeRequests()
        .anyRequest()
        .permitAll();
  }
}
