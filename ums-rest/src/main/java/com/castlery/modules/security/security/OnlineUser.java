package com.castlery.modules.security.security;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUser {

  private String userName;

  private String job;

  private String browser;

  private String ip;

  private String address;

  private String key;

  private Date loginTime;
}
