package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.response.HandlerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public HandlerResponse currentUserName(Principal principal) {
        return new HandlerResponse(principal.getName(), true);
    }
}
