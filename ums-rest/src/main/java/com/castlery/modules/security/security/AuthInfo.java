package com.castlery.modules.security.security;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zheng Jie
 * @date 2018-11-23 返回token
 */
@Getter
@AllArgsConstructor
public class AuthInfo implements Serializable {

  private final String token;

  private final JwtUser user;
}
