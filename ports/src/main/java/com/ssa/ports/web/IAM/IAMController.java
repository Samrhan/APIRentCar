package com.ssa.ports.web.IAM;

import com.ssa.domain.IAM.IAMService;
import com.ssa.domain.IAM.model.Customer;
import com.ssa.ports.web.IAM.dto.CustomerDetailResponse;
import com.ssa.ports.web.IAM.dto.CustomerIdResponse;
import com.ssa.ports.web.IAM.dto.LoginRequest;
import com.ssa.ports.web.IAM.dto.RegisterCustomerRequest;
import com.ssa.ports.web.security.JwtTokenUtil;
import com.ssa.ports.web.security.SecurityConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.ssa.ports.web.security.SecurityConfiguration.PRE_AUTHORIZE_SELLER;

@RestController
@RequestMapping("/iam")
public class IAMController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final IAMService IAMService;

    private final PasswordEncoder passwordEncoder;

    public IAMController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, IAMService IAMService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.IAMService = IAMService;
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
        boolean userWasAdded = IAMService.registerCustomer(registerCustomerRequest.getUsername(), passwordEncoder.encode(registerCustomerRequest.getPassword()), SecurityConfiguration.CUSTOMER, registerCustomerRequest.getFirstName(), registerCustomerRequest.getLastName());
        return userWasAdded ? ResponseEntity.ok().build() : ResponseEntity.status(409).build();
    }

    @PostMapping("/register/seller")
    @ResponseStatus(HttpStatus.CREATED)
    // @PreAuthorize(PRE_AUTHORIZE_ADMIN) // Should be reserved to admins, but isn't to make testing the project easier
    public ResponseEntity<Object> registerSeller(@RequestBody @Valid LoginRequest loginRequest) {
        boolean userWasAdded = IAMService.registerSeller(loginRequest.getUsername(), passwordEncoder.encode(loginRequest.getPassword()), SecurityConfiguration.SELLER);
        return userWasAdded ? ResponseEntity.ok().build() : ResponseEntity.status(409).build();
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize(PRE_AUTHORIZE_SELLER)
    public ResponseEntity<CustomerDetailResponse> getCustomerDetails(@PathVariable("id") UUID id) {
        return ResponseEntity.of(
                IAMService.getCustomer(id)
                        .map(this::toCustomerDetailResponse)
        );
    }

    @GetMapping("/customer/search")
    @PreAuthorize(PRE_AUTHORIZE_SELLER)
    public ResponseEntity<CustomerIdResponse> findCustomerByEmail(@RequestParam String email) {
        return ResponseEntity.of(
                IAMService.getCustomerByEmail(email).map(this::toCustomerIdResponse)
        );
    }

    private CustomerIdResponse toCustomerIdResponse(Customer customer) {
        return new CustomerIdResponse(customer);
    }

    private CustomerDetailResponse toCustomerDetailResponse(Customer customer) {
        return new CustomerDetailResponse(customer);
    }
}
