package com.castlery.system.service;

import com.castlery.system.service.vo.RedisVo;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 可自行扩展
 *
 * @author Zheng Jie
 * @date 2018-12-10
 */
public interface RedisService {

  /**
   * findById
   *
   * @param key 键
   * @return /
   */
  Page findByKey(String key, Pageable pageable);

  /**
   * findById
   *
   * @param key 键
   * @return /
   */
  List<RedisVo> findByKey(String key);

  /**
   * 查询验证码的值
   *
   * @param key 键
   * @return /
   */
  String getCodeVal(String key);

  /**
   * 保存验证码
   *
   * @param key 键
   * @param val 值
   */
  void saveCode(String key, Object val);

  /**
   * delete
   *
   * @param key 键
   */
  void delete(String key);

  /** 清空缓存 */
  void deleteAll();

  /**
   * @param redisVos /
   * @param response /
   */
  void download(List<RedisVo> redisVos, HttpServletResponse response) throws IOException;
}
