package roy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：楼兰
 * @date ：Created in 2021/4/8
 * @description:
 **/

@Configuration
public class DemoApplicationConfig {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 添加Security的用户
     * @return
     */
    @Bean
    public UserDetailsService myUserDetailsService(){
//        把用户存储在内存中
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        构造用户的信息
        String[][] usersGroupAndRoles= {
                {"jack","password","ROLE_ACTIVITI_USER","GROUP_activitiTeam"},
                {"rose","password","ROLE_ACTIVITI_USER","GROUP_activitiTeam"},
                {"tom","password","ROLE_ACTIVITI_USER","GROUP_activitiTeam"},
                {"jerry","password","ROLE_ACTIVITI_USER","GROUP_activitiTeam"},
                {"other","password","ROLE_ACTIVITI_USER","GROUP_otherTeam"},
                {"system","password","ROLE_ACTIVITI_USER"},
                {"admin","password","ROLE_ACTIVITI_ADMIN"}
        };

        for (String[] users : usersGroupAndRoles) {
//            用户的角色和组
            List<String> authStrList = Arrays.asList(Arrays.copyOfRange(users, 2, users.length));

            log.info("> Registering new user: {} with the following Authorities[{}]",users[0],authStrList);

            inMemoryUserDetailsManager.createUser(new User(users[0],
                    passwordEncoder().encode(users[1]),
                    authStrList.stream().map(str -> new SimpleGrantedAuthority(str)).collect(Collectors.toList())));
        }
        return inMemoryUserDetailsManager;

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
