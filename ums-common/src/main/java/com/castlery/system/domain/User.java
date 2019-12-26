package com.castlery.system.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
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
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @author Zheng Jie
 * @date 2018-11-22
 */
@Entity
@Getter
@Setter
@Table(name = "sys_user")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull(groups = Update.class)
  private Long id;

  @NotBlank
  @Column(unique = true)
  private String username;

  @NotBlank
  @Pattern(
      regexp = "([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}",
      message = "格式错误")
  private String email;

  //  @NotBlank
  private String phone;

  //  @NotNull
  private Boolean enabled;

  private String password;

  @Column(name = "create_time")
  @CreationTimestamp
  private Timestamp createTime;

  @Column(name = "last_password_reset_time")
  private Date lastPasswordResetTime;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
  private Set<Role> roles;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_depts",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "dept_id", referencedColumnName = "id")})
  private Set<Dept> depts;

  public @interface Update {

  }
}
