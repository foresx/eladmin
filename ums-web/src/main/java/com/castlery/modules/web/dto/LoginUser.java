package com.castlery.modules.web.dto;

import lombok.Data;

@Data
public class LoginUser {

  private String email;
  private String password;
  private String challenge;
}
