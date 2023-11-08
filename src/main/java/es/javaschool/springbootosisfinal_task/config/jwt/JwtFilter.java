package es.javaschool.springbootosisfinal_task.config.jwt;


import es.javaschool.springbootosisfinal_task.config.security.ClientUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {

    //When the request is made, it is filtered by this class and the token is verified to be correct.

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ClientUserDetailsService clientUserDetailsService;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    public JwtFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Request authorization header
        String jwtHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        try {
            //If the header is not null and begins with "Bearer", the token is the header minus 7 characters and the name is extracted from the service
            if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
                token = jwtHeader.substring(7);
                email = jwtService.extractClientEmail(token);
            }
            //If the name we extract is not null and does not have any authentication, we use clientUserDetailsService to load the user data
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = clientUserDetailsService.loadUserByUsername(email);

                //Token is validated and authentication details are given
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                //When authenticated, the method continues its course
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);


        }
    }
}
