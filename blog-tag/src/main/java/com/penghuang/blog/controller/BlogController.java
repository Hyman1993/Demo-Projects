package com.penghuang.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 博客文章 控制器
 * @author penghuang
 * @since 1.0.0 2019/01
 *
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {
	@GetMapping
	public String listBlogs(@RequestParam(value="order",required=false,defaultValue="new") String order,
			@RequestParam(value="keyword",required = false,defaultValue="") Long keyword){
		System.out.println("order:"+order+";keyword:"+keyword);
		return "redirect:/index?order="+order+"&keyword="+keyword;
	}
}
