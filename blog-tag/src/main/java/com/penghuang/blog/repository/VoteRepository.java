package com.penghuang.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penghuang.blog.domain.Vote;

/**
 *  点赞  库，实体类型及主键类型
 * @author Administrator
 *
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
