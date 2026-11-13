package cn.org.faster.cms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 解析 {@code Authorization: Bearer <token>} 并注入安全上下文。
 *
 * <p>只处理 {@code /admin} 前缀，站点前台请求不承担解析开销。</p>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    private final JwtService jwtService;
    private final TokenDenylist tokenDenylist;
    private final AdminUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   TokenDenylist tokenDenylist,
                                   AdminUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.tokenDenylist = tokenDenylist;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/admin");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith(BEARER)) {
            authenticate(header.substring(BEARER.length()).trim(), request);
        }
        chain.doFilter(request, response);
    }

    private void authenticate(String token, HttpServletRequest request) {
        try {
            Jwt jwt = jwtService.parse(token);
            Long userId = Long.valueOf(jwt.getSubject());
            if (tokenDenylist.isRevoked(jwt.getId())
                    || tokenDenylist.isStale(userId, jwt.getIssuedAt())) {
                SecurityContextHolder.clearContext();
                return;
            }
            AdminUserDetails details = userDetailsService.loadById(userId);
            var authentication = UsernamePasswordAuthenticationToken.authenticated(
                    details, token, details.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (RuntimeException ex) {
            SecurityContextHolder.clearContext();
        }
    }
}
