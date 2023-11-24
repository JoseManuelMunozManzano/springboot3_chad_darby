package com.luv2code.springboot.cruddemo.rest;

import com.luv2code.springboot.cruddemo.entity.Role;
import com.luv2code.springboot.cruddemo.entity.User;
import com.luv2code.springboot.cruddemo.service.UserService;
import com.luv2code.springboot.cruddemo.user.WebUser;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class RegistrationController {

    private Logger logger = Logger.getLogger(getClass().getName());

    private UserService userService;

    // @Autowired
    public RegistrationController(UserService theUserService) {
        userService = theUserService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody WebUser theWebUser) {
        String userName = theWebUser.getUserName();
        logger.info("Processing registration for: " + userName);

        // check the database if user already exists
        User user = userService.findByUserName(userName);
        if (user != null){
            String errorMsg = "User name already exists.";
            logger.warning(errorMsg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }

        // check the database if role is valid
        String roleName = theWebUser.getRoleName();
        Role role = userService.findRoleByName(roleName);
        if (role == null){
            String errorMsg = "Invalid role.";
            logger.warning(errorMsg);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);
        }

        // create user account and store in the database
        userService.save(theWebUser);

        return ResponseEntity.ok().build();
    }
}
