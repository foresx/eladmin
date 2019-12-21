package com.castlery.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Getter
@Setter
public class UserDTO implements Serializable {

  @ApiModelProperty(hidden = true)
  private Long id;

  private String username;

  //    private String avatar;

  private String email;

  private String phone;

  private Boolean enabled;

  @JsonIgnore private String password;

  private Date lastPasswordResetTime;

  @ApiModelProperty(hidden = true)
  private Set<RoleSmallDTO> roles;

  //    private DeptSmallDTO dept;

  @ApiModelProperty(hidden = true)
  private Set<DeptSmallDTO> depts;

  //    private Long deptId;

  private Timestamp createTime;
}
