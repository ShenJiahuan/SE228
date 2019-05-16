package com.shenjiahuan.eBook.repository;

import com.shenjiahuan.eBook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(String email);

    User findUserByUsername(String username);

}
