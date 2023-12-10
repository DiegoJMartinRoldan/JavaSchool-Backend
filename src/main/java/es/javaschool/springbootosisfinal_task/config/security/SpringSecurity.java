package es.javaschool.springbootosisfinal_task.config.security;

import es.javaschool.springbootosisfinal_task.config.jwt.JwtFilter;
import es.javaschool.springbootosisfinal_task.config.security.ClientUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurity{

    @Autowired
    private ClientUserDetailsService clientUserDetailsService;


    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;


    @Bean
    public  JwtFilter jwtFilter(){
        //HandleException for the filter en Jwt
        return  new JwtFilter(handlerExceptionResolver);
    }




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
                                         "/client/changePassword",
                                         "/client/addToCart",
                                         "/client/cart",
                                         "/product/list",
                                         "/product/getby/{id}",
                                         "/client/login",
                                         "/client/refreshToken",
                                         "/client/cart",
                                         "/client/addToCart",
                                         "/client/cartDelete").permitAll()
                            .requestMatchers("/client/**",
                                             "/clientsAddress/**",
                                             "/orders/**",
                                             "/product/**",
                                             "/orderHasProduct/**",
                                             "/statistics/**")
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
               .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
               .build();
    }



    //BCryptPassword
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }



    //Authentication Provider
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


    //Cors Filter -  React Front end
    @Bean
    public FilterRegistrationBean corsFilter(){
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedHeaders(Arrays.asList(
                org.springframework.http.HttpHeaders.AUTHORIZATION,
                org.springframework.http.HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.PATCH.name()
        ));
        configuration.setMaxAge(4000L);
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new CorsFilter(urlBasedCorsConfigurationSource));
        filterRegistrationBean.setOrder(-102);
        return filterRegistrationBean;
    }



}
