package com.castlery.system.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zheng Jie
 * @date 2019-04-10
 */
@Getter
@Setter
public class DictDTO implements Serializable {

  private Long id;

  private String name;

  private String remark;

  private List<DictDetailDTO> dictDetails;

  private Timestamp createTime;
}
