package com.castlery.config;

import java.util.Arrays;

// @Service(value = "el")
public class ElPermissionConfig {

  public Boolean check(String... permissions) {
    // 如果是匿名访问的，就放行
    String anonymous = "anonymous";
    if (Arrays.asList(permissions).contains(anonymous)) {
      return true;
    }
    //        // 获取当前用户的所有权限
    //        List<String> elPermissions =
    // SecurityUtils.getUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    //        // 判断当前用户的所有权限是否包含接口上定义的权限
    //        return elPermissions.contains("admin") ||
    // Arrays.stream(permissions).anyMatch(elPermissions::contains);
    return true;
  }
}
