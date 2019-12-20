package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.service.MenuService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.MenuDTO;
import me.zhengjie.modules.system.service.dto.MenuQueryCriteria;
import me.zhengjie.modules.system.service.dto.UserDTO;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * @date 2018-12-03
 */
@Api(tags = "系统：菜单管理")
@RestController
@RequestMapping("/api/menus")
@SuppressWarnings("unchecked")
public class MenuController {

  private final MenuService menuService;

  private final UserService userService;

  private final RoleService roleService;

  private static final String ENTITY_NAME = "menu";

  public MenuController(MenuService menuService, UserService userService, RoleService roleService) {
    this.menuService = menuService;
    this.userService = userService;
    this.roleService = roleService;
  }

  @Log("导出菜单数据")
  @ApiOperation("导出菜单数据")
  @GetMapping(value = "/download")
  public void download(HttpServletResponse response, MenuQueryCriteria criteria)
      throws IOException {
    menuService.download(menuService.queryAll(criteria), response);
  }

//  @ApiOperation("获取前端所需菜单")
//  @GetMapping(value = "/build")
//  public ResponseEntity buildMenus() {
//    UserDTO user = userService.findByName(SecurityUtils.getUsername());
//    List<MenuDTO> menuDTOList = menuService.findByRoles(roleService.findByUsers_Id(user.getId()));
//    List<MenuDTO> menuDTOS = (List<MenuDTO>) menuService.buildTree(menuDTOList).get("content");
//    return new ResponseEntity<>(menuService.buildMenus(menuDTOS), HttpStatus.OK);
//  }

  @ApiOperation("返回全部的菜单")
  @GetMapping(value = "/tree")
  public ResponseEntity getMenuTree() {
    return new ResponseEntity<>(menuService.getMenuTree(menuService.findByPid(0L)), HttpStatus.OK);
  }

  @Log("查询菜单")
  @ApiOperation("查询菜单")
  @GetMapping
  public ResponseEntity getMenus(MenuQueryCriteria criteria) {
    List<MenuDTO> menuDTOList = menuService.queryAll(criteria);
    return new ResponseEntity<>(menuService.buildTree(menuDTOList), HttpStatus.OK);
  }

  @Log("新增菜单")
  @ApiOperation("新增菜单")
  @PostMapping
  public ResponseEntity create(@Validated @RequestBody Menu resources) {
    if (resources.getId() != null) {
      throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
    }
    return new ResponseEntity<>(menuService.create(resources), HttpStatus.CREATED);
  }

  @Log("修改菜单")
  @ApiOperation("修改菜单")
  @PutMapping
  public ResponseEntity update(@Validated(Menu.Update.class) @RequestBody Menu resources) {
    menuService.update(resources);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @Log("删除菜单")
  @ApiOperation("删除菜单")
  @DeleteMapping(value = "/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    List<Menu> menuList = menuService.findByPid(id);
    Set<Menu> menuSet = new HashSet<>();
    menuSet.add(menuService.findOne(id));
    menuSet = menuService.getDeleteMenus(menuList, menuSet);
    menuService.delete(menuSet);
    return new ResponseEntity(HttpStatus.OK);
  }
}
