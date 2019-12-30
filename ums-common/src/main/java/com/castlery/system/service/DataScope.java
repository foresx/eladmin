package com.castlery.system.service;

import com.castlery.system.domain.Dept;
import com.castlery.system.domain.enums.ScopeType;
import com.castlery.system.service.DeptService;
import com.castlery.system.service.RoleService;
import com.castlery.system.service.UserService;
import com.castlery.system.service.dto.DeptSmallDTO;
import com.castlery.system.service.dto.RoleSmallDTO;
import com.castlery.system.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * 数据权限配置
 *
 * @author Zheng Jie
 * @date 2019-4-1
 */
@Component
public class DataScope {

  //  private final String[] scopeType = {"全部", "本级", "自定义"};

  private final UserService userService;

  private final RoleService roleService;

  private final DeptService deptService;

  public DataScope(UserService userService, RoleService roleService, DeptService deptService) {
    this.userService = userService;
    this.roleService = roleService;
    this.deptService = deptService;
  }

  public Set<Long> getDeptIds(UserDTO user) {

    //    UserDTO user = userService.findByName(SecurityUtils.getUsername());

    // 用于存储部门id
    Set<Long> deptIds = new HashSet<>();

    // 查询用户角色
    List<RoleSmallDTO> roleSet = roleService.findByUsers_Id(user.getId());

    for (RoleSmallDTO role : roleSet) {

      if (ScopeType.SELF_LEVEL == role.getDataScope()) {
        return new HashSet<>();
      }

      // 存储本级的数据权限
      if (ScopeType.DEPT_LEVEL == role.getDataScope()) {
        deptIds.addAll(
            user.getDepts().stream().map(DeptSmallDTO::getId).collect(Collectors.toList()));
      }

      // 存储自定义的数据权限
      if (ScopeType.CUSTOMIZE_LEVEL == role.getDataScope()) {
        Set<Dept> depts = deptService.findByRoleIds(role.getId());
        for (Dept dept : depts) {
          deptIds.add(dept.getId());
          List<Dept> deptChildren = deptService.findByPid(dept.getId());
          if (deptChildren != null && deptChildren.size() != 0) {
            deptIds.addAll(getDeptChildren(deptChildren));
          }
        }
      }
    }
    return deptIds;
  }

  private List<Long> getDeptChildren(List<Dept> deptList) {
    List<Long> list = new ArrayList<>();
    deptList.forEach(
        dept -> {
          if (dept != null && dept.getEnabled()) {
            List<Dept> depts = deptService.findByPid(dept.getId());
            if (deptList.size() != 0) {
              list.addAll(getDeptChildren(depts));
            }
            list.add(dept.getId());
          }
        });
    return list;
  }
}
