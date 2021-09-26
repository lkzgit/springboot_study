package com.demo.security.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**

 * @author: lkz
 * @date:Created in 2021/9/26
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * Spring的 @PreAuthorize/@PostAuthorize 注解更适合方法级的安全,也支持Spring 表达式语言，提供了基于表达式的访问控制
     * 拥有normal或者admin角色的用户都可以方法helloUser()方法
     * hasPermission必须自己去实现才能使用，我已经测试过了
     *
     * @return
     */
    @GetMapping(value = "/list")
    @PreAuthorize("hasPermission(null ,'system_manage')")
    @ApiOperation(value = "用户列表")
    public String userList() {
        return "home";
    }

    /**
     * 拥有normal或者admin角色的用户都可以方法helloUser()方法。另外需要注意的是这里匹配的字符串需要添加前缀“ROLE_“
     * 如果我们要求，只有同时拥有admin & noremal的用户才能方法helloUser()方法，这时候@Secured就无能为力了
     *
     * @return
     */
    @GetMapping("/update")
    @Secured({"ROLE_chairman", "ROLE_general_manager"})
    public String update() {
        return "update";
    }

    /**
     * 此时如果我们要求用户必须同时拥有normal和admin的话，那么可以这么编码
     *
     * @return
     */
    @GetMapping("/delete")
    @PreAuthorize("hasRole('root') AND hasRole('chairman')")
    public String delete() {
        return "delete";
    }

    /**
     * @return
     * @PostAuthorize 注解使用并不多，在方法执行后再进行权限验证，适合验证带有返回值的权限，
     * Spring EL 提供 返回对象能够在表达式语言中获取返回的对象returnObject。
     * 当@EnableGlobalMethodSecurity(prePostEnabled=true)的时候，@PostAuthorize可以使用
     */
    @GetMapping("/export")
    @PostAuthorize(" returnObject!=null &&  returnObject.username == authentication.name")
    public Object export() {
        Object pricipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return pricipal;
    }
}
