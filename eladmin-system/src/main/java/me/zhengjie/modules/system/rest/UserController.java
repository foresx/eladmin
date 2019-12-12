package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.aop.log.Log;
import me.zhengjie.config.DataScope;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.vo.UserPassVo;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.RoleSmallDTO;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import me.zhengjie.utils.EncryptUtils;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Api(tags = "系统：用户管理")
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  private final DataScope dataScope;

  private final DeptService deptService;

  private final RoleService roleService;

  //    private final VerificationCodeService verificationCodeService;

  public UserController(
      UserService userService,
      DataScope dataScope,
      DeptService deptService,
      RoleService roleService) {
    this.userService = userService;
    this.dataScope = dataScope;
    this.deptService = deptService;
    this.roleService = roleService;
    //        this.verificationCodeService = verificationCodeService;
  }

  @Log("导出用户数据")
  @ApiOperation("导出用户数据")
  @GetMapping(value = "/download")
  public void download(HttpServletResponse response, UserQueryCriteria criteria)
      throws IOException {
    userService.download(userService.queryAll(criteria), response);
  }

  @Log("查询用户")
  @ApiOperation("查询用户")
  @GetMapping
  public ResponseEntity getUsers(UserQueryCriteria criteria, Pageable pageable) {
    Set<Long> deptSet = new HashSet<>();
    Set<Long> result = new HashSet<>();
    if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
      deptSet.add(criteria.getDeptId());
      deptSet.addAll(dataScope.getDeptChildren(deptService.findByPid(criteria.getDeptId())));
    }
    // 数据权限
    Set<Long> deptIds = dataScope.getDeptIds();
    // 查询条件不为空并且数据权限不为空则取交集
    if (!CollectionUtils.isEmpty(deptIds) && !CollectionUtils.isEmpty(deptSet)) {
      // 取交集
      result.addAll(deptSet);
      result.retainAll(deptIds);
      // 若无交集，则代表无数据权限
      criteria.setDeptIds(result);
      if (result.size() == 0) {
        return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
      } else return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
      // 否则取并集
    } else {
      result.addAll(deptSet);
      result.addAll(deptIds);
      criteria.setDeptIds(result);
      return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
    }
  }

  @Log("新增用户")
  @ApiOperation("新增用户")
  @PostMapping
  public ResponseEntity create(@Validated @RequestBody User resources) {
    checkLevel(resources);
    return new ResponseEntity<>(userService.create(resources), HttpStatus.CREATED);
  }

  @Log("修改用户")
  @ApiOperation("修改用户")
  @PutMapping
  public ResponseEntity update(@Validated(User.Update.class) @RequestBody User resources) {
    checkLevel(resources);
    userService.update(resources);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @Log("删除用户")
  @ApiOperation("删除用户")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    Integer currentLevel =
        Collections.min(
            roleService.findByUsers_Id(SecurityUtils.getUserId()).stream()
                .map(RoleSmallDTO::getLevel)
                .collect(Collectors.toList()));
    Integer optLevel =
        Collections.min(
            roleService.findByUsers_Id(id).stream()
                .map(RoleSmallDTO::getLevel)
                .collect(Collectors.toList()));

    if (currentLevel > optLevel) {
      throw new BadRequestException("角色权限不足");
    }
    userService.delete(id);
    return new ResponseEntity(HttpStatus.OK);
  }

  @ApiOperation("修改密码")
  @PostMapping(value = "/updatePass")
  public ResponseEntity updatePass(@RequestBody UserPassVo user) {
    UserDetails userDetails = SecurityUtils.getUserDetails();
    if (!userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getOldPass()))) {
      throw new BadRequestException("修改失败，旧密码错误");
    }
    if (userDetails.getPassword().equals(EncryptUtils.encryptPassword(user.getNewPass()))) {
      throw new BadRequestException("新密码不能与旧密码相同");
    }
    userService.updatePass(
        userDetails.getUsername(), EncryptUtils.encryptPassword(user.getNewPass()));
    return new ResponseEntity(HttpStatus.OK);
  }

  /**
   * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
   *
   * @param resources /
   */
  private void checkLevel(User resources) {
    Integer currentLevel =
        Collections.min(
            roleService.findByUsers_Id(SecurityUtils.getUserId()).stream()
                .map(RoleSmallDTO::getLevel)
                .collect(Collectors.toList()));
    Integer optLevel = roleService.findByRoles(resources.getRoles());
    if (currentLevel > optLevel) {
      throw new BadRequestException("角色权限不足");
    }
  }
}
