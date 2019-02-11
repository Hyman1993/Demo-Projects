package com.penghuang.blog.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.penghuang.blog.domain.InvitationCode;
import com.penghuang.blog.repository.InvitationCodeRepository;

/**
 * 邀请码  服务实现类.
 * 
 * @since 2019.02.01
 * @author penghuang
 */
@Service
public class InvitationCodeServiceImpl implements InvitationCodeService {

	@Autowired
	private InvitationCodeRepository invitationCodeRepository;

	@Override
	public Optional<InvitationCode> getInvitationCodeById(Long id) {
		return invitationCodeRepository.findById(id);
	}

	@Override
	public boolean isExistsByCode(Example<InvitationCode> invitationCode) {
		return invitationCodeRepository.exists(invitationCode);
	}

	@Transactional
	@Override
	public void saveOrUpdateInvitationCode(InvitationCode invitationCode) {
		invitationCodeRepository.save(invitationCode);
	}

	@Transactional
	@Override
	public void removeInvitationCode(Long id) {
		invitationCodeRepository.deleteById(id);
	}

	@Override
	public Optional<InvitationCode> getInvitationCodeByCode(Example<InvitationCode> invitationCode) {
		return invitationCodeRepository.findOne(invitationCode);
	}
	
	

}
