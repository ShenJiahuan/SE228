package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.dao.RoleDao;
import com.shenjiahuan.eBook.dao.UserDetailsDao;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.response.HandlerResponse;
import com.shenjiahuan.eBook.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class UserController {
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public HandlerResponse currentUserName(Principal principal) {
        if (principal == null) {
            return new HandlerResponse("", false);
        }
        String username = principal.getName();
        return new HandlerResponse(username, true);
    }

    @RequestMapping(value="/user/login", method = RequestMethod.GET)
    public String login() {
        return "";
    }

    @Autowired
    UserDetailsDao userDetailsDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/user/register")
    public HandlerResponse registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return new HandlerResponse(bindingResult.getAllErrors().get(0).getDefaultMessage(), false);
        }

        userDetailsDao.save(userForm);

        roleDao.addRoleToUser(userDetailsDao.findUserByEmail(userForm.getEmail()).getUid(), "USER");

        //securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return new HandlerResponse("注册成功", true);
    }
}
