package com.penghuang.blog.service;

import java.util.List;
import java.util.Optional;

import com.penghuang.blog.domain.Catalog;
import com.penghuang.blog.domain.User;

/**
 * Catalog 服务接口.
 * 
 * @since 1.0.0 2019年2月9日
 * @author penghuang
 */
public interface CatalogService {
	/**
	 * 保存Catalog
	 * @param catalog
	 * @return
	 */
	Catalog saveCatalog(Catalog catalog);
	
	/**
	 * 删除Catalog
	 * @param id
	 * @return
	 */
	void removeCatalog(Long id);

	/**
	 * 根据id获取Catalog
	 * @param id
	 * @return
	 */
	Optional<Catalog> getCatalogById(Long id);
	
	/**
	 * 获取Catalog列表
	 * @return
	 */
	List<Catalog> listCatalogs(User user);
}
