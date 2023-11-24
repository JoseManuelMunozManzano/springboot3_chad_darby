package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.entity.Role;
import com.luv2code.springboot.cruddemo.entity.User;
import com.luv2code.springboot.cruddemo.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	void save(WebUser webUser);

    Role findRoleByName(String roleName);
}
