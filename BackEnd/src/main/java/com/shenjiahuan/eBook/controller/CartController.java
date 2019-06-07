package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.entity.CartItem;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.service.CartService;
import com.shenjiahuan.eBook.service.UserService;
import com.shenjiahuan.eBook.util.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/cart", method = GET)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    public List<CartItem> getCartList(Principal principal) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        return cartService.findCartItemByUserId(user.getUid());
    }

    @RequestMapping(value = "/cart", method = POST)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public void addCart(Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        int uid = user.getUid();
        CartItem item = Format.cartItemFromJsonStr(body, uid);
        cartService.addCart(item);
    }

    @RequestMapping(value = "/cart", method = PUT)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public void updateCart(Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        int uid = user.getUid();
        CartItem item = Format.cartItemFromJsonStr(body, uid);
        cartService.updateCart(item);
    }

    @RequestMapping(value = "/cart", method = DELETE)
    @PreAuthorize("hasRole('ROLE_NORMAL')")
    @ResponseBody
    public void deleteCart(Principal principal, @RequestBody String body) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        int uid = user.getUid();
        CartItem item = Format.cartItemFromJsonStr(body, uid);
        cartService.deleteCart(item);
    }
}
