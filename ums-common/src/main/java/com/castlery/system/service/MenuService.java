package com.castlery.system.service;

import com.castlery.system.domain.Menu;
import com.castlery.system.service.dto.MenuDTO;
import com.castlery.system.service.dto.MenuQueryCriteria;
import com.castlery.system.service.dto.RoleSmallDTO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
public interface MenuService {

  List<MenuDTO> queryAll(MenuQueryCriteria criteria);

  MenuDTO findById(long id);

  MenuDTO create(Menu resources);

  void update(Menu resources);

  Set<Menu> getDeleteMenus(List<Menu> menuList, Set<Menu> menuSet);

  Object getMenuTree(List<Menu> menus);

  List<Menu> findByPid(long pid);

  Map<String, Object> buildTree(List<MenuDTO> menuDTOS);

  List<MenuDTO> findByRoles(List<RoleSmallDTO> roles);

  Object buildMenus(List<MenuDTO> byRoles);

  Menu findOne(Long id);

  void delete(Set<Menu> menuSet);

  void download(List<MenuDTO> queryAll, HttpServletResponse response) throws IOException;
}
