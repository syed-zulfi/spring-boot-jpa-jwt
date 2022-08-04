package com.royalfoods.tastytables.security;

import com.royalfoods.tastytables.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
@Service
public class SecurityUserService implements UserDetailsService {
    @Autowired
    UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username){
        UserDetails userDetails = repository
                .findByEmail(username)
                .orElseThrow(()->
                        new UsernameNotFoundException(
                                "Invalid email, please use your valid email to login")
                );
        return userDetails;
    }
}
