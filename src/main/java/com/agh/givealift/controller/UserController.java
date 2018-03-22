package com.agh.givealift.controller;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.entity.Route;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<GalUser> list() {
        return new ResponseEntity<>(new GalUser(), HttpStatus.OK);
    }
}
