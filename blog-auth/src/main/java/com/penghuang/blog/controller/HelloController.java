package com.penghuang.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
   @RequestMapping(value="/hello")
   public String hello(){
	   System.out.println("hello springboot");
	   return "Hello world!";
   }
}
