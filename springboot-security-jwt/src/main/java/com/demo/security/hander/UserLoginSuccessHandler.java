package com.demo.security.hander;



import com.demo.security.domain.User;
import com.demo.security.util.JwtUtil;
import com.demo.security.util.ResponseUtil;
import com.demo.security.vo.UserTokenVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 登录成功处理类
 * @Author: lkz
 **/
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 登录成功返回结果
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 获取登陆的用户信息
        User user = (User) authentication.getPrincipal();
        //生成令牌
        String token = JwtUtil.createToken(String.valueOf(user.getUserId()), user.getAccount(), user.getGrantedAuthorities());
        //写入到vo
        UserTokenVO userTokenVO = new UserTokenVO(token);
        //响应给前端
        ResponseUtil.ResponseMeg(response, userTokenVO);
    }
}
