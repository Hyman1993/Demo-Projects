package com.penghuang.blog.service;

import java.util.Optional;

import com.penghuang.blog.domain.Vote;

/**
 * Vote 服务接口.
 * 
 * @since 1.0.0 2019年4月9日
 * @author penghuang
 */
public interface VoteService {
	/**
	 * 根据id获取 Vote
	 * @param id
	 * @return
	 */
	Optional<Vote> getVoteById(Long id);
	/**
	 * 删除Vote
	 * @param id
	 * @return
	 */
	void removeVote(Long id);
}
