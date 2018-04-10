package com.agh.givealift.controller;

import com.agh.givealift.exceptions.FacebookAccessException;
import com.agh.givealift.model.AuthToken;
import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.LoginUser;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;

@Controller
@RequestMapping("/api")
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

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
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
        return ResponseEntity.ok(new AuthenticationResponse(user, new AuthToken(token)));
    }


    @RequestMapping(value = "/user/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody LoginUser loginUser) {
        return new ResponseEntity<>(new GalUserResponse(userService.signUp(loginUser)), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(facebookService.createFacebookAuthorizationURL(), HttpStatus.OK);
    }

    @RequestMapping(value = "/facebook/url", method = RequestMethod.GET)
    public ResponseEntity<?> facebookUrl() {
        return new ResponseEntity<>(facebookService.createFacebookAuthorizationURL(), HttpStatus.OK);
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
