package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.es.EsBlog;
import com.example.repository.es.EsBlogRepository;

/**
 * Blog 控制器
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/blogs")
public class BlogController {
	@Autowired
	private EsBlogRepository esBlogRepository;
	
	@GetMapping
	public List<EsBlog> list(@RequestParam(value="title") String title,
			@RequestParam(value="content") String content,
			@RequestParam(value="summary") String summary,
			@RequestParam(value="pageIndex",defaultValue="0") Integer pageIndex,
			@RequestParam(value="pageSize",defaultValue="10") Integer pageSize){
		
		// 数据是在test时初始化
		Pageable pageable = new PageRequest(pageIndex,pageSize);
		Page<EsBlog> page = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);
         return page.getContent();
	}
}
