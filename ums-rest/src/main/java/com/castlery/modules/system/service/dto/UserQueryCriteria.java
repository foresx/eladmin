package com.castlery.modules.system.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import lombok.Data;
import com.castlery.annotation.Query;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Data
public class UserQueryCriteria implements Serializable {

  @Query private Long id;

  @Query(propName = "id", type = Query.Type.IN, joinName = "depts")
  private Set<Long> deptIds;

  // 多字段模糊
  @Query(blurry = "email,username")
  private String blurry;

  @Query private Boolean enabled;

  @Query(type = Query.Type.GREATER_THAN, propName = "createTime")
  private Timestamp startTime;

  @Query(type = Query.Type.LESS_THAN, propName = "createTime")
  private Timestamp endTime;
}
