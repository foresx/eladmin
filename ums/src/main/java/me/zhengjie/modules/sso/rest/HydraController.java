package me.zhengjie.modules.sso.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HydraController {
  @GetMapping
  public void login(@RequestParam("login_challenge") String challenge) {
    //    new AdminApi()
  }
}
