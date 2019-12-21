package com.castlery.base;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDTO implements Serializable {

  private Boolean isDelete;

  private Timestamp createTime;

  private Timestamp updateTime;
}
