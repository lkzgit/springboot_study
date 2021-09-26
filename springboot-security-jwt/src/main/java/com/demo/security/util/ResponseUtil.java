package com.demo.security.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**

 * 响应处理工具类

 * @author: lkz
 */
public class ResponseUtil {
    public static <T> void ResponseMeg(HttpServletResponse response, T t) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(JSON.toJSONString(t));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
