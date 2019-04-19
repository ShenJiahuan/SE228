package com.shenjiahuan.eBook.service;

import com.shenjiahuan.eBook.dao.RoleDao;
import com.shenjiahuan.eBook.dao.UserDetailsDao;
import com.shenjiahuan.eBook.entity.User;
import com.shenjiahuan.eBook.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsDao userDetailsDao;

    @Autowired
    RoleDao roleDao;

    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userDetailsDao.findUserByEmail(email);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
            builder.disabled(false);
            logger.debug(user.getPassword());
            builder.password(user.getPassword());
            String[] authorities = Converter.asStrings(roleDao.findRoleById(user.getUid()).toArray());
            builder.authorities(authorities);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}
