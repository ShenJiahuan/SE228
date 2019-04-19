package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.dao.UserDetailsDao;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.response.HandlerResponse;
import com.shenjiahuan.eBook.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return new HandlerResponse(userDetailsDao.findUserByUsername(username), true);
    }

    @RequestMapping(value="/user/login", method = RequestMethod.GET)
    public String login() {
        return "";
    }

    @Autowired
    UserDetailsDao userDetailsDao;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("/user/register")
    public HandlerResponse registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return new HandlerResponse(bindingResult.getAllErrors().get(0).getDefaultMessage(), false);
        }

        userDetailsDao.save(userForm);

        //roleDao.addRoleToUser(userDetailsDao.findUserByEmail(userForm.getEmail()).getUid(), "USER");

        //securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return new HandlerResponse("注册成功", true);
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HandlerResponse listUsers() {
        return new HandlerResponse(userDetailsDao.findAllUsers(), true);
    }

    @RequestMapping(value = "/user/ban", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HandlerResponse banUser(@RequestParam(value="uid") int uid, @RequestParam(value="ban") boolean ban) {
        userDetailsDao.banUser(uid, ban);
        return new HandlerResponse(null, true);
    }

    @RequestMapping(value = "/user/admin", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public HandlerResponse adminUser(@RequestParam(value="uid") int uid, @RequestParam(value="admin") boolean admin) {
        userDetailsDao.adminUser(uid, admin);
        return new HandlerResponse(null, true);
    }
}
