package com.penghuang.blog.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.penghuang.blog.domain.Blog;
import com.penghuang.blog.domain.Comment;
import com.penghuang.blog.domain.User;
import com.penghuang.blog.service.BlogService;
import com.penghuang.blog.service.CommentService;
import com.penghuang.blog.util.ConstraintViolationExceptionHandler;
import com.penghuang.blog.vo.Response;

/**
 * 评论 控制器.
 * 
 * @since 1.0.0 2019年2月8日
 * @author penghuang
 */
@Controller
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private CommentService commentService;

	/**
	 * 获取评论列表
	 * 
	 * @param blogId
	 * @param model
	 * @return
	 */
	@GetMapping
	public String listComments(@RequestParam(value = "blogId", required = true) Long blogId, Model model) {
		Blog blog = blogService.getBlogById(blogId);
		List<Comment> comments  = blog.getComments();

		// 判断操作用户是否是评论的所有者
		String commentOwner = "";
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() 
				&& !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal != null) {
				commentOwner = principal.getUsername();
			}
		}

		model.addAttribute("commentOwner", commentOwner);
		model.addAttribute("comments", comments);
		return "/userspace/blog :: #mainContainerRepleace";
	}

	/**
	 * 发表评论
	 * 希望游客也能发表评论
	 * @param blogId
	 * @param commentContent
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')") // 指定角色权限才能操作方法,注解掉，这样游客(未登录)也能发表评论
	@PostMapping
	public ResponseEntity<Response> createComment(Long blogId, String commentContent,boolean isLogin) {

		try {
			blogService.createComment(blogId, commentContent,isLogin);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}

	/**
	 * 删除评论
	 * 必须得是博客用户才有权限删除
	 * @return
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')") // 指定角色权限才能操作方法
	public ResponseEntity<Response> delete(@PathVariable("id") Long id, Long blogId) {

		boolean isOwner = false;
		Comment comment = commentService.getCommentById(id);
		User user = null;

		if (comment != null) {
			user = comment.getUser();
		} else {
			return ResponseEntity.ok().body(new Response(false, "不存在该评论！"));
		}

		// 判断操作用户是否是评论的所有者,将comment类的user信息跟当前页面的user信息进行比较来判断
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() 
				&& !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// 在此处加上，若是管理员，则可随意删除(游客评论)
			if ((principal != null && user != null && user.getUsername().equals(principal.getUsername())) || "admin".equals(principal.getUsername())) {
				isOwner = true;
			}
		}

		if (!isOwner) {
			return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
		}

		try {
			// 删除博客里的评论
			blogService.removeComment(blogId, id);
			commentService.removeComment(id);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
}
