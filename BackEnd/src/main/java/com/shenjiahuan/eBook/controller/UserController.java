package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.dao.UserDetailsDao;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.exception.IncorrectParameterException;
import com.shenjiahuan.eBook.exception.UnauthorizedException;
import com.shenjiahuan.eBook.response.HandlerResponse;
import com.shenjiahuan.eBook.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserDetailsDao userDetailsDao;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public User currentUserName(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException("Not logged in");
        }
        String username = principal.getName();
        return userDetailsDao.findUserByUsername(username);
    }

    @RequestMapping(value="/user/login", method = RequestMethod.GET)
    public String login() {
        return "";
    }

    @PostMapping("/user/register")
    public void registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new IncorrectParameterException("register form incorrect");
        }

        userDetailsDao.save(userForm);

        //roleDao.addRoleToUser(userDetailsDao.findUserByEmail(userForm.getEmail()).getUid(), "USER");

        //securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> listUsers() {
        return userDetailsDao.findAllUsers();
    }

    @RequestMapping(value = "/user/ban", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void banUser(@RequestParam(value="uid") int uid, @RequestParam(value="ban") boolean ban) {
        userDetailsDao.banUser(uid, ban);
    }

    @RequestMapping(value = "/user/admin", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public void adminUser(@RequestParam(value="uid") int uid, @RequestParam(value="admin") boolean admin) {
        userDetailsDao.adminUser(uid, admin);
    }
}
