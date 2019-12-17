package me.zhengjie.modules.system.repository;

import java.util.Set;
import me.zhengjie.modules.system.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

  Role findByName(String name);

  Set<Role> findByUsers_Id(Long id);

  @Modifying
  @Query(value = "delete from roles_permissions where permission_id = ?1", nativeQuery = true)
  void untiedMenu(Long id);
}
