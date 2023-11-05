//package com.example.Library.security;
//
//import com.example.Library.models.Customer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//public class CustomerDetails implements UserDetails {
//
//    private final Customer customer;
//
//    @Autowired
//    public CustomerDetails(Customer customer) {
//        this.customer = customer;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.customer.getCredentials().getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.customer.getCredentials().getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    public Customer getCustomerCredentials() {
//        return this.customer;
//    }
//}
