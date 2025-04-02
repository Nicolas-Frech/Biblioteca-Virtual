package br.com.nicolasfrech.biblioteca_online.infra.security;

import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.infra.security.token.TokenService;
import br.com.nicolasfrech.biblioteca_online.infra.user.gateway.UserEntityMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    private final UserRepository userRepository;

    private final UserEntityMapper mapper;

    public SecurityFilter(UserRepository userRepository, UserEntityMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);

        if(token != null) {
            var username = tokenService.getSubject(token);

            var user = userRepository.findByUsername(username);

            var userEntity = mapper.toEntity(user);

            var authenticationObj = new UsernamePasswordAuthenticationToken(user, null, userEntity.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationObj);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if(authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
