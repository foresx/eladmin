package com.castlery.system.service.dto;

import com.castlery.annotation.Query;
import lombok.Data;

/** 公共查询类 */
@Data
public class DictQueryCriteria {

  // 多字段模糊
  @Query(blurry = "name,remark")
  private String blurry;
}
