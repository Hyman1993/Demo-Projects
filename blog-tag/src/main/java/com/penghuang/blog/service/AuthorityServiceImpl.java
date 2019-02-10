/**
 * 
 */
package com.penghuang.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penghuang.blog.domain.Authority;
import com.penghuang.blog.repository.AuthorityRepository;

/**
 * Authority 服务.
 * 
 * @since 1.0.0 2017年3月30日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class AuthorityServiceImpl  implements AuthorityService {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Override
	public Authority getAuthorityById(Long id) {
		return authorityRepository.getOne(id);
	}

}
