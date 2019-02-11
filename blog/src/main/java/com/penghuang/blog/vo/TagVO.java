package com.penghuang.blog.vo;

/**
 * Tag 值对象.
 * 
 * @since 1.0.0 2019年2月11日
 * @author penghuang
 */
public class TagVO {
	
	private String name;
	private Long count;
	
	public TagVO(String name, Long count) {
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
 
}
