package com.shenjiahuan.eBook.dao;

import java.util.List;

public interface OrderDao {
    List<Object> findOrderByUserId(int userId, boolean paid);
}
