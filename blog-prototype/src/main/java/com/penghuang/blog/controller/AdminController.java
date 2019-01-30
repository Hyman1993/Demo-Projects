package com.penghuang.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户后台超级权限管理控制器.
 * 
 * @since 1.0.0 2019/01/19
 * @author penghuang
 */
@Controller
@RequestMapping("/admins")
public class AdminController {
	/**
	 * 获取后台管理主页
	 * @param model
	 * @return
	 */
	@GetMapping
	public ModelAndView listUsers(Model model) {
		return new ModelAndView("admins/index","menuList",model);
	}
}
