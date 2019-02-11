package com.penghuang.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.penghuang.blog.domain.Blog;
import com.penghuang.blog.domain.Catalog;
import com.penghuang.blog.domain.Comment;
import com.penghuang.blog.domain.User;
import com.penghuang.blog.domain.Vote;
import com.penghuang.blog.domain.es.EsBlog;
import com.penghuang.blog.repository.BlogRepository;

/**
 * Blog 服务.
 * 
 * @since 1.0.0 2017年4月7日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class BlogServiceImpl implements BlogService {

	// 用来存储游客的ip地址
	List<String> ipList = new ArrayList<>();
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserService userService;

	@Autowired
	private EsBlogService esBlogService;
	
	/**
	 * 除了将blog存储到mysql数据库中，还需要将blog存储到elasticsearch中
	 */
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
	   boolean isNew = (blog.getId() == null);
	   EsBlog esBlog = null;
	
	   Blog returnBlog = blogRepository.save(blog);
	
	   if (isNew) {
		  esBlog = new EsBlog(returnBlog);
	   } else {
		  esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
		  esBlog.update(returnBlog);
	   }
	
	   esBlogService.updateEsBlog(esBlog);
	   return returnBlog;}

	@Transactional
	@Override
	public void removeBlog(Long id) {
		blogRepository.deleteById(id);
		EsBlog esblog = esBlogService.getEsBlogByBlogId(id);
		esBlogService.removeEsBlog(esblog.getId());
	}

	@Transactional
	@Override
	public Blog updateBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	@Override
	public Blog getBlogById(Long id) {
		return blogRepository.getOne(id);
	}

	@Override
	public Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable) {
		// 模糊查询 最新
		title = "%" + title + "%";
		String tags = title;
		Page<Blog> blogs = blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title,user,tags,user,pageable);
		return blogs;
	}

	@Override
	public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
		// 模糊查询 最热
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
		return blogs;
	}

	/**
	 * 阅读量递增
	 */
	@Override
	public void readingIncrease(Long id) {
		Blog blog = blogRepository.getOne(id);
		blog.setReadSize(blog.getReadSize()+1);//阅读量+1
		blogRepository.save(blog);
	}
 
	/**
	 * 添加评论
	 * 游客也能添加评论?// 均以游客身份添加
	 */
	@Override
	public Blog createComment(Long blogId, String commentContent,boolean isLogin) {
		Optional<Blog> optionalBlog = blogRepository.findById(blogId);
		Blog originalBlog = null;
		if(optionalBlog.isPresent()) {
			originalBlog = optionalBlog.get();
			User user = null;
			if(isLogin){
			// 获取当前页面的用户
			   user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			}else{
				// 让游客用默认游客账户来发表言论
				user = userService.getUserById((long) 3);
			}

			Comment comment = new Comment(user, commentContent);
			originalBlog.addComment(comment);
		}

		return this.saveBlog(originalBlog);
	}

	/**
	 * 删除评论
	 * 只有该用户或者管理员能删除评论
	 */
	@Override
	public void removeComment(Long blogId, Long commentId) {
		Optional<Blog> optionalBlog = blogRepository.findById(blogId);
		if(optionalBlog.isPresent()) {
			Blog originalBlog = optionalBlog.get();
			originalBlog.removeComment(commentId);
			this.saveBlog(originalBlog);
		}
	}
	
	/**
	 * 点赞(游客也可以点赞,需要自动为其注入游客对象)
	 * 只有该用户或者管理员能删除评论
	 */
	@Override
	public Blog createVote(Long blogId,boolean isLogin,String ip) {
		Optional<Blog> optionalBlog = blogRepository.findById(blogId);
		Blog originalBlog = null;
 
		if (optionalBlog.isPresent()) {
			originalBlog = optionalBlog.get();
			User user = null;
			// isLogin判断是否为登录状态,将游客user对象传进去
			if(isLogin){
			    user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			}else{ // 若是未登录，游客身份的
			   
		        // 让游客用默认游客账户来点赞
				 if(ipList.contains(ip)){
					 // 同一个ip地址的游客只能点赞一次
					 throw new IllegalArgumentException("谢谢,您已经点过赞了!");
				 }else{
					// 让游客用默认游客账户来点赞
					user = userService.getUserById((long) 3);	
					ipList.add(ip);
				 }
			}
			Vote vote = new Vote(user);
			boolean isExist = originalBlog.addVote(vote);
			if (isExist) {
				// 还需要判断，若是同一个登录用户，或者同一个游客(根据点赞的时间)，不能连续点赞
				throw new IllegalArgumentException("谢谢,您已经点过赞了!");
			}
		}

		return this.saveBlog(originalBlog);
	}

	/**
	 * 删除(只有登录对象才能删除)
	 * 只有该用户或者管理员能删除评论
	 */
	@Override
	public void removeVote(Long blogId, Long voteId) {
		Optional<Blog> optionalBlog = blogRepository.findById(blogId);
		Blog originalBlog = null;
 
		if (optionalBlog.isPresent()) {
			originalBlog = optionalBlog.get();
			originalBlog.removeVote(voteId);
			this.saveBlog(originalBlog);
		}
	}
	
	/**
	 * 根据标签来查询博客列表
	 */
	@Override
	public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
		Page<Blog> blogs = blogRepository.findByCatalog(catalog, pageable);
		return blogs;
	}

}
