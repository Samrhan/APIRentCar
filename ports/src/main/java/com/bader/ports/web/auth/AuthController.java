package com.bader.ports.web.auth;

import com.bader.domain.user.UserService;
import com.bader.domain.user.model.Customer;
import com.bader.ports.web.auth.dto.CustomerDetailResponse;
import com.bader.ports.web.auth.dto.CustomerIdResponse;
import com.bader.ports.web.auth.dto.LoginRequest;
import com.bader.ports.web.auth.dto.RegisterCustomerRequest;
import com.bader.ports.web.security.JwtTokenUtil;
import com.bader.ports.web.security.SecurityConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/iam")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));
        String token = jwtTokenUtil.generateToken(authenticate);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
    }

    @PostMapping("/register/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> registerCustomer(@RequestBody @Valid RegisterCustomerRequest registerCustomerRequest) {
        boolean userWasAdded = userService.registerCustomer(registerCustomerRequest.getUsername(), passwordEncoder.encode(registerCustomerRequest.getPassword()), SecurityConfiguration.CUSTOMER, registerCustomerRequest.getFirstName(), registerCustomerRequest.getLastName());
        return userWasAdded ? ResponseEntity.ok().build() : ResponseEntity.status(409).build();
    }

    @PostMapping("/register/seller")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> registerSeller(@RequestBody @Valid LoginRequest loginRequest) {
        boolean userWasAdded = userService.registerSeller(loginRequest.getUsername(), passwordEncoder.encode(loginRequest.getPassword()), SecurityConfiguration.SELLER);
        return userWasAdded ? ResponseEntity.ok().build() : ResponseEntity.status(409).build();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDetailResponse> getCustomerDetails(@PathVariable("id") UUID id) {
        return ResponseEntity.of(
                userService.getCustomer(id)
                        .map(this::toCustomerDetailResponse)
        );
    }

    @GetMapping("/customer/search")
    public ResponseEntity<CustomerIdResponse> findCustomerByEmail(@RequestParam String email) {
        return ResponseEntity.of(
                userService.getCustomerByEmail(email).map(this::toCustomerIdResponse)
        );
    }

    private CustomerIdResponse toCustomerIdResponse(Customer customer) {
        return new CustomerIdResponse(customer);
    }

    private CustomerDetailResponse toCustomerDetailResponse(Customer customer) {
        return new CustomerDetailResponse(customer);
    }
}
