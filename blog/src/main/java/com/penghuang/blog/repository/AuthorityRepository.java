package com.penghuang.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.penghuang.blog.domain.Authority;

/**
 * Authority 仓库.
 *
 * @since 1.0.0 2017年3月2日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
}
