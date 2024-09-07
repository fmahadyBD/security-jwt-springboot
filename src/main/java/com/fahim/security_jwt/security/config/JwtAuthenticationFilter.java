package com.fahim.security_jwt.security.config;

import com.fahim.security_jwt.security.services.JwtService;
import com.fahim.security_jwt.security.services.MyUserdetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    //It will use to filter the JWT TOKEN to filter the request

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserdetailsService myUserdetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        //if authHeader is null or Our Token is not start with Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //cut the Bearer with 7
    String jwt = authHeader.substring(7);
        // find the username of the login user
    String username=jwtService.extractUsername(jwt);


    if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null) {

        //find the user's details by using his/her username
        UserDetails userDetails = myUserdetailsService.loadUserByUsername(username);

        //check the userDetails is not return null and token is valid.

        if(userDetails !=null && jwtService.isTokenValid(jwt)){

            // create new username and password Authentication Token
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    userDetails.getPassword(),
                    userDetails.getAuthorities());

            // set Details into this token
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // now set
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }
    }
    filterChain.doFilter(request, response);

    }


}
