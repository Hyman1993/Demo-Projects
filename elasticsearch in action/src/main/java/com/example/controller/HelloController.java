package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
   @RequestMapping(path="/hello")
   public String hello(){
	   System.out.println("hello springboot");
	   return "Hello world!";
   }
}