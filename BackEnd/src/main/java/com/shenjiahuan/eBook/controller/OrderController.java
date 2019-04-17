package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.dao.OrderDao;
import com.shenjiahuan.eBook.dao.UserDetailsDao;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.response.HandlerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class OrderController {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDetailsDao userDetailsDao;

    @RequestMapping(value = "/orders", method = GET)
    public HandlerResponse getBookList(Principal principal) {
        String username = principal.getName();
        User user = userDetailsDao.findUserByUsername(username);
        System.out.println(user.getUid());
        return new HandlerResponse(orderDao.findOrderByUserId(user.getUid(), true), true);
    }
}
