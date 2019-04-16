package com.shenjiahuan.eBook.dao;

import java.util.List;

public interface OrderDao {
    List<Object> findPaidOrderByUserId(int userId);

    List<Object> findUnpaidOrderByUserId(int userId);
}
