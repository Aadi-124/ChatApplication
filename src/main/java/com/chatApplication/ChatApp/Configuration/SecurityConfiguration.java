package com.chatApplication.ChatApp.Configuration;


import com.chatApplication.ChatApp.Service.JWTFilter;
import com.chatApplication.ChatApp.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfiguration {



//    Role Prefix Issue (ROLE_): Spring Security by default expects role names to have a ROLE_ prefix. So, if you're storing roles like ADMIN in the database, Spring Security will check for ROLE_ADMIN rather than just ADMIN.
//
//    You can fix this by either:
//
//    Adding the ROLE_ prefix to your roles when storing them in the database.
//            Or, configuring Spring Security to ignore the ROLE_ prefix.

    @Autowired
    public JWTFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        Customizer.withDefaults(); means apply the default security configurations
//
        http
                // This disables the Csrf Protection
                .csrf(csrf -> csrf.disable())
                // It uses spring-boot's default csrf protection configuration
//                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/create","/login").permitAll()
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/public").hasAnyRole("ADMIN","NORMAL","PUBLIC")
                                .requestMatchers("/normal").hasAnyRole("ADMIN","NORMAL")
                                .anyRequest().authenticated())

                // It tells that all request should be authenticated
//                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())


                .formLogin(f->f.disable())
                    // This is applying the default security.
                .httpBasic(Customizer.withDefaults())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//
//        http.csrf(customizer -> customizer.disable());
        return http.build();
    }


    // In order to make our own users we need to add a bean of type UserDetails service
    // By default spring allows only one user to access the api but by using this we can create multiple users with different attributes
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User.withDefaultPasswordEncoder().username("Aaditya").password("1234").roles("USER").build();
//        UserDetails user2 = User.withDefaultPasswordEncoder().username("Sham").password("s123").roles("USER").build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    // The above user information are stored into the spring application cache
//    but if we want to get all the users from the data abase then we need to provide our own authentication provides as demonstrated below:-


//     The authentication provider is used to interacting with the database and to get the user information from it

    @Autowired
    public MyUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    // Authentication Provider
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }



    // Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }



}
