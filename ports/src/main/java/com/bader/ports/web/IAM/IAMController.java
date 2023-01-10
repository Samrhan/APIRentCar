package com.bader.ports.web.IAM;

import com.bader.domain.IAM.IAMService;
import com.bader.domain.IAM.model.Customer;
import com.bader.ports.web.IAM.dto.request.CreateCustomerRequest;
import com.bader.ports.web.IAM.dto.response.CustomerDetailResponse;
import com.bader.ports.web.IAM.dto.response.CustomerIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/iam")
public class IAMController {
    private final IAMService iamService;

    public IAMController(IAMService iamService) {
        this.iamService = iamService;
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDetailResponse> getCustomerDetails(@PathVariable("id") UUID id){
        return ResponseEntity.of(
                iamService.getCustomer(id)
                        .map(this::toCustomerDetailResponse)
        );
    }

    @GetMapping("/customer/search")
    public ResponseEntity<CustomerIdResponse> findCustomerByEmail(@RequestParam String email){
        return ResponseEntity.of(
                iamService.getCustomerByEmail(email).map(this::toCustomerIdResponse)
        );
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerIdResponse> createCustomer(@RequestBody @Valid CreateCustomerRequest request){
        Optional<Customer> createdCustomer = iamService.createCustomer(request.getFirstName(), request.getLastName(), request.getPhone(), request.getEmail());
        if (createdCustomer.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            return ResponseEntity.of(createdCustomer.map(this::toCustomerIdResponse));
        }
    }

    private CustomerIdResponse toCustomerIdResponse(Customer customer){
        return new CustomerIdResponse(customer);
    }

    private CustomerDetailResponse toCustomerDetailResponse(Customer customer){
        return new CustomerDetailResponse(customer);
    }
}
