package com.penghuang.blog.repository;

import java.util.List;

import com.penghuang.blog.domain.User;

public interface UserRepository {

  /**
   * 创建或者修改用户
   * @param user
   * @return
   */
  User saveORUpdateUser(User user);
  
  /**
   * 删除用户
   * @param id
   */
  void deleteUser(Long id);
  
  /**
   * 根据ID来查询用户
   * @param id
   * @return
   */
  User getUserById(Long id);
  
  /**
   * 查询用户列表
   * @return
   */
  List<User> listUsers();
}
