package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * UserRepository
 * @author Administrator
 *
 */
@Repository
public class UserRepositoryImpl implements UserRepository{

	private static AtomicLong counter = new AtomicLong();
	private final ConcurrentMap<Long,User> userMap = new ConcurrentHashMap<>();
	
	@Override
	public User saveORUpdateUser(User user) {
        Long id = user.getId();
        if(id == null){ //新建
        	id = counter.incrementAndGet();
        	user.setId(id);
        }
        this.userMap.put(id, user);
		return user;
	}

	@Override
	public void deleteUser(Long id) {
        this.userMap.remove(id);		
	}

	@Override
	public User getUserById(Long id) {
		return this.userMap.get(id);
	}

	@Override
	public List<User> listUsers() {
		// TODO Auto-generated method stub
		return new ArrayList<User>(this.userMap.values());
	}
   
}
