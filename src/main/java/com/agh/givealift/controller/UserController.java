package com.agh.givealift.controller;

import com.agh.givealift.exceptions.FacebookAccessException;
import com.agh.givealift.model.AuthToken;
import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.enums.EmailTemplate;
import com.agh.givealift.model.enums.ResetTokenEnum;
import com.agh.givealift.model.request.LoginUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.model.response.AuthenticationResponse;
import com.agh.givealift.model.response.GalUserResponse;
import com.agh.givealift.security.JwtTokenUtil;
import com.agh.givealift.security.UserDetails;
import com.agh.givealift.service.FacebookService;
import com.agh.givealift.service.PasswordResetService;
import com.agh.givealift.service.RouteService;
import com.agh.givealift.service.UserService;
import com.agh.givealift.service.implementation.EmailService;
import com.agh.givealift.util.ResetTokenExpirationException;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private final EmailService emailService;
    private final PasswordResetService passwordResetService;



    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService, FacebookService facebookService, RouteService routeService, EmailService emailService, PasswordResetService passwordResetService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.facebookService = facebookService;
        this.routeService = routeService;
        this.emailService = emailService;
        this.passwordResetService = passwordResetService;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> authExc() {
        return new ResponseEntity<>("Błędny login lub hasło", HttpStatus.UNAUTHORIZED);

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
        final GalUser user = userService.getUserByUsername(loginUser.getUsername()).get();
        final String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(user.getGalUserId(), token));
    }

    @RequestMapping(value = "/user/signup", method = RequestMethod.POST)
    public ResponseEntity<Long> signUp(@RequestBody SignUpUserRequest signUpUserRequest) {
        cod.i(signUpUserRequest);
        //  emailService.sendMessage(signUpUserRequest.getEmail(), EmailTemplate.USER_SIGN_UP.getSubject(), EmailTemplate.USER_SIGN_UP.getText());
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

    @PostMapping(value = "/user/send-reset-email/{email}")
    public ResponseEntity<?> sendResetEmail(@PathVariable("email") String email, HttpServletRequest request) {
        Optional<GalUser> userOptional = userService.getUserByUsername(email);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>("Nie znaleziono użytkownika: " + email, HttpStatus.UNAUTHORIZED);
        }
        GalUser user = userOptional.get();
        String token = UUID.randomUUID().toString();
        passwordResetService.createEmailResetPassToken(user, token);
        String url = request.getHeader("origin") + "/change-password?id=" +
                user.getGalUserId() + "&token=" + token;
        emailService.sendMessage
                (user.getEmail(), EmailTemplate.PASSWORD_RESET.getSubject(), EmailTemplate.PASSWORD_RESET.getText() + "  " + url);

        return new ResponseEntity<>(user.getEmail(), HttpStatus.OK);
    }

    @SuppressWarnings("deprecation")
    @GetMapping(value = "/user/change/password")
    public ResponseEntity<String> changePassword(@RequestParam("old-password") String oldPass, @RequestParam("new-password") String newPass, Authentication authentication) throws AuthenticationException {
        GalUser user = ((UserDetails) authentication.getPrincipal()).getUser();
        userService.changeUserPassword(user, oldPass, newPass);
        return new ResponseEntity<>("Hasło zmienione", HttpStatus.OK);
    }


    @PutMapping(value = "/user/reset/password")
    @SuppressWarnings("deprecation")
    public ResponseEntity<?> editPassword(@RequestParam("id") long id, @RequestParam("token") String token, @RequestBody String password) {
        try {
            passwordResetService.validateResetPasswordToken(id, token, ResetTokenEnum.EMAIL_CONFIRMED);
        } catch (IllegalStateException _) {
            return new ResponseEntity<>("Wrong user or state", HttpStatus.BAD_REQUEST);
        } catch (ResetTokenExpirationException _) {
            return new ResponseEntity<>("Token Expired", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(userService.resetUserPassword(password, id), HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/photo/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id) throws IOException {
        return new ResponseEntity<>(userService.getUserPhoto(id), HttpStatus.OK);
    }

    @GetMapping(value = "/user/route/{id}")
    public ResponseEntity<List<Route>> getRoutes(@PathVariable("id") long id, @RequestParam("page") int page) throws IOException {
        return new ResponseEntity<>(routeService.userRoute(id, PageRequest.of(page, 3)), HttpStatus.OK);
    }

    @GetMapping(value = "/user/count/route/{id}")
    public ResponseEntity<Integer> getRoutes(@PathVariable("id") long id) {
        return new ResponseEntity<>(routeService.countUserRoute(id), HttpStatus.OK);
    }

    @PutMapping(value = "/user/rate/{id}")
    public ResponseEntity<Double> rateUsser(@PathVariable("id") long id, Integer rate) throws IOException {
        return new ResponseEntity<>(userService.changeRate(rate, id), HttpStatus.OK);
    }


    @GetMapping(value = "/user/public/{id}")
    public ResponseEntity<?> getPublicInfo(@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.getUserPublicInfo(id), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<GalUserResponse> list(@PathVariable("id") long id) {

        return userService.getUserById(id).map(u -> new ResponseEntity<>(new GalUserResponse(u), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
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
                        user.getEmail(),
                        user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(user);
        return new ResponseEntity<>(new AuthToken(token), HttpStatus.OK);
    }


}
