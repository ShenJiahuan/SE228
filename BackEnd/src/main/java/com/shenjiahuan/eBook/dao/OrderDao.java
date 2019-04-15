package com.shenjiahuan.eBook.dao;

import java.util.List;

public interface OrderDao {
    List<Object> findPurchasedOrderByUserId(int userId);

    List<Object> findUnpurchasedOrderByUserId(int userId);
}
