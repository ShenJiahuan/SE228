package com.shenjiahuan.eBook.validator;

import com.shenjiahuan.eBook.dao.UserDetailsDao;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.util.UserCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    @Lazy
    private UserDetailsDao userDetailsDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty", "Email address should not be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty", "Username should not be empty");
        if (userDetailsDao.findUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate", "Email address exists");
        }
        if (!UserCheck.checkEmail(user.getEmail())) {
            errors.rejectValue("email", "Illegal", "Illegal email address");
        }
        ValidationUtils.rejectIfEmpty(errors, "password", "NotEmpty", "Password should not be empty");
        if (!UserCheck.checkPass(user.getPassword())) {
            errors.rejectValue("password", "Illegal", "Illegal password");
        }
    }
}
