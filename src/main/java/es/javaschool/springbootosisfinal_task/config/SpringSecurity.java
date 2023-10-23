package es.javaschool.springbootosisfinal_task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurity{

    @Autowired
    private  ClientUserDetailsService clientUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;



                       //Spring Security AUTHENTICATION
   @Bean
   public UserDetailsService userDetailsService(){
       // ROLE_ADMIN , ROLE_USER from the database
       return  username -> clientUserDetailsService.loadUserByUsername(username);

   }


                        //Spring Security AUTHORIZATION
    @Bean
    //http url can be accessed
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        // CSRF denied
       return httpSecurity.csrf(AbstractHttpConfigurer::disable)
               //Permit or authenticated requests
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/client/welcome",
                                         "/client/create",
                                         "/client/update/{id}",
                                         "/client/getby/{id}",
                                         "/client/delete/{id}",
                                         "/client/authjwt").permitAll()
                            .requestMatchers("/client/**",
                                             "/clientsAddress/**",
                                             "/orders/**",
                                             "/product/**",
                                             "/orderHasProduct/**")
                            .authenticated();

                })
               .formLogin(Customizer.withDefaults())
                // Session management
               .sessionManagement
                        (sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
               //Authentication Provider Method Filter
               .authenticationProvider(authenticationProvider())
               //Jwt Filter
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
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


     //JWT
     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
     }


}
