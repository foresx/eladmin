package com.castlery.modules.security.service;

import com.castlery.modules.security.security.JwtUser;
import com.castlery.modules.system.service.UserService;
import com.castlery.modules.system.service.dto.UserDTO;
import com.castlery.exception.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zheng Jie
 * @date 2018-11-22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JwtUserDetailsService implements UserDetailsService {

  private final UserService userService;

  public JwtUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {

    UserDTO user = userService.findByName(username);
    if (user == null) {
      throw new BadRequestException("账号不存在");
    } else {
      return createJwtUser(user);
    }
  }

  public UserDetails createJwtUser(UserDTO user) {
    return new JwtUser(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        null,
        user.getEmail(),
        user.getPhone(),
        null,
        //        Optional.ofNullable(user.getDept()).map(DeptSmallDTO::getName).orElse(null),
        // todo delete job
        null,
        //                permissionService.mapToGrantedAuthorities(user),
        // todo 应该改成应该有的权限
        null,
        user.getEnabled(),
        user.getCreateTime(),
        user.getLastPasswordResetTime());
  }
}
