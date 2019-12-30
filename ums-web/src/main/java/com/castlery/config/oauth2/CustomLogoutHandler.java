package com.castlery.config.oauth2;

import com.castlery.modules.web.service.HydraService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

  @Autowired
  private HydraService hydraService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    String logoutChallenge = request.getParameter("logout_challenge");
    hydraService.logout(logoutChallenge, response);
  }
}
