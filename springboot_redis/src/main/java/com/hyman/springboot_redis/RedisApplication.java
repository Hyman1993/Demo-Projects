package com.hyman.springboot_redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hyman
 * @date 2019/05/21
 */
@SpringBootApplication
public class RedisApplication {
  public static void main(String[] args) {
    SpringApplication.run(RedisApplication.class, args);
  }
}
