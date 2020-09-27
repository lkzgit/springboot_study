package com.demo.model.controller;


import com.demo.model.constant.Constant;
import com.demo.model.model.LoginBean;

import com.demo.model.model.User;
import com.demo.model.result.HttpResult;

import com.demo.model.service.TokenService;
import com.demo.model.service.UserService;


import com.demo.model.untils.RedisCache;
import com.demo.model.untils.VerifyCodeUtils;
import com.demo.model.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Resource
    private RedisCache redisCache;


    @Resource
    BCryptPasswordEncoder passwordEncoder;


    @GetMapping("/test")
    public String test(){

        return "login";
    }

    /**
     * 生成验证码
     * @return
     */
    @GetMapping("/creatKapt")
    public HttpResult creatKapt(HttpServletResponse response, HttpServletRequest request) throws IOException {
        /*禁止缓存*/
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        /*获取验证码*/
        String code = VerifyCodeUtils.generateVerifyCode(4);
        /*验证码已key，value的形式缓存到redis 存放时间一分钟*/
        log.info("验证码============>" + code);
        String uuid = UUID.randomUUID().toString();
      redisCache.setCacheObject(Constant.CAPTCHA_CODE_KEY+uuid,code,1800,TimeUnit.SECONDS);
        request.getSession().setAttribute("code",code);
        ServletOutputStream outputStream = response.getOutputStream();
        //ImageIO.write(bufferedImage,"jpg",outputStream);
        VerifyCodeUtils.outputImage(110,40,outputStream,code);
        outputStream.flush();
        outputStream.close();
        return null;
    }

    @PostMapping("/tologin")
    public HttpResult tologin(@RequestBody LoginBean user){
        String username = user.getUsername();
        String password = user.getPassword();
        Authentication authentication = null;
        try{
            User byUsername = userService.findByUsername(username);
            if(byUsername!=null){
                if(passwordEncoder.matches(password,byUsername.getPassword())){ // 用户验证
                    // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
                    authentication = authenticationManager
                            .authenticate(new UsernamePasswordAuthenticationToken(username, password));
                  //  SecurityContextHolder.getContext().setAuthentication(authentication);
                    LoginUser loginUser = (LoginUser)  authentication.getPrincipal();
                    String token= tokenService.createToken(loginUser);
                    return HttpResult.ok(token);
                }else{
                    return HttpResult.ok("密码不正确");
                }
            }else{
                return HttpResult.ok("没有此用户");
            }
        }catch (Exception e){
            log.info(e.getMessage());
            return HttpResult.error(999,"失败");
        }

    }

}
