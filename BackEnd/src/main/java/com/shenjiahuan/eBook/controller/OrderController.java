package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.entity.Order;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.service.OrderService;
import com.shenjiahuan.eBook.service.UserService;
import com.shenjiahuan.eBook.util.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class OrderController {
    @Autowired
    @Lazy
    private OrderService orderService;

    @Autowired
    @Lazy
    private UserService userService;

    @RequestMapping(value = "/orders", method = GET)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    public List<Order> getOrderList(Principal principal) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        return orderService.findOrderByUserId(user.getUid());
    }

    @RequestMapping(value = "/orders", method = POST)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public void createOrder( Principal principal, @RequestBody String body) throws IOException {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        int uid = user.getUid();
        Order order = Format.orderFromJsonStr(body, uid);
        orderService.createOrder(order);
    }

    @RequestMapping(value = "/orders", method = PUT)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public void cartToOrder(Principal principal, @RequestBody String body) throws IOException {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        int uid = user.getUid();
        Order order = Format.orderFromJsonStr(body, uid);
        orderService.cartToOrder(order);
    }

    @RequestMapping(value = "/order_stat", method = GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public List<Object> getOrderStatus(@RequestParam(value = "from") BigDecimal from, @RequestParam(value = "to") BigDecimal to) {
        return orderService.getStatus(from, to);
    }

    @RequestMapping(value = "/purchase_stat", method = GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public List<Object> getUserPurchase(@RequestParam(value = "from") BigDecimal from, @RequestParam(value = "to") BigDecimal to) {
        return orderService.getUserPurchase(from, to);
    }
}
