package com.penghuang.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penghuang.blog.domain.Catalog;
import com.penghuang.blog.domain.User;

/**
 * Catalog Repository
 * @author penghuang
 *
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    /**
     * 根据用户查询
     * @param user
     * @return
     */
    List<Catalog> findByUser(User user);

    /**
     * 根据用户、分类名称查询
     * @param user
     * @param name
     * @return
     */
    List<Catalog> findByUserAndName(User user,String name);
}
