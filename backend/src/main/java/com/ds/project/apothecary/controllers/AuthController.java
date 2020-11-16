package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.JwtRespDto;
import com.ds.project.apothecary.dtos.LoginDto;
import com.ds.project.apothecary.dtos.SimpleStringDto;
import com.ds.project.apothecary.dtos.UserDto;
import com.ds.project.apothecary.security.JwtUtils;
import com.ds.project.apothecary.security.UserDetailsImpl;
import com.ds.project.apothecary.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Auth controller.
 */
@CrossOrigin(origins = "*",
        allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * The Authentication manager.
     */
    private final AuthenticationManager
            authenticationManager;

    /**
     * The User service.
     */
    private final UserService
            userService;

    /**
     * The Jwt utils.
     */
    private final JwtUtils
            jwtUtils;

    /**
     * Instantiates a new Auth controller.
     *
     * @param pAuthenticationManager the authentication manager
     * @param pUserService           the user service
     * @param pJwtUtils              the jwt utils
     */
    public AuthController(
            final AuthenticationManager pAuthenticationManager,
            final UserService pUserService,
            final JwtUtils pJwtUtils) {
        this.authenticationManager =
                pAuthenticationManager;
        this.userService =
                pUserService;
        this.jwtUtils =
                pJwtUtils;
    }

    /**
     * Authenticate user response entity.
     *
     * @param loginRequest the login request
     * @return the response entity
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @RequestBody final LoginDto loginRequest) {

        Authentication
                authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String
                jwt =
                jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl
                userDetails =
                (UserDetailsImpl) authentication.getPrincipal();
        List<String>
                roles =
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtRespDto(
                        jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getFirstName(),
                        userDetails.getLastName(),
                        roles));
    }

    /**
     * Register user response entity.
     *
     * @param signUpRequest the sign up request
     * @return the response entity
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(
            @RequestBody final UserDto signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new SimpleStringDto("Error: Username is already "
                            + "taken!"));
        }

        userService.create(signUpRequest);

        return ResponseEntity.ok(new SimpleStringDto("User registered"
                + " successfully!"));
    }
}
