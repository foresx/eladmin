package com.castlery.config.security;

import com.castlery.config.oauth2.GrantedAuthoritiesExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableWebSecurity
@Profile("resource-server")
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private GrantedAuthoritiesExtractor grantedAuthoritiesExtractor;

  private static final String UMS_AUTHORITY = "UMS";


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement()
        .disable()
        .csrf()
        .disable()
        .authorizeRequests()
        .requestMatchers(EndpointRequest.toAnyEndpoint())
        .permitAll()
        .and()
        .authorizeRequests()
        .anyRequest()
        .hasAuthority(UMS_AUTHORITY)
        .and()
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(grantedAuthoritiesExtractor());
  }

  Converter<Jwt, AbstractAuthenticationToken> grantedAuthoritiesExtractor() {
    JwtAuthenticationConverter jwtAuthenticationConverter =
        new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesExtractor);
    return jwtAuthenticationConverter;
  }


  @Bean
  public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
    return new SecurityEvaluationContextExtension();
  }
}
