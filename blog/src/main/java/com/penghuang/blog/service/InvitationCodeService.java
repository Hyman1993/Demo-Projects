package com.penghuang.blog.service;

import java.util.Optional;

import org.springframework.data.domain.Example;

import com.penghuang.blog.domain.InvitationCode;

/**
 * 邀请码服务接口
 * @author penghuang
 *
 */
public interface InvitationCodeService {
	/**
	 * 根据id获取邀请码
	 * @param id
	 * @return
	 */
	Optional<InvitationCode> getInvitationCodeById(Long id);
	
	/**
	 * 根据邀请码搜索,检验邀请码是否存在
	 * @param id
	 * @return
	 */
	boolean isExistsByCode(Example<InvitationCode> invitationCode);
	
	/**
	 * 根据邀请码搜索,检索出这一条邀请码
	 * @param id
	 * @return
	 */
	Optional<InvitationCode> getInvitationCodeByCode(Example<InvitationCode> invitationCode);
	
	/**
	 * 保存或更新邀请码
	 * @param id
	 * @return
	 */
	void saveOrUpdateInvitationCode(InvitationCode invitationCode);
	
	/**
	 * 删除邀请码
	 * @param user
	 * @return
	 */
	void removeInvitationCode(Long id);
}
