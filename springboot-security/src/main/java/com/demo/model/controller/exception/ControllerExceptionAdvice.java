package com.demo.model.controller.exception;


import com.demo.model.exception.CustomException;
import com.demo.model.result.HttpResult;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义全局异常
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {

    //只有出现AccessDeniedException异常才调转403.jsp页面
    @ExceptionHandler(AccessDeniedException.class)
    public String exceptionAdvice(){
        return "没有权限";
    }
    @ExceptionHandler(SignatureException.class)
    public String exceptionAdvice02(){
        return "token解析失败";
    }
    @ExceptionHandler(MalformedJwtException.class)
    public String exceptionAdvice03(){
        return "token算法不一致";
    }
//    @ExceptionHandler(UnsupportedEncodingException.class)
//    public String exceptionAdvice04(){
//        return "系统加密出现错误";
//    }
//    @ExceptionHandler( TokenExpiredException.class)
//    public String exceptionAdvice05(){
//        return "token过期";
//    }
//    @ExceptionHandler(AlgorithmMismatchException.class)
//    public String exceptionAdvice06(){
//        return "token算法不一致";
//    }

    @ExceptionHandler(CustomException.class)
    public HttpResult customException(CustomException e, HttpServletRequest request){

        Map map=new HashMap();
        map.put("msg",e.getMessage());
        map.put("cood",e.getCode());
        map.put("url",request.getRequestURL());
        return  HttpResult.ok(map);
    }


}
