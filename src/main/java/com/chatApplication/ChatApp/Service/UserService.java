package com.chatApplication.ChatApp.Service;


import com.chatApplication.ChatApp.Entity.User;
import com.chatApplication.ChatApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepo repo;

    public User addUser(User user){

        try{
            repo.save(user);
            return user;
        } catch(Exception E){
            E.printStackTrace();
            return null;
        }
    }

}
