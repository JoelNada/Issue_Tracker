package com.joel.issue_tracker.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class JWTAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    @NullMarked
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws
            IOException, ServletException
    {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String errorMessage = (String)request.getAttribute("error");

        if(errorMessage==null){
            errorMessage = "Unauthorized - Please provide valid credentials";
        }
        System.out.println("Error message from authException: " + errorMessage);

        // Build structured JSON response
        String jsonResponse = String.format(
                "{\"timestamp\":\"%s\", \"status\":401, \"error\":\"Unauthorized\", \"message\":\"%s\"}",
                java.time.Instant.now(), errorMessage);

        response.getWriter().write(jsonResponse);

    }
}

