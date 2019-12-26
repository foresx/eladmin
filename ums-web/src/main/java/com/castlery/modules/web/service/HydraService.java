package com.castlery.modules.web.service;

import com.castlery.modules.web.dto.LoginUser;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public interface HydraService {

  ModelAndView login(String challenge, HttpServletResponse response) throws IOException;

  void login(LoginUser user, HttpServletResponse response) throws IOException;

  String acceptLogin(String challenge, String subject, HttpServletResponse response)
      throws IOException;

  void consent(String challenge, HttpServletResponse response);
}
