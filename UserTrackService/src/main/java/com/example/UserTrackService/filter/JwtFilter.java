package com.example.UserTrackService.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Key;

import static io.jsonwebtoken.Jwts.*;


public class JwtFilter extends GenericFilter {
    private static final String SECRET_KEY = "your-256-bit-secret"; // replace with your actual secret key

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authHeader = request.getHeader("authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            throw new ServletException("Missing or invalid authorization header");
        }

        String token = authHeader.substring(7);
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            request.setAttribute("claims", claims);
        } catch (Exception e) {
            throw new ServletException("Invalid token", e);
        }

        filterChain.doFilter(request, response);
    }
}

//public class JwtFilter extends GenericFilter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String authHeader = request.getHeader("authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer")) {
//            throw new ServletException("Missing or invalid authorization header");
//        }
//
//        String token = authHeader.substring(7);
//
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey("secretKey")
//                    .parseClaimsJws(token)
//                    .getBody();
//            request.setAttribute("claims", claims);
//        } catch (Exception e) {
//            throw new ServletException("Invalid token");
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//}
