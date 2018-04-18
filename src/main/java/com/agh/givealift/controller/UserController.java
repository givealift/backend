package com.agh.givealift.controller;

import com.agh.givealift.exceptions.FacebookAccessException;
import com.agh.givealift.model.AuthToken;
import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.LoginUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.model.response.AuthenticationResponse;
import com.agh.givealift.model.response.GalUserResponse;
import com.agh.givealift.security.JwtTokenUtil;
import com.agh.givealift.service.FacebookService;
import com.agh.givealift.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Collections;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final FacebookService facebookService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService, FacebookService facebookService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.facebookService = facebookService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity signIn(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final GalUser user = userService.getUserByUsername(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(user.getGalUserId(), token));
    }


    @RequestMapping(value = "/user/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody SignUpUserRequest signUpUserRequest) {
        return new ResponseEntity<>(userService.signUp(signUpUserRequest), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(userService.list(), HttpStatus.OK);
    }

    @PutMapping(value = "/user/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable("id") long id, @RequestBody SignUpUserRequest signUpUserRequest) {
        return new ResponseEntity<>(userService.editUser(signUpUserRequest, id), HttpStatus.CREATED);
    }

    @PutMapping(value = "/user/edit/password/{id}")
    public ResponseEntity<?> editPassword(@PathVariable("id") long id, @RequestBody String password) {
        return new ResponseEntity<>(userService.editUserPassword(password, id), HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/public{id}")
    public ResponseEntity<?> getPublicInfo(@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.getUserPublicInfo(id), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<GalUserResponse> list(@PathVariable("id") long id) {
        return new ResponseEntity<>(new GalUserResponse(userService.getUserById(id)), HttpStatus.OK);
    }

    @RequestMapping(value = "/facebook/url", method = RequestMethod.GET)
    public ResponseEntity<?> facebookUrl() {
        return new ResponseEntity<>(Collections.singletonMap("response", facebookService.createFacebookAuthorizationURL()), HttpStatus.OK);
    }

    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public ResponseEntity<?> facebook(@RequestParam("code") String code) {
        GalUser user = null;
        try {
            user = facebookService.createFacebookAccessToken(code);
        } catch (FacebookAccessException e) {
            return new ResponseEntity<>("Cannot sign in with Facebook", HttpStatus.FORBIDDEN);
        }
        final Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        user.getLogin(),
                        user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(user);
        return new ResponseEntity<>(new AuthToken(token), HttpStatus.OK);
    }


}
