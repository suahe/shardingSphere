package com.sah.shardingshere.util;

import com.sah.shardingshere.common.CommonConstant;
import com.sah.shardingshere.entity.SysUser;
import com.sah.shardingshere.security.model.LoginDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
public class JwtUtil {


    /**
     * 访问令牌有效期
     */
    private static final Long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000L;


    /**
     * 访问令牌的秘钥
     */
    private static final String ACCESS_TOKEN_KEY = "hello202312345997wwwwweeskkscsdjcalslkasaswyegdhcskdcsdhcksdjcksdcskdsdhcksdkchs";


    /**
     * 刷新令牌有效期
     */
    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L;


    /**
     * 刷新令牌的秘钥
     */
    private static final String REFRESH_TOKEN_KEY = "nihao2023dadadajjufncncjskeiinvkaodiasdisaoidaididhscchsducuiwqijakdaksjdaskjdakhdaksjdjas";


    public static String getUUID() {
        String token = UUID.randomUUID().toString().replace("-", "");
        return token;
    }

    public static String createJWTToken(LoginDTO loginDTO, long timeToExpire) {
        return createJWTToken(loginDTO, timeToExpire,
                new SecretKeySpec(ACCESS_TOKEN_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
    }

    public static String createAccessToken(LoginDTO loginDTO) {
        return createJWTToken(loginDTO, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public static String createRefreshToken(LoginDTO loginDTO) {
        return createJWTToken(loginDTO, REFRESH_TOKEN_EXPIRE_TIME,
                new SecretKeySpec(REFRESH_TOKEN_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
    }


    /**
     * 根据用户信息生成一个 JWT
     *
     * @param loginDTO  用户信息
     * @param timeToExpire 毫秒单位的失效时间
     * @param signKey      签名使用的 key
     * @return JWT
     */
    private static String createJWTToken(LoginDTO loginDTO, long timeToExpire,
                                         Key signKey) {
        return Jwts
                .builder()
                //唯一ID
                .setId(getUUID())
                .setSubject(loginDTO.getUsername())
                .claim("userId", loginDTO.getUserId())
                .claim("telephone", loginDTO.getTelephone())
                .claim("depId", loginDTO.getDepId())
                .claim("userId", loginDTO.getUserId())
                //权限信息
                .claim("authorities",
                        loginDTO.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                //授权信息
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + timeToExpire))
                //签名
                .signWith(signKey, SignatureAlgorithm.HS512).compact();
    }


    public static boolean validateAccessToken(String jwtToken) {
        return validateToken(jwtToken, new SecretKeySpec(ACCESS_TOKEN_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
    }

    public static boolean validateRefreshToken(String jwtToken) {
        return validateToken(jwtToken, new SecretKeySpec(REFRESH_TOKEN_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
    }

    public static boolean validateToken(String jwtToken, Key signKey) {
        return parseClaims(jwtToken, signKey).isPresent();
    }


    public static Optional<Claims> parseAccessTokenClaims(String jwtToken) {
        return Optional.ofNullable(Jwts.parserBuilder().setSigningKey(new SecretKeySpec(ACCESS_TOKEN_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA512"))
                .build().parseClaimsJws(jwtToken).getBody());
    }

    public static Optional<Claims> parseRefreshTokenClaims(String jwtToken) {
        return Optional.ofNullable(Jwts.parserBuilder().setSigningKey(new SecretKeySpec(REFRESH_TOKEN_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA512"))
                .build().parseClaimsJws(jwtToken).getBody());
    }


    public static Optional<Claims> parseClaims(String jwtToken, Key signKey) {
        return Optional.ofNullable(Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(jwtToken).getBody());
    }


    public static boolean validateWithoutExpiration(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(new SecretKeySpec(ACCESS_TOKEN_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA512"))
                    .build().parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            if (e instanceof ExpiredJwtException) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据request中的token获取用户账号
     *
     * @return
     */
    public static LoginDTO getSysUserByToken() {
        String accessToken = getToken();
        if(StringUtils.isEmpty(accessToken)){
            return null;
        }
        Optional<Claims> optional = parseAccessTokenClaims(accessToken);
        if (!optional.isPresent()) {
            return null;
        }
        Claims claims = optional.get();
        String subject = claims.getSubject();
        String userId = claims.get("userId").toString();
        String telephone = claims.get("phone").toString();
        String depId = claims.get("depId").toString();
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(subject);
        loginDTO.setUserId(userId);
        loginDTO.setTelephone(telephone);
        loginDTO.setDepId(depId);
        return loginDTO;
    }

    public static String getToken() {
        HttpServletRequest request = SpringContextUtil.getHttpServletRequest();
        if(request==null){
            return "";
        }
        String accessToken = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        return accessToken;
    }
}
