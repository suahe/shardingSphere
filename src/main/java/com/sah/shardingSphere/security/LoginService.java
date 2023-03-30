package com.sah.shardingSphere.security;

import com.sah.shardingSphere.entity.SysUser;
import com.sah.shardingSphere.exception.LoginAuthException;
import com.sah.shardingSphere.security.model.AuthUser;
import com.sah.shardingSphere.security.model.LoginDTO;
import com.sah.shardingSphere.security.model.LoginVO;
import com.sah.shardingSphere.service.ISysUserService;
import com.sah.shardingSphere.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
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

    @Autowired
    private ISysUserService sysUserService;


    public LoginVO login(LoginDTO loginDTO) {
        //创建Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                        loginDTO.getPassword());

        //调用AuthenticationManager的authenticate方法进行认证
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (authentication == null) {
            throw new LoginAuthException("用户名或密码错误");
        }
        //登录成功以后用户信息、
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        loginDTO.setAuthorities(authUser.getAuthorities());
        LoginVO loginVO = new LoginVO();
        loginVO.setUserName(authUser.getUsername());
        loginVO.setAccessToken(JwtUtil.createAccessToken(loginDTO));
        loginVO.setRefreshToken(JwtUtil.createRefreshToken(loginDTO));
        return loginVO;
    }


    public LoginVO refreshToken(String accessToken, String refreshToken) {
        if (!JwtUtil.validateRefreshToken(refreshToken) && !JwtUtil.validateWithoutExpiration(accessToken)) {
            throw new LoginAuthException("认证失败");
        }
        Optional<String> userName = JwtUtil.parseRefreshTokenClaims(refreshToken).map(Claims::getSubject);
        if (userName.isPresent()) {
            AuthUser authUser = (AuthUser) authUserService.loadUserByUsername(userName.get());
            if (Objects.nonNull(authUser)) {
                SysUser sysUser = sysUserService.findByUsername(authUser.getUsername());
                if (Objects.nonNull(sysUser)) {
                    throw new LoginAuthException("用户不存在");
                }
                LoginDTO loginDTO = new LoginDTO();
                BeanUtils.copyProperties(loginDTO, sysUser);
                loginDTO.setUserId(sysUser.getId());
                loginDTO.setAuthorities(authUser.getAuthorities());
                LoginVO loginVO = new LoginVO();
                loginVO.setUserName(authUser.getUsername());
                loginVO.setAccessToken(JwtUtil.createAccessToken(loginDTO));
                loginVO.setRefreshToken(JwtUtil.createRefreshToken(loginDTO));
                return loginVO;
            }
            throw new LoginAuthException("用户不存在");
        }
        throw new LoginAuthException("认证失败");
    }
}
