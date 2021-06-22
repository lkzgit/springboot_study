//package com.jwt.demo.until;
//
//import cn.hutool.core.util.StrUtil;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jianfan.backend.common.exception.CustomException;
//import com.jianfan.backend.common.result.BaseResult;
//import com.jianfan.backend.common.utils.JWTUtils;
//import com.jianfan.backend.framework.jwt.annotation.PassToken;
//import com.jianfan.backend.framework.jwt.annotation.UserLoginToken;
//import com.jianfan.backend.project.doctor.domain.Doctor;
//import com.jianfan.backend.project.doctor.domain.DoctorExample;
//import com.jianfan.backend.project.doctor.mapper.DoctorMapper;
//import com.jianfan.backend.project.sys.domain.SysUserRole;
//import com.jianfan.backend.project.sys.service.BackRoleService;
//import com.jianfan.backend.project.sys.service.BackUserRoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//import java.lang.reflect.Method;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author sunda
// * @version 1.0
// * @date 2021-04-21 15:18
// * @description: TODO
// */
//public class AuthenticationInterceptor implements HandlerInterceptor {
//
//
//    @Autowired
//    private DoctorMapper doctorMapper;
//
//    @Autowired
//    private BackUserRoleService userRoleService;
//    @Autowired
//    private BackRoleService roleService;
//
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object object) throws Exception {
//        String token ="";
//        token = this.getToken(httpServletRequest);
//
//        System.out.println("Token为："+token);
//        // 验证 token
//        Map<String,String> map=new HashMap<>();
//
//
//        // 如果不是映射到方法直接通过
//        if(!(object instanceof HandlerMethod)){
//            return true;
//        }
//
//        HandlerMethod handlerMethod=(HandlerMethod)object;
//        PassToken handlePass = handlerMethod.getBean().getClass().getDeclaredAnnotation(PassToken.class);
//        System.out.println("拿到的handlePass:"+handlePass);
//        System.out.println("handle拿到的类是:"+handlerMethod.getBean().getClass().getName());
//        System.out.println("handle拿到的类是否是swagger的控制器类:"+handlerMethod.getBean().getClass().getName().equals("springfox.documentation.swagger.web.ApiResourceController"));
//
//        //判断如果请求的类是swagger的控制器，直接通行。
//        if(handlerMethod.getBean().getClass().getName().equals("springfox.documentation.swagger.web.ApiResourceController")){
//            return  true;
//        }
//
//        Method method=handlerMethod.getMethod();
//        //检查是否有passtoken注释，有则跳过认证
//        if (method.isAnnotationPresent(PassToken.class)) {
//            PassToken passToken = method.getAnnotation(PassToken.class);
//            if (passToken.required()) {
//                return true;
//            }
//        }
//        //检查有没有需要用户权限的注解
//        if (method.isAnnotationPresent(UserLoginToken.class)) {
//
//            if(StrUtil.isEmptyOrUndefined(token)){
//                token= this.getCookies(httpServletRequest);
//            }
//            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
//            if (userLoginToken.required()) {
//
//                //权限验证
//                System.out.println("开始验证Token权限。。。。");
//                HttpServletRequest request= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//                String url=this.getURL(request);
//                String askMethod=this.getMethod(request);
////                    String cookies = this.getCookies(request);
////                    System.out.println("Token为："+cookies);
//                System.out.println("URL为："+url);
//                System.out.println("Method为："+askMethod);
//                //判断token是否为空
//                if(StrUtil.isEmptyOrUndefined(token)){
//                    throw new CustomException(301, "无token,请重新登录");
//                }else{
//                    Map mapToken = JWTUtils.getUserNameFromToken(token);
//                    String phone = (String)mapToken.get("phone");
//                    //数据库中查询用户角色的权限
//                    DoctorExample example=new DoctorExample();
//                    DoctorExample.Criteria criteria = example.createCriteria();
//                    criteria.andPhoneEqualTo(phone);
//                    List<Doctor> doctors = doctorMapper.selectByExample(example);
//                    if(doctors.size()>0&&doctors!=null){
//                        BaseResult userRoleList = userRoleService.getUserRoleList(doctors.get(0).getId());
//                        if(userRoleList.getData()==null){
//                            System.out.println("用户角色");
//                            throw  new CustomException(304,"该用户没有未分配角色权限");
//                        }else{
//                            List<SysUserRole> data = (List<SysUserRole>)userRoleList.getData();
//                            List<Long> idList=new ArrayList<>();
//                            data.forEach(e->{
//                                idList.add(e.getRoleId());
//                            });
//                            //根据角色获取角色请求权限
//                            BaseResult roleMenuListMany = roleService.getRoleASKUrlList(idList);
//                            if(roleMenuListMany.getData()==null){
//                                System.out.println("请求角色权限");
//                                throw new CustomException(304,"该用户没有角色权限");
//                            }else{
//                                Set<String> data1 = (Set<String>)roleMenuListMany.getData();
//                                if(!data1.contains(url)){
//                                    System.out.println("该用户分配的权限不包含该权限");
//                                    throw new CustomException(304,"该用户拥有权限不包含此操作");
//                                }
//                            }
//                        }
//                    }else{
//                        throw new CustomException(303,"用户不存在");
//                    }
//                }
//            }
//
////                try{
////                  //  JWTUtils.(token);
////                    map.put("msg","验证成功");
////                    return true;
////                }catch(SignatureVerificationException e){
////                    e.getStackTrace();
////                    map.put("msg","无效签名");
////                }catch (TokenExpiredException e){
////                    e.getStackTrace();
////                    map.put("msg","token过期");
////                }catch (AlgorithmMismatchException e){
////                    e.getStackTrace();
////                    map.put("msg","token算法不一致");
////                }
////                catch (Exception e){
////                    e.getStackTrace();
////                    map.put("msg","token无效");
////                }
////                //将map 转为json jackon
////                String json = new ObjectMapper().writeValueAsString(map);
////                response.setContentType("application/json:charset=UTF-8");
////                response.getWriter().println(json);
////                return false;
//
//        }
//
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//
//
//    public String getToken(HttpServletRequest request){
//        return request.getHeader("token");
//    }
//    public String getURL(HttpServletRequest request){
//        String requestURI = request.getRequestURI();
//        //  String s = StrUtil.removePreAndLowerFirst(requestURI, 1);
//        return requestURI;
//    }
//    public String getMethod(HttpServletRequest request){
//        return request.getMethod();
//    }
//
//    public String getCookies(HttpServletRequest request){
//        String token="";
//        Cookie[] cookies = request.getCookies();
//        for(int i=0;i<cookies.length;i++){
//            if(cookies[i].getName().equals("zvfptoken")){
//                token=  cookies[i].getValue();
//                break;
//            }
//        }
////        String cookiesName = cookies[0].getName();
////        if(cookiesName.equals("zvfptoken")){
////            token= cookies[0].getValue();
////        }
//        return token;
//    }
