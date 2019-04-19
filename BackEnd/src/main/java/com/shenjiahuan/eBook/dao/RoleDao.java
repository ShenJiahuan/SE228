package com.shenjiahuan.eBook.dao;

import java.util.List;

public interface RoleDao {
    List<String> findRoleById(int userId);
    void addRoleToUser(int userId, String role);
}
