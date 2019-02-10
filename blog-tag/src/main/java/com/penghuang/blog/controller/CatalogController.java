package com.penghuang.blog.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.penghuang.blog.domain.Catalog;
import com.penghuang.blog.domain.User;
import com.penghuang.blog.service.CatalogService;
import com.penghuang.blog.util.ConstraintViolationExceptionHandler;
import com.penghuang.blog.vo.CatalogVO;
import com.penghuang.blog.vo.Response;
 

/**
 * 分类 控制器
 * 
 * @since 1.0.0 2019年2月9日
 * @author penghuang
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 获取分类列表
	 * @param username
	 * @param model
	 * @return
	 */
	@GetMapping
	public String listCatalogs(@RequestParam(value="username",required=true) String username, Model model) {
		User user = (User)userDetailsService.loadUserByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);

		// 判断操作用户是否是分类的所有者
		boolean isOwner = false;
		
		if (SecurityContextHolder.getContext().getAuthentication() !=null 
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				 &&  !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()
				 .equals("anonymousUser")) {
			User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal !=null && user.getUsername().equals(principal.getUsername())) {
				isOwner = true;
			} 
		} 
		
		model.addAttribute("isCatalogsOwner", isOwner);
		model.addAttribute("catalogs", catalogs);
		return "/userspace/u :: #catalogRepleace";
	}
	
	/**
	 * 创建分类
	 * @param catalogVO
	 * @return
	 */
	@PostMapping
	@PreAuthorize("authentication.name.equals(#catalogVO.username)")// 指定用户才能操作方法
	public ResponseEntity<Response> createCatalog(@RequestBody CatalogVO catalogVO) {
		
		String username = catalogVO.getUsername();
		Catalog catalog = catalogVO.getCatalog();
		
		User user = (User)userDetailsService.loadUserByUsername(username);
		
		try {
			catalog.setUser(user);
			catalogService.saveCatalog(catalog);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, 
					ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 删除分类.登陆后采用操作权限
	 * @param username
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("authentication.name.equals(#username)")  // 指定用户才能操作方法
	public ResponseEntity<Response> deleteCatalog(String username, @PathVariable("id") Long id) {
		try {
			catalogService.removeCatalog(id);
		} catch (ConstraintViolationException e)  {
			return ResponseEntity.ok().body(new Response(false, 
					ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		
		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
	
	/**
	 * 获取分类编辑界面
	 * @param model
	 * @return
	 */
	@GetMapping("/edit")
	public String getCatalogEdit(Model model) {
		Catalog catalog = new Catalog(null, null);
		model.addAttribute("catalog",catalog);
		return "/userspace/catalogedit";
	}
	
	/**
	 * 根据 Id 获取编辑界面
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String getCatalogById(@PathVariable("id") Long id, Model model) {
		Optional<Catalog> optionalCatalog = catalogService.getCatalogById(id);
		Catalog catalog = null;
		
		if (optionalCatalog.isPresent()) {
			catalog = optionalCatalog.get();
		}
		
		model.addAttribute("catalog",catalog);
		return "/userspace/catalogedit";
	}
	
}
