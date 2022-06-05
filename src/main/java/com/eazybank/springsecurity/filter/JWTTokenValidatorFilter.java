package com.eazybank.springsecurity.filter;

import com.eazybank.springsecurity.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(ApplicationConstants.JWT_HEADER);
        if(jwt != null) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(ApplicationConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                        .parseClaimsJws(jwt).getBody();
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e) {
                throw new BadCredentialsException("Invalid Credentials!");
            }
        }

        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/user");
    }
}
