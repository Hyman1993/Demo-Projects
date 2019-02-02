package com.penghuang.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.penghuang.blog.domain.Authority;
import com.penghuang.blog.domain.User;
import com.penghuang.blog.service.AuthorityService;
import com.penghuang.blog.service.UserService;
import com.penghuang.blog.util.ConstraintViolationExceptionHandler;
import com.penghuang.blog.vo.Response;

/**
 * 主页控制器.
 * 
 * @since 1.0.0 2017年3月8日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Controller
public class MainController {
	
	private static final Long ROLE_USER_AUTHORITY_ID = 2L;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService  authorityService;
	
	@GetMapping("/")
	public String root() {
	    return "redirect:/index";
	}
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * 获取登录界面
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登陆失败，账号或者密码错误！");
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	/**
	 * 注册用户
	 * @param user
	 * @param result
	 * @param redirect
	 * @return
	 */
	@PostMapping("/register")
	public String registerUser(User user,Model model) {
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
		user.setAuthorities(authorities);
		
		try {
		    userService.saveUser(user);
		} catch (ConstraintViolationException e)  {
			// 捕获bean验证的异常信息,并设置到页面
			List<String> errorMsgList = new ArrayList<>();
			for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
				errorMsgList.add(constraintViolation.getMessage());
	        }
			model.addAttribute("errorMsgList",errorMsgList);
			return "register";
		} catch (Exception e) {
			model.addAttribute("errorMsgList",e);
			return "register";
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/search")
	public String search() {
		return "search";
	}
}
