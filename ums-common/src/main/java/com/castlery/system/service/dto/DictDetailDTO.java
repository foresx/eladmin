package com.castlery.system.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zheng Jie
 * @date 2019-04-10
 */
@Getter
@Setter
public class DictDetailDTO implements Serializable {

  private Long id;

  private String label;

  private String value;

  private String sort;

  private Timestamp createTime;
}
