package com.sah.shardingshere.security;

import com.sah.shardingshere.security.model.AuthUser;
import com.sah.shardingshere.security.model.LoginDTO;
import com.sah.shardingshere.security.model.LoginVO;
import com.sah.shardingshere.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Service
public class LoginService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUserService authUserService;


    public LoginVO login(LoginDTO loginDTO) {
        //创建Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                        loginDTO.getPassword());

        //调用AuthenticationManager的authenticate方法进行认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (authentication == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        //登录成功以后用户信息、
        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        LoginVO loginVO = new LoginVO();
        loginVO.setUserName(authUser.getUsername());
        loginVO.setAccessToken(JwtUtil.createAccessToken(authUser));
        loginVO.setRefreshToken(JwtUtil.createRefreshToken(authUser));

        return loginVO;
    }


    public LoginVO refreshToken(String accessToken, String refreshToken) {
        if (!JwtUtil.validateRefreshToken(refreshToken) && !JwtUtil.validateWithoutExpiration(accessToken)) {
            throw new RuntimeException("认证失败");
        }
        Optional<String> userName = JwtUtil.parseRefreshTokenClaims(refreshToken).map(Claims::getSubject);
        if (userName.isPresent()) {
            AuthUser authUser = (AuthUser) authUserService.loadUserByUsername(userName.get());
            if (Objects.nonNull(authUser)) {
                LoginVO loginVO = new LoginVO();
                loginVO.setUserName(authUser.getUsername());
                loginVO.setAccessToken(JwtUtil.createAccessToken(authUser));
                loginVO.setRefreshToken(JwtUtil.createRefreshToken(authUser));
                return loginVO;
            }
            throw new InternalAuthenticationServiceException("用户不存在");
        }
        throw new RuntimeException("认证失败");
    }
}
