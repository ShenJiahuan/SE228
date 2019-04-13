package com.shenjiahuan.eBook.dao;

import com.shenjiahuan.eBook.entity.User;

public interface UserDetailsDao {
  User findUserByUsername(String email);
}
