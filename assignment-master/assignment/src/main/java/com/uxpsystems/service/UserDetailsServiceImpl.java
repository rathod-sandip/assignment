package com.uxpsystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uxpsystems.model.User;
import com.uxpsystems.model.UserPrinciple;
import com.uxpsystems.repository.UserRepository;



/**
 * @author Sandip Rathod
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    UserRepository userRepository;
 
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    	
        User user = userRepository.findByUsername(username)
                	.orElseThrow(() -> 
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
        );
 
        return UserPrinciple.build(user);
    }

    public User getUserByEmail(String email)
    {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user is not found ..!"));
    }

    public User getLoginUser() {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         User user = userRepository.findByUsername(authentication.getName()).orElse(null);
         
         if(user != null)
         {
        	 return user;
         }
         throw new RuntimeException("User Not Found ..!");
    }

}