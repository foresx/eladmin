package com.castlery.system.service.dto;

import com.castlery.system.domain.enums.ScopeType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Getter
@Setter
public class RoleDTO implements Serializable {

  private Long id;

  private String name;

  private ScopeType dataScope;

  private Integer level;

  private String remark;

  private String code;

  private String permission;

  private Set<MenuDTO> menus;

  private Set<DeptDTO> depts;

  private Timestamp createTime;
}
