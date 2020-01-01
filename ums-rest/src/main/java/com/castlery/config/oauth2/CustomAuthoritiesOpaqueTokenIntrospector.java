//package com.castlery.config.oauth2;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
//import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
//import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionClaimNames;
//import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthoritiesOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
//  private OpaqueTokenIntrospector delegate =
//      new NimbusOpaqueTokenIntrospector(
//          "http://localhost:9001/oauth2/introspect", "facebook-photo-backup", "some-secret");
//
//  @Autowired
//  private RestTemplateBuilder restTemplateBuilder;
//
//  @Override
//  public OAuth2AuthenticatedPrincipal introspect(String token) {
//    //    OAuth2AuthenticatedPrincipal principal = this.delegate.introspect(token);
//    //    return new DefaultOAuth2AuthenticatedPrincipal(
//    //        principal.getName(), principal.getAttributes(), extractAuthorities(principal));
//
//    OAuth2AuthenticatedPrincipal principal = this.delegate.introspect(token);
//    //    Instant issuedAt = authorized.getAttribute(ISSUED_AT);
//    //    Instant expiresAt = authorized.getAttribute(EXPIRES_AT);
//    //    ClientRegistration clientRegistration =
//    // this.repository.findByRegistrationId("registration-id");
//    //    OAuth2AccessToken token = new OAuth2AccessToken(BEARER, token, issuedAt, expiresAt);
//    //    OAuth2UserRequest oauth2UserRequest = new OAuth2UserRequest(clientRegistration, token);
//    String str =
//        restTemplateBuilder.build().getForObject("http://localhost:8080/api/regions", String.class);
//
//    //    return this.oauth2UserService.loadUser(oauth2UserRequest);
//
//    return new CustomerOAuth2AuthenticatedPrincipal(
//        principal.getName(), principal.getAttributes(), extractAuthorities(principal), str);
//  }
//
//  private Collection<GrantedAuthority> extractAuthorities(OAuth2AuthenticatedPrincipal principal) {
//    List<String> scopes = principal.getAttribute(OAuth2IntrospectionClaimNames.SCOPE);
//    ArrayList<String> all = new ArrayList<>();
//    all.addAll(scopes);
//    all.add("lxzlxlz");
//    return all.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//  }
//}