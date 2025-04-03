package br.com.nicolasfrech.biblioteca_online.infra.security;

import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.domain.user.UserRole;
import br.com.nicolasfrech.biblioteca_online.infra.security.token.TokenService;
import br.com.nicolasfrech.biblioteca_online.infra.user.gateway.UserEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserEntity;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserRepositoryJPA;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    private final UserRepositoryJPA jpaRepository;

    private final UserEntityMapper mapper;

    public SecurityFilter(UserRepositoryJPA jpaRepository, UserEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            var token = recoverToken(request);

            if (token != null) {
                var username = tokenService.getSubject(token);

                if (username == null) {
                    System.out.println("❌ Token inválido ou expirado.");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token inválido ou expirado.");
                    return;
                }

                var user = jpaRepository.findByUsername(username);

                if (user == null) {
                    System.out.println("❌ Usuário não encontrado para username: " + username);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Usuário não encontrado.");
                    return;
                }

                var authenticationObj = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationObj);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            System.out.println("🚨 Erro ao processar autenticação: " + e.getMessage());
            SecurityContextHolder.clearContext(); // Limpa qualquer autenticação inválida
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Erro na autenticação");
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if(authHeader != null) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}
