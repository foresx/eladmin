package com.castlery.config.oauth2;

import com.castlery.modules.web.dto.Constant;
import com.castlery.utils.StringUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest.Builder;

public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

  private final HttpSession session;
  private final OAuth2AuthorizationRequestResolver defaultAuthorizationRequestResolver;

  public CustomAuthorizationRequestResolver(
      ClientRegistrationRepository clientRegistrationRepository, HttpSession session) {
    this.session = session;
    this.defaultAuthorizationRequestResolver =
        new DefaultOAuth2AuthorizationRequestResolver(
            clientRegistrationRepository, "/oauth2/authorization");
  }

  @Override
  public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
    OAuth2AuthorizationRequest authorizationRequest =
        this.defaultAuthorizationRequestResolver.resolve(request);
    String challenge = getChallengeStr(request);
    return authorizationRequest != null ? customAuthorizationRequest(authorizationRequest,
        challenge) : null;
  }

  @Override
  public OAuth2AuthorizationRequest resolve(HttpServletRequest request,
      String clientRegistrationId) {

    OAuth2AuthorizationRequest authorizationRequest =
        this.defaultAuthorizationRequestResolver.resolve(
            request, clientRegistrationId);
    String challenge = getChallengeStr(request);
    return authorizationRequest != null ?
        customAuthorizationRequest(authorizationRequest, challenge) : null;
  }

  private String getChallengeStr(HttpServletRequest request) {
    String challenge = request.getParameter(Constant.CHALLENGE);
    if (null != challenge) {
      session.setAttribute(Constant.CHALLENGE, challenge);
    }
    return challenge;
  }

  private OAuth2AuthorizationRequest customAuthorizationRequest(
      OAuth2AuthorizationRequest authorizationRequest, String challenge) {

    Map<String, Object> additionalParameters =
        new LinkedHashMap<>(authorizationRequest.getAdditionalParameters());
    Builder builder = OAuth2AuthorizationRequest.from(authorizationRequest)
        .additionalParameters(additionalParameters);
    if (StringUtils.isNotBlank(challenge)) {
      builder.state(challenge);
    }
    return builder.build();
  }
}

