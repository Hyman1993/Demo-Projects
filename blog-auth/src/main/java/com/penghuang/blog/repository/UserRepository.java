package com.penghuang.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.penghuang.blog.domain.User;

/**
 *  User Repository 接口
 * @author penghuang
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	/**
	 * 根据用户姓名分页查询用户列表
	 * @param name
	 * @param pageable
	 * @return
	 */
	Page<User> findByNameLike(String name,Pageable pageable);

	/**
	 *  根据用户账号查询用户
	 * @param username
	 * @return
	 */
    User findByUsername(String username);
}
