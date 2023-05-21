package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import tacos.model.User;
import tacos.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * Using In-Memory User Store
     *
     * 这个方法的的User类, 和下面方法的User类，不是同一个User类
     * 这个方法的User类，是SpringSecurity 提供的
     */
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//        List<UserDetails> usersList = new ArrayList<>();
//        usersList.add(new User(
//                "buzz", encoder.encode("password"),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
//        ));
//
//        usersList.add(new User(
//                "woody", encoder.encode("password"),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
//        ));
//
//        return new InMemoryUserDetailsManager(usersList);
//    }




    /**
     * Not A Method Reload,
     * Only have a userDetailsService() method.
     * Can't use both of them in the same time.
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo){
        return username -> {
            User user = userRepo.findByUsername(username);

            if(user != null) {
                return user;
            }

            throw new UsernameNotFoundException("User '" + username + "' not found" );
        };
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeRequests()
//                .antMatchers("/design", "/orders").hasRole("USER")
//                .antMatchers("/",  "/**").permitAll()

                // using Spring Expressions Language which contain "access()"
                .antMatchers("/design", "/orders").access("hasRole('USER')")
                .antMatchers("/",  "/**").access("permitAll()")

                .and()
                    .formLogin()
                        .loginPage("/login")

//                Add third-part authentication
                .and()
                    .oauth2Login()
                        .loginPage("/login")

                .and()
                    .logout()
                        .logoutSuccessUrl("/")
//                .and()
//                    .csrf()
//                        .disable()

                .and()
                .build();
    }
}


