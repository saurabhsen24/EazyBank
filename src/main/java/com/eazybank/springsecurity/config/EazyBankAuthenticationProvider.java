package com.eazybank.springsecurity.config;

import com.eazybank.springsecurity.entities.Authority;
import com.eazybank.springsecurity.entities.Customer;
import com.eazybank.springsecurity.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EazyBankAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        List<Customer> customerList = customerRepository.findByEmail(email);

        if(!CollectionUtils.isEmpty(customerList)){
            if(passwordEncoder.matches(password,customerList.get(0).getPwd())) {
                return new UsernamePasswordAuthenticationToken(email,password,getGrantedAuthorities(customerList.get(0).getAuthorities()));
            } else {
                throw new BadCredentialsException("Invalid Credentials!");
            }
        } else {
            throw new BadCredentialsException("No User is registered with this details");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        List<GrantedAuthority> authorityList = authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return authorityList;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
