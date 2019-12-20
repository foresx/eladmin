package me.zhengjie.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.modules.system.domain.enums.MenuType;
import me.zhengjie.modules.system.domain.enums.converter.MenuTypeConverter;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @author Zheng Jie
 * @date 2018-12-17
 */
@Entity
@Getter
@Setter
@Table(name = "permission")
public class Menu implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull(groups = {Update.class})
  private Long id;

  @NotBlank private String name;

  // 类型
  @Column(name = "type")
  @Convert(converter = MenuTypeConverter.class)
  private MenuType type;

  // 权限
  @Column(name = "code", unique = true)
  private String permission;

  @Column(name = "description")
  private String desc;

  // 上级菜单ID
  @Column(name = "pid", nullable = false)
  private Long pid;

  @ManyToMany(mappedBy = "menus", fetch = FetchType.EAGER)
  @JsonIgnore
  private Set<Role> roles;

  @Column(name = "create_time")
  @CreationTimestamp
  private Timestamp createTime;

  public @interface Update {}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Menu menu = (Menu) o;
    return Objects.equals(id, menu.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
