package com.demo.security.hander;


import com.demo.security.enumeration.ErrorCodeEnum;
import com.demo.security.util.ResponseUtil;
import com.demo.security.vo.BaseVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 用户未登录处理类
 * @Author: lkz
 * @Date: 2021/9/26
 **/
@Component
public class UserNotLoginHandler implements AuthenticationEntryPoint {
    /**
     * 用户未登录返回结果
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        ResponseUtil.ResponseMeg(response, new BaseVO(false, ErrorCodeEnum.E0705.getKey(), ErrorCodeEnum.E0705.getValue()));
    }
}