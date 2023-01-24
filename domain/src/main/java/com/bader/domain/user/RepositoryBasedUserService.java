package com.bader.domain.user;

import com.bader.domain.user.model.Authority;
import com.bader.domain.user.model.Customer;
import com.bader.domain.user.model.User;
import com.bader.domain.user.repository.CustomerRepository;
import com.bader.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryBasedUserService implements UserService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public RepositoryBasedUserService(UserRepository userRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void registerCustomer(String username, String password, String role, String firstName, String lastName) {
        List<Authority> authorities = List.of(new Authority(role));
        User user = userRepository.save(new User(username, password, true, authorities));
        this.customerRepository.createCustomer(firstName, lastName, user.getUsername());
    }

    @Override
    public void registerSeller(String username, String password, String role) {
        List<Authority> authorities = List.of(new Authority(role));
        userRepository.save(new User(username, password, true, authorities));
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
