package com.bader.ports.web.IAM;

import com.bader.domain.IAM.IAMService;
import com.bader.domain.IAM.model.Customer;
import com.bader.ports.web.IAM.dto.request.CreateCustomerRequest;
import com.bader.ports.web.IAM.dto.response.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/iam")
public class IAMController {
    private final IAMService iamService;

    public IAMController(IAMService iamService) {
        this.iamService = iamService;
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CreateCustomerRequest request){
        Optional<Customer> createdCustomer = iamService.createCustomer(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPhone());
        if (createdCustomer.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            return ResponseEntity.of(createdCustomer.map(this::toCustomerResponse));
        }
    }

    private CustomerResponse toCustomerResponse(Customer customer){
        return new CustomerResponse(customer);
    }
}
