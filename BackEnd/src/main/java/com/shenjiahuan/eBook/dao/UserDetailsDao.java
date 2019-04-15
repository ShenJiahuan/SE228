package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.User;

public interface UserDetailsDao {
  void save(User user);

  User findUserByEmail(String email);

  User findUserByUsername(String username);
}
