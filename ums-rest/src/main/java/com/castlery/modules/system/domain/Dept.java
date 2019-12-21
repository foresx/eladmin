package com.castlery.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Column;
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
import org.hibernate.annotations.CreationTimestamp;

/**
 * @author Zheng Jie
 * @date 2019-03-25
 */
@Entity
@Getter
@Setter
@Table(name = "dept")
public class Dept implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @NotNull(groups = Update.class)
  private Long id;

  @Column(name = "name", nullable = false)
  @NotBlank
  private String name;

  @Column(name = "code", nullable = false)
  @NotBlank
  private String code;

  @NotNull private Boolean enabled;

  @Column(name = "pid", nullable = false)
  @NotNull
  private Long pid;

  @JsonIgnore
  @ManyToMany(mappedBy = "depts")
  private Set<Role> roles;

  @ManyToMany(mappedBy = "depts", fetch = FetchType.EAGER)
  @JsonIgnore
  private Set<User> users;

  @Column(name = "create_time")
  @CreationTimestamp
  private Timestamp createTime;

  public @interface Update {}
}
