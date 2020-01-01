package com.castlery.modules.web.service.handler;

import com.castlery.modules.web.dto.Constant;
import com.castlery.modules.web.service.HydraService;
import com.castlery.system.service.UserService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  //  private TokenProvider tokenProvider;
  //
  //  private AppProperties appProperties;

  //  private HttpCookieOAuth2AuthorizationRequestRepository
  //      httpCookieOAuth2AuthorizationRequestRepository;
  @Autowired
  private UserService userService;

  @Autowired
  private HttpSession session;

  @Autowired
  private HydraService hydraService;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {

    String challenge = request.getParameter(Constant.OAUTH2_STATE);
    if (session.getAttribute(Constant.CHALLENGE).toString().equals(challenge)) {
      String userEmail = session.getAttribute(Constant.AZURE_USER_EMAIL).toString();
      userService.createOauth2User(userEmail);
      String redirectUrl = hydraService.acceptLogin(challenge, userEmail, response);
      response.sendRedirect(redirectUrl);
    }
    //    request.getHeader()
    //    String header = request.getHeader("");
    //    String targetUrl = determineTargetUrl(request, response);
    //    if (response.isCommitted()) {
    //      logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
    //      //      return;
    //    }

    //    targetUrl = "http://localhost:8080/registration";
    // todo
    //    targetUrl = "https://cowboy.castlery.com/";
    //    clearAuthenticationAttributes(request, response);
    //    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
  //
  //  protected String determineTargetUrl(
  //      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
  //    Optional<String> redirectUri =
  //        CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);
  //
  //    if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
  //      throw new BadRequestException(
  //          "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the
  // authentication");
  //    }
  //
  //    String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
  //
  //    String token = tokenProvider.createToken(authentication);
  //
  //    return UriComponentsBuilder.fromUriString(targetUrl)
  //        .queryParam("token", token)
  //        .build()
  //        .toUriString();
  //  }
  //
  //  protected void clearAuthenticationAttributes(
  //      HttpServletRequest request, HttpServletResponse response) {
  //    super.clearAuthenticationAttributes(request);
  //    httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(
  //        request, response);
  //  }
  //
  //  private boolean isAuthorizedRedirectUri(String uri) {
  //    URI clientRedirectUri = URI.create(uri);
  //
  //    return appProperties.getOauth2().getAuthorizedRedirectUris().stream()
  //        .anyMatch(
  //            authorizedRedirectUri -> {
  //              // Only validate host and port. Let the clients use different paths if they want
  // to
  //              URI authorizedURI = URI.create(authorizedRedirectUri);
  //              if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
  //                  && authorizedURI.getPort() == clientRedirectUri.getPort()) {
  //                return true;
  //              }
  //              return false;
  //            });
  //  }
}
