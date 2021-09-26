package com.demo.security.hander;


import com.demo.security.enumeration.ErrorCodeEnum;
import com.demo.security.util.ResponseUtil;
import com.demo.security.vo.BaseVO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 暂无权限处理类
 * @Author: lkz
 * @Date: 2021/9/26
 **/
@Component
public class UserNotPermissionHandler implements AccessDeniedHandler {
    /**
     * 暂无权限返回结果
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        ResponseUtil.ResponseMeg(response, new BaseVO(false, ErrorCodeEnum.E0706.getKey(), ErrorCodeEnum.E0706.getValue()));
    }
}