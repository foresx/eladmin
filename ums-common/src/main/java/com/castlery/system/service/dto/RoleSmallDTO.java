package com.castlery.system.service.dto;

import com.castlery.system.domain.enums.ScopeType;
import java.io.Serializable;
import lombok.Data;

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
