package com.bader.domain.IAM;

import com.bader.domain.IAM.model.Authority;
import com.bader.domain.IAM.model.Customer;
import com.bader.domain.IAM.model.User;
import com.bader.domain.IAM.repository.CustomerRepository;
import com.bader.domain.IAM.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryBasedIAMService implements IAMService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public RepositoryBasedIAMService(UserRepository userRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean registerCustomer(String username, String password, String role, String firstName, String lastName) {
        List<Authority> authorities = List.of(new Authority(role));
        Optional<User> user = userRepository.createUser(new User(username, password, true, authorities));
        if (user.isEmpty()) {
            return false;
        }

        Optional<Customer> addedCustomer = this.customerRepository.createCustomer(firstName, lastName, user.get().getUsername());
        return addedCustomer.isPresent();
    }

    @Override
    public boolean registerSeller(String username, String password, String role) {
        List<Authority> authorities = List.of(new Authority(role));
        Optional<User> user = userRepository.createUser(new User(username, password, true, authorities));
        return user.isPresent();
    }

    @Override
    public Optional<Customer> getCustomer(UUID id) {
        return this.customerRepository.getCustomer(id);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return this.customerRepository.getCustomerByEmail(email);
    }
}
