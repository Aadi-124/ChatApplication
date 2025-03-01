package com.chatApplication.ChatApp.Configuration;


import com.chatApplication.ChatApp.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        Customizer.withDefaults(); means apply the default security configurations

        http
                .csrf(customizer -> customizer.disable())  // This disables the Csrf Protection
//                .csrf(Customizer.withDefaults())   // It uses springboot's default csrf protection configuration
                .authorizeHttpRequests(request -> request.anyRequest().authenticated()) // It tells that all request should be authenticated
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


    // In order to make our own users we need to add a bean of type UserDetails service
    // By default spring allows only one user to access the api but by using this we can create multiple users with different attributes
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.withDefaultPasswordEncoder().username("Aaditya").password("1234").roles("USER").build();
        UserDetails user2 = User.withDefaultPasswordEncoder().username("Sham").password("s123").roles("USER").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    // The above user informations are stored into the sprinng application cache
//    but if we want to get all the users from the data abase then we need to provide our own authentication provides as demonstrated below:-


//     The authentication provider is used to interacting with the database and to get the user informations from it

    @Autowired
    public MyUserDetailsService userDetailsService;


    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(userDetailsService);

        return provider;

    }




}
