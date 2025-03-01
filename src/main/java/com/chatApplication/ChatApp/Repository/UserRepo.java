package com.chatApplication.ChatApp.Repository;

import com.chatApplication.ChatApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    User findByname(String name);
}
