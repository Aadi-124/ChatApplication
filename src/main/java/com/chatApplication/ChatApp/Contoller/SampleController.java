package com.chatApplication.ChatApp.Contoller;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

//    In spring security when we add the spring security and pass the username and password with basic auth then we can only access the resources of the data !
//    But when we want to make any updates i the application then we need to use CSRF token
//    For example without csrf token we can access the get request but cannot access put and post, DELETE request
//    in order to access the post and put, Delete request we need to send the csrf token with the request



    // Method to get the CSRF token
    @GetMapping("/get-csrfToken")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the Chat Application";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createData(){
        return ResponseEntity.status(201).body("Created!");
    }




    @GetMapping("/public")
    public String publicPath(){
        return "Public Access!";
    }

    @GetMapping("/normal")
    public String NormailPath(){
        return "Normal Access!";
    }

    @GetMapping("/admin")
    public String adminPath(){
        return "Admin Access";
    }
}
