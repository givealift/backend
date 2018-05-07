package com.agh.givealift.controller;

import com.agh.givealift.exceptions.FacebookAccessException;
import com.agh.givealift.model.AuthToken;
import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.request.LoginUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.model.response.AuthenticationResponse;
import com.agh.givealift.model.response.GalUserResponse;
import com.agh.givealift.security.JwtTokenUtil;
import com.agh.givealift.service.FacebookService;
import com.agh.givealift.service.RouteService;
import com.agh.givealift.service.UserService;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {
    private final static COD cod = CODFactory.get();
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final FacebookService facebookService;
    private final RouteService routeService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService, FacebookService facebookService,
                          RouteService routeService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.facebookService = facebookService;
        this.routeService = routeService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity signIn(@RequestBody LoginUser loginUser) throws AuthenticationException {
        cod.i(loginUser);
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
        cod.i(signUpUserRequest);
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

    @PostMapping(value = "/user/photo/{id}")
    public ResponseEntity<?> photoUser(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {

        return new ResponseEntity<>(userService.saveUserPhoto(id, file), HttpStatus.OK);
    }

    @PutMapping(value = "/user/edit/password/{id}")
    public ResponseEntity<?> editPassword(@PathVariable("id") long id, @RequestBody String password) {
        return new ResponseEntity<>(userService.editUserPassword(password, id), HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/photo/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id) throws IOException {
        return new ResponseEntity<>(userService.getUserPhoto(id), HttpStatus.OK);
    }

    @GetMapping(value = "/user/route/{id}")
    public ResponseEntity<List<Route>> getRoutes(@PathVariable("id") long id, @RequestParam("page") int page) throws IOException {
        return new ResponseEntity<>(routeService.userRoute(id, new PageRequest(page, 10)), HttpStatus.OK);
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
