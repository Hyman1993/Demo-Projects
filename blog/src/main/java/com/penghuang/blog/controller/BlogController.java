package com.penghuang.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.penghuang.blog.domain.User;
import com.penghuang.blog.domain.es.EsBlog;
import com.penghuang.blog.service.EsBlogService;
import com.penghuang.blog.vo.TagVO;

/**
 * Blog 控制器.(首页)
 * 
 * @since 1.0.0 2019年2月11日
 * @author penghuang
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {
	
	@Autowired
    private EsBlogService esBlogService;
	 
    @GetMapping
    public String listEsBlogs(
            @RequestParam(value="order",required=false,defaultValue="new") String order,
            @RequestParam(value="keyword",required=false,defaultValue="" ) String keyword,
            @RequestParam(value="async",required=false) boolean async,
            @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
            @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
            Model model) {

        Page<EsBlog> page = null;
        List<EsBlog> list = null;
        boolean isEmpty = true; // 系统初始化时，没有博客数据
        try {
            if (order.equals("hot")) { // 最热查询 根据阅读量、评论量、点赞量、时间来逆序
                Sort sort = new Sort(Direction.DESC,"readSize","commentSize","voteSize","createTime"); 
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listHotestEsBlogs(keyword, pageable);
            } else if (order.equals("new")) { // 最新查询 根据时间逆序
                Sort sort = new Sort(Direction.DESC,"createTime"); 
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listNewestEsBlogs(keyword, pageable);
            }

            isEmpty = false;
        } catch (Exception e) { // 系统初始化，es内可能为空，检索时有可能出现异常,设定一些参数对象，直接返回页面
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = esBlogService.listEsBlogs(pageable);
            model.addAttribute("order", order);
            model.addAttribute("keyword", keyword); //keyword即标签
            model.addAttribute("page", page);
            return (async==true?"/index :: #mainContainerRepleace":"/index");
        }  

        list = page.getContent();   // 当前所在页面数据列表

        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword); //keyword即标签
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        // 首次访问页面才加载以下数据(不是ajax请求)
        if (!async && !isEmpty) {
            List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
            model.addAttribute("newest", newest);
            List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
            model.addAttribute("hotest", hotest);
            List<TagVO> tags = esBlogService.listTop30Tags();
            model.addAttribute("tags", tags);
            List<User> users = esBlogService.listTop12Users();
            
            // 管理员账户和游客账户不在前台页面显示
            if(users != null && users.size() > 0) {
            	users.removeIf(t->{
            		return "admin".equals(t.getUsername()) || "visitor".equals(t.getUsername());
            	});
            }
            
            model.addAttribute("users", users);
        }

        return (async==true?"/index :: #mainContainerRepleace":"/index");
    }
}