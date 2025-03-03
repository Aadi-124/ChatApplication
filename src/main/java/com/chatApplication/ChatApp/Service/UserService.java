package com.chatApplication.ChatApp.Service;


import com.chatApplication.ChatApp.Entity.User;
import com.chatApplication.ChatApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepo repo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JWTService jwtService;

    public User addUser(User user){

        try{
            repo.save(user);
            return user;
        } catch(Exception E){
            E.printStackTrace();
            return null;
        }
    }

    public String verify(User user){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getName());
        }
        return "INVALID_USER";
    }

}
