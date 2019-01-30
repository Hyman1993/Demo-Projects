package com.penghuang.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 网站主页控制器
 * 包含:最新、最热文章、最新标签、最热用户等
 * @author penghuang
 * @since 1.0.0 2019/01
 *
 */
@Controller
public class MainController {
     
	   @GetMapping("/")
	   public String root() {
		   return "redirect:/index";
	   }
	   
	   @GetMapping("/index")
	   public String index() {
		   return "index";
	   }
	   @GetMapping("/login")
	   public String login() {
		   return "login";
	   }
	   @GetMapping("/login-error")
	   public String loginError(Model model) {
		   model.addAttribute("loginError", true);
		   model.addAttribute("errorMsg","登录失败,用户名或密码错误!");
		   return "login";
	   }
}
