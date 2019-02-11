package com.penghuang.blog.vo;

import com.penghuang.blog.domain.Catalog;

/**
 * Catalog VO.
 * 
 * @since 1.0.0 2019年2月9日
 * @author penghuang
 */
public class CatalogVO {
	
	private String username;
	private Catalog catalog;
	
	public CatalogVO() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

}
