package me.zhengjie.modules.monitor.domain.vo;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zheng Jie
 * @date 2018-12-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisVo implements Serializable {

  @NotBlank private String key;

  @NotBlank private String value;
}
