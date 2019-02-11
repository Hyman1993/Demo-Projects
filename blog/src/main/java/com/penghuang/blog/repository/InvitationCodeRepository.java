package com.penghuang.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.penghuang.blog.domain.InvitationCode;

/**
 *  邀请码 库，实体类型及主键类型
 * @author penghuang
 *
 */
public interface InvitationCodeRepository extends JpaRepository<InvitationCode, Long> {

}
