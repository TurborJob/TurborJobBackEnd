package com.turborvip.core.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turborvip.core.config.exception.ForbiddenException;
import com.turborvip.core.domain.repositories.TokenRepository;
import com.turborvip.core.service.GMailerService;
import com.turborvip.core.service.TokenService;
import com.turborvip.core.service.impl.JwtService;
import com.turborvip.core.model.entity.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final TokenRepository tokenRepository;

//    @Autowired
//    private final GMailerService gMailerService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // Todo find token in with value token in request
                String token = authorizationHeader.substring("Bearer " .length());

                // 1. Abnormal : bat thuong
                Token refreshTokenUsed = tokenService.findByRefreshTokenUsed(token).orElse(null);
                if (refreshTokenUsed != null) {
                    Token refreshTokenUsedBD = tokenService.findFirstTokenByValue(refreshTokenUsed.getValue()).orElseThrow(() -> new Exception("Don't find token"));
                    // Todo remove all token of userId
                    List<Token> listByUser = tokenService.findByUserId(refreshTokenUsed.getCreateBy().getId());
                    // back list

                    // remove
                    listByUser.forEach(tokenRepository::delete);
                    // send mail
                    /*
                     *  new GMailerServiceImpl().sendEmail(refreshTokenUsedBD.getCreateBy().getEmail(),
                     *                             "Warning warning !!! Turborvip app",
                     *                             "Another try attach your account you should change password now!");
                      */
                    throw new ForbiddenException("Some thing wrong happened ! Please re login ! ");
                }

                Token tokenDB = tokenService.findFirstTokenByValue(token).orElseThrow(() -> new Exception("Don't find any token"));
                // Todo decode token using jwt
                Claims claims = jwtService.validationToken(tokenDB.getValue(), tokenDB.getVerifyKey());
                String username = tokenDB.getCreateBy().getUsername();
                List<String> roles = claims.get("roles", List.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                roles.forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        filterChain.doFilter(request, response);
    }

}
