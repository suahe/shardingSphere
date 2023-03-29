package com.sah.shardingshere.security;

import com.sah.shardingshere.common.CommonConstant;
import com.sah.shardingshere.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //check Token
        if (checkJWTToken(request)) {
            //解析token中的认证信息
            Optional<Claims> claimsOptional = validateToken(request)
                    .filter(claims -> claims.get("authorities") != null);
            if (claimsOptional.isPresent()) {
                List<String> authoritiesList = castList(claimsOptional.get().get("authorities"), String.class);
                List<SimpleGrantedAuthority> authorities = authoritiesList
                        .stream().map(String::valueOf)
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(claimsOptional.get().getSubject(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    private Optional<Claims> validateToken(HttpServletRequest req) {
        String jwtToken = req.getHeader(CommonConstant.X_ACCESS_TOKEN);
        try {
            return JwtUtil.parseAccessTokenClaims(jwtToken);
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            //输出日志
            return Optional.empty();
        }
    }

    private boolean checkJWTToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        return authenticationHeader != null;
    }
}
