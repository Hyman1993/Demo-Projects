package com.penghuang.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penghuang.blog.domain.Comment;
import com.penghuang.blog.repository.CommentRepository;

/**
 * Comment Service接口实现类
 * @author Administrator
 *
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public Comment getCommentById(Long id) {
		return commentRepository.getOne(id);
	}

	@Override
	public void removeComment(Long id) {
		commentRepository.deleteById(id);
	}

}
