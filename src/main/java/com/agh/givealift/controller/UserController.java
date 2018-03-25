package com.agh.givealift.controller;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.Registration;
import com.agh.givealift.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(userService.list(), HttpStatus.OK);
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<?> registration(@RequestBody Registration registration) {
        userService.register(registration);
        return new ResponseEntity<>(new GalUser(), HttpStatus.OK);
    }
}
