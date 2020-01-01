package com.castlery.system.service.dto;

import com.castlery.annotation.Query;
import java.sql.Timestamp;
import lombok.Data;

/**
 * @author Zheng Jie
 * @date 2019-03-25
 */
@Data
public class DeptQueryCriteria {

  //    @Query(type = Query.Type.IN, propName="id")
  //    private Set<Long> ids;

  @Query(type = Query.Type.INNER_LIKE)
  private String name;

  @Query private Boolean enabled;

  @Query private Long pid;

  @Query(type = Query.Type.GREATER_THAN, propName = "createTime")
  private Timestamp startTime;

  @Query(type = Query.Type.LESS_THAN, propName = "createTime")
  private Timestamp endTime;
}
