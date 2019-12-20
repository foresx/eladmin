package me.zhengjie.modules.system.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.modules.system.domain.enums.MenuType;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Getter
@Setter
public class MenuDTO implements Serializable {

  private Long id;

  private MenuType type;

  private String permission;

  private String name;

  private Long pid;

  private String desc;

  private List<MenuDTO> children;

  private Timestamp createTime;
}
