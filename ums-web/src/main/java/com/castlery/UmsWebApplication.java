package com.castlery;

import com.castlery.utils.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Zheng Jie
 * @date 2018/11/15 9:20:19
 */
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
public class UmsWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(UmsWebApplication.class, args);
  }

  @Bean
  public SpringContextHolder springContextHolder() {
    return new SpringContextHolder();
  }
}
