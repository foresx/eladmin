package com.castlery.modules.web.rest;

import com.castlery.modules.web.dto.Constant;
import com.castlery.modules.web.dto.LoginUser;
import com.castlery.modules.web.service.HydraService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class HydraController {

  @Autowired
  private HydraService hydraService;

  @GetMapping(value = "/login")
  public ModelAndView login(@RequestParam("login_challenge") String challenge,
      HttpServletResponse response) {
    try {
      return hydraService.login(challenge, response);
    } catch (IOException e) {
      log.error("Redirect to hydra error.");
      return new ModelAndView(Constant.LOGIN_UI);
    }
  }

  @PostMapping("/custom/login")
  public ModelAndView formLogin(@ModelAttribute("userForm") LoginUser userForm,
      HttpServletResponse response) {
    try {
      hydraService.login(userForm, response);
      return new ModelAndView();
    } catch (IOException e) {
      log.error("Redirect to hydra error.");
      return new ModelAndView(Constant.LOGIN_UI);
    }
  }

  @GetMapping("/consent")
  public void consent(@RequestParam("consent_challenge") String challenge,
      HttpServletResponse response) {
    hydraService.consent(challenge, response);
  }

  @GetMapping("/logout")
  public void logout(@RequestParam("logout_challenge") String challenge,
      HttpServletResponse response) {
    log.info("challenge:  {}", challenge);
    hydraService.logout(challenge, response);
  }

  @GetMapping("/logout-successful")
  public ModelAndView logoutSuccessfulHandler() {
    return new ModelAndView("logout-success");
  }
}
