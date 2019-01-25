package com.example.domain.es;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 文档
 * @author Administrator
 *
 */
@Document(indexName = "blog",type="blog") // 文档注解
public class EsBlog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id // 组件
	private String id;
	private String title;
	private String summary;
	private String content;
	protected EsBlog(){// JPA规范要求:防止直接使用
		
	}
	public EsBlog(String title,String summary,String content){// JPA规范要求:防止直接使用
		this.title = title;
		this.summary = summary;
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "EsBlog [id=" + id + ", title=" + title + ", summary=" + summary + ", content=" + content + "]";
	}
	
}
