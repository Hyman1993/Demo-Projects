package com.hyman.springboot_redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyman.springboot_redis.service.RedisService;

/**
 * @author hyman
 * @date 2019/05/21
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisController {
	  /**
	   * 注入redisTemplate bean
	   */
	  @Autowired
	  private RedisService redisService;
	  
	  /**
	   * 保存(更新)数据
	   * @param key
	   * @param value
	   * @return
	   */
	  @RequestMapping(value="/add",method=RequestMethod.GET)
	  public String add(String key,String value) {
		  redisService.set(key, value);
		  
		  return "add successfully";
	  }
	  
	  /**
	   * 删除指定数据
	   * @param key
	   * @return
	   */
	  @GetMapping(value="/delete")
	  public String delete(String key) {
		  
		  redisService.del(key);
		  return "delete successfully";
	  }
	  
	  /**
	   * 读取指定数据
	   * @param key
	   * @return
	   */
	  @GetMapping(value="/get")
	  public String get(String key) {
		  return redisService.get(key)==null ? "data don't exist!" : redisService.get(key).toString();
	  }
}
