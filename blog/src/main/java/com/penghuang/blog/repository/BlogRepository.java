package com.penghuang.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.penghuang.blog.domain.Blog;
import com.penghuang.blog.domain.Catalog;
import com.penghuang.blog.domain.User;

/**
 * Blog 仓库.
 *
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
public interface BlogRepository extends JpaRepository<Blog, Long>{
	/**
	 * 根据用户名、博客标题分页查询博客列表(查找最热)
	 * @param user
	 * @param title
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);
	
	/**
	 * 根据用户名、博客分页查询博客列表(时间逆序)(查找最新)
	 * @param title
	 * @param user
	 * @param tags
	 * @param user2
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title,User user,String tags,User user2,Pageable pageable);

	/**
	 * 根据分类查询博客列表
	 * @param catalog
	 * @param pageable
	 * @return
	 */
	Page<Blog> findByCatalog(Catalog catalog, Pageable pageable);
}
