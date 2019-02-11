package com.penghuang.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.penghuang.blog.domain.Authority;
import com.penghuang.blog.domain.InvitationCode;
import com.penghuang.blog.domain.User;
import com.penghuang.blog.service.AuthorityService;
import com.penghuang.blog.service.InvitationCodeService;
import com.penghuang.blog.service.UserService;

/**
 * 主页控制器.
 * 
 * @since 1.0.0 2019年2月11日
 * @author penghuang
 */
@Controller
public class MainController {
	
	private static final Long ROLE_USER_AUTHORITY_ID = 2L;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService  authorityService;
	
	@Autowired
	private InvitationCodeService  invitationCodeService;
	
	@GetMapping("/")
	public String root() {
	    return "redirect:/index";
	}
	
	@GetMapping("/index")
	public String index() {
		return "redirect:/blogs";
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
     *  用户注册
     * @param user 用户注册信息
     * @param invitationCode 邀请码
     * @param model
     * @return
     */
	@PostMapping("/register")
	public String registerUser(User user,@RequestParam(value="invitationCode",required=false,defaultValue="" )String invitationCode,Model model) {
		// 存储注册异常信息
		List<String> errorMsgList = null;
		
		// 先进行邀请码的验证，如果邀请码无效，则返回注册页面重新注册
		InvitationCode invitationCodeObj = new InvitationCode();
		invitationCodeObj.setCode(invitationCode);
		invitationCodeObj.setStatus("1");//1为活性
		
		// 创建 Example。
        Example<InvitationCode> invitationCodeExample = Example.of(invitationCodeObj);
		
        // 检索这条邀请码是否存在并且有效
        Optional<InvitationCode> optionalInvitationCode = invitationCodeService.getInvitationCodeByCode(invitationCodeExample);
		if(!optionalInvitationCode.isPresent()){ // 如果邀请码无效或者不存在,则直接返回
			errorMsgList = new ArrayList<>();
			errorMsgList.add("邀请码无效，请向管理员申请 !");
			model.addAttribute("errorMsgList",errorMsgList);
			return "register";
		}
		
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
		user.setAuthorities(authorities);
		user.setEncodePassword(user.getPassword()); // 加密密码
		
		try {
		    userService.saveUser(user);
		    
		    // 创建用户成功后,将邀请码设置成非活性的
		    invitationCodeObj = optionalInvitationCode.get();//取出这条邀请码
		    invitationCodeObj.setStatus("0");// 将其状态失活
		    invitationCodeService.saveOrUpdateInvitationCode(invitationCodeObj);
		    
		} catch (ConstraintViolationException e)  {
			// 捕获bean验证的异常信息,并设置到页面
			errorMsgList = new ArrayList<>();
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
	
}
