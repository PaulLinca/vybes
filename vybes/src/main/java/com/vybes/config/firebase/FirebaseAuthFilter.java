package com.vybes.config.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.vybes.security.FirebasePrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class FirebaseAuthFilter extends OncePerRequestFilter {

    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String idToken = header.substring(7);
            try {
                FirebaseToken decoded = firebaseAuth.verifyIdToken(idToken);
                FirebasePrincipal principal =
                        new FirebasePrincipal(decoded.getUid(), decoded.getEmail());
                AbstractAuthenticationToken auth =
                        new AbstractAuthenticationToken(null) {
                            @Override
                            public Object getCredentials() {
                                return null;
                            }

                            @Override
                            public Object getPrincipal() {
                                return principal;
                            }

                            @Override
                            public String getName() {
                                return principal.email();
                            }
                        };
                auth.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex) {
                log.warn("Firebase token verification failed: {}", ex.getMessage());
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
