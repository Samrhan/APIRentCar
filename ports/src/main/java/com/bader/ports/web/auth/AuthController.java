package com.bader.ports.web.auth;

import com.bader.domain.user.UserService;
import com.bader.ports.web.auth.dto.LoginRequest;
import com.bader.ports.web.security.JwtTokenUtil;
import com.bader.ports.web.security.SecurityConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping()
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest) {
        System.out.println("loginRequest = " + loginRequest);
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));
        String token = jwtTokenUtil.generateToken(authenticate);
        System.out.println(token);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
    }
    @PostMapping("/register/customer")
    public ResponseEntity<Object> registerCustomer(@RequestBody @Valid LoginRequest loginRequest) {
        userService.registerUser(loginRequest.getUsername(), passwordEncoder.encode(loginRequest.getPassword()), SecurityConfiguration.CUSTOMER);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/register/seller")
    public ResponseEntity<Object> registerSeller(@RequestBody @Valid LoginRequest loginRequest) {
        userService.registerUser(loginRequest.getUsername(), passwordEncoder.encode(loginRequest.getPassword()), SecurityConfiguration.SELLER);
        return ResponseEntity.ok().build();
    }
}
