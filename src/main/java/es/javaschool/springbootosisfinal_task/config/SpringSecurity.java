package es.javaschool.springbootosisfinal_task.config;

import es.javaschool.springbootosisfinal_task.services.clientServices.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurity{

    @Lazy
    @Autowired
    private ClientService clientService;



                       //Spring Security AUTHENTICATION
   @Bean
   public UserDetailsService userDetailsService(){
       // ROLE_ADMIN , ROLE_USER from the database
       return  username -> clientService.loadUserByUsername(username);

   }


                        //Spring Security AUTHORIZATION
    @Bean
    //http url can be accessed
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        // CSRF denied
       return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/client/welcome",
                                         "/client/create",
                                         "/client/update/{id}",
                                         "/client/getby/{id}",
                                         "/client/delete/{id}")
                                          .permitAll()
                            .requestMatchers("/client/**",
                                             "/clientsAddress/**")
                            .authenticated();
                })

               .formLogin(Customizer.withDefaults())

             //  //Custom Login Page
             //  .formLogin(formLogin -> formLogin
             //          .loginPage("/login").permitAll()
             //  )
                // Session management
               .sessionManagement
                        (sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        )
               //Filters
               .authenticationProvider(authenticationProvider())
               .build();
    }




    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


     @Bean
     public AuthenticationProvider authenticationProvider(){
         DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
         authenticationProvider.setUserDetailsService(userDetailsService());
         authenticationProvider.setPasswordEncoder(passwordEncoder());
         return  authenticationProvider;
     }


}
