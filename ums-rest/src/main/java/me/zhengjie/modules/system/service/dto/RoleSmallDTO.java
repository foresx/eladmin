package me.zhengjie.modules.system.service.dto;

import java.io.Serializable;
import lombok.Data;
import me.zhengjie.modules.system.domain.enums.ScopeType;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Data
public class RoleSmallDTO implements Serializable {

  private Long id;

  private String name;

  private Integer level;

  private ScopeType dataScope;
}
