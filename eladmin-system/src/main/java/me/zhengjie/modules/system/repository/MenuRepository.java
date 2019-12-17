package me.zhengjie.modules.system.repository;

import java.util.LinkedHashSet;
import java.util.List;
import me.zhengjie.modules.system.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

  Menu findByName(String name);

  Menu findByComponentName(String name);

  List<Menu> findByPid(long pid);

  LinkedHashSet<Menu> findByRolesIdAndTypeIsNotOrderBySortAsc(Long id, Integer type);
}
