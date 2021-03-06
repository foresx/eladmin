package com.castlery.system.domain;

import com.castlery.system.domain.enums.ScopeType;
import com.castlery.system.domain.enums.converter.ScopeTypeConverter;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

/**
 * 角色
 *
 * @author Zheng Jie
 * @date 2018-11-22
 */
@Entity
@Table(name = "role")
@Getter
@Setter
public class Role implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull(groups = {Update.class})
  private Long id;

  @Column(nullable = false)
  @NotBlank
  private String name;

  // 数据权限类型 全部 、 本级 、 自定义
  @Column(name = "data_scope")
  @Convert(converter = ScopeTypeConverter.class)
  private ScopeType dataScope = ScopeType.SELF_LEVEL;

  // 数值越小，级别越大
  @Column(name = "level")
  private Integer level = 3;

  @Column
  private String remark;

  @NotNull
  private String code;

  //    // 权限
  //    @Column(name = "permission")
  //    private String permission;

  @JsonIgnore
  @ManyToMany(mappedBy = "roles")
  private Set<User> users;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "roles_permissions",
      joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
  private Set<Menu> menus;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "roles_depts",
      joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "dept_id", referencedColumnName = "id")})
  private Set<Dept> depts;

  @Column(name = "create_time")
  @CreationTimestamp
  private Timestamp createTime;

  public @interface Update {

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Role role = (Role) o;
    return Objects.equals(id, role.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
