package com.lecsures.section2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lecsures.section2.dtos.CustomerDto;
import com.lecsures.section2.models.Customer;
import com.lecsures.section2.repositories.CustomerRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping()
    public ResponseEntity<String> register(@RequestBody CustomerDto customerDto) {
        try {
            String hashPassword = this.passwordEncoder.encode(customerDto.getPassword());
            
            Customer customer = new Customer();
            customer.setEmail(customerDto.getEmail());
            customer.setPwd(hashPassword);
            customer.setRole("sale");

            this.customerRepository.save(customer);

            return ResponseEntity.status(HttpStatus.OK).body("Successful");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
