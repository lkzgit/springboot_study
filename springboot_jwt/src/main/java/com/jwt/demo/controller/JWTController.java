package com.jwt.demo.controller;


import com.jwt.demo.until.JWTUntil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class JWTController {

    @GetMapping("test")
    public String test(){
        return "hellow JWT";
    }

    /**
     * 此处模拟数据库
     * @param name
     * @return
     */
    @GetMapping("/login")
    public Map login(@RequestParam(value = "name",required = false)String name){
        Map<String,String> map=new HashMap<>();
        if(StringUtils.isEmpty(name)){
            map.put("msg","没有此用户");
            return map;
        }else{
           Map<String,String> payload=new HashMap<>();
           payload.put("name",name);
            String token = JWTUntil.getToken(payload);

            map.put("name",name);
            map.put("token",token);

            return map;
        }

    }
    @PostMapping("/testToken")
    public Map testToken(@RequestParam("token")String token){
        Map<String,String> map=new HashMap<>();

            JWTUntil.verify(token);
            map.put("msg","验证成功");
            return map;

    }
}
