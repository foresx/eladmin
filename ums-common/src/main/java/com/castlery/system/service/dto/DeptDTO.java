package com.castlery.system.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zheng Jie
 * @date 2019-03-25
 */
@Getter
@Setter
public class DeptDTO implements Serializable {

  // ID
  private Long id;

  // 名称
  private String name;

  private String code;

  @NotNull private Boolean enabled;

  // 上级部门
  private Long pid;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<DeptDTO> children;

  private Timestamp createTime;

  public String getLabel() {
    return name;
  }
}
