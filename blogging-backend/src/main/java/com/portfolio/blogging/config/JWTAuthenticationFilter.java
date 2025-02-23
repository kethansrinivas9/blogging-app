package com.portfolio.blogging.config;

import com.portfolio.blogging.entity.User;
import com.portfolio.blogging.service.CustomUserDetailsService;
import com.portfolio.blogging.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //This needs to be passed in the subsequent requests after Email/Password authentication
        //once the JWT is generated
        String authHeader = request.getHeader("Authorization");

        //usual JWT format
        //Authorization: Bearer <token>
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            //This will invoke next filter in filter chain
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        //Extract email from token
        final String userName = jwtService.extractUserName(jwt);

        //SecurityContextHolder can be called anywhere in the code to get Authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //verify if username/email exists and user isn't authenticated
        if(userName != null && authentication == null) {
            User user = userDetailsService.loadUserByUsername(userName);

            //validate the token
            if(jwtService.isTokenValid(jwt, user)) {
                //Create authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

                //Add request details to auth token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        filterChain.doFilter(request, response);
    }
}
