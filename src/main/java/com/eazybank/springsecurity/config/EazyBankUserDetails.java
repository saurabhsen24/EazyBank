package com.eazybank.springsecurity.config;

import com.eazybank.springsecurity.entities.Customer;
import com.eazybank.springsecurity.entities.SecurityCustomer;
import com.eazybank.springsecurity.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public class EazyBankUserDetails implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> customerList = customerRepository.findByEmail(username);
        if(customerList.size()==0) {
            throw new UsernameNotFoundException("User Details not found for the user: " + username);
        }

        return new SecurityCustomer(customerList.get(0));
    }
}
