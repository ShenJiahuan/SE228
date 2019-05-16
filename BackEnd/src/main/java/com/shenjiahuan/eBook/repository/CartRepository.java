package com.shenjiahuan.eBook.repository;

import com.shenjiahuan.eBook.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findCartItemsByUidOrderByAddTimeDesc(int userId);

    CartItem findCartItemByUidAndBookId(int userId, int bookId);

    void deleteCartItemByUidAndBookId(int userId, int bookId);
}
