package com.chatApplication.ChatApp.Service;

import com.chatApplication.ChatApp.Model.User;
import com.chatApplication.ChatApp.Model.UserPrinciples;
import com.chatApplication.ChatApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByname(username);
        if(user == null){
            System.out.println("User Not Found!");
            throw new UsernameNotFoundException("User not Found!");
        }

        // Now here we need to return the object of type userdetails but as userdetails is an interface we cannot pass that object
//         os we need to create our own custome class that implements userdetails and pass from here.
//         in this case we are creating the userprinciples as an implementation class of userdetails.


        return new UserPrinciples(user);
    }
}
