package com.demo;

import com.demo.mongn.MongoApplication;
import com.demo.mongn.entity.User;
import com.demo.mongn.repository.UserRepository;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoApplication.class)
public class DemomogoApplicationTests {

    //基于MongoTemplate
    @Autowired
    private MongoTemplate mongoTemplate;
    //基于MongoRepository开发CRUD
    @Autowired
    private UserRepository userRepository;

    //添加
    @Test
    public void createUserRepository() {
        User user = new User();
        user.setAge(21);
        user.setName("张三");
        user.setEmail("3332200@qq.com");
        User user1 = userRepository.save(user);
    }

    //查询所有
    @Test
    public void findUserRepository() {
        List<User> userList = userRepository.findAll();
        System.out.println(userList);
    }

    //id查询
    @Test
    public void getByIdRepository() {
        User user = userRepository.findById("5ffbfe8197f24a07007bd6ce").get();
        System.out.println(user);
    }

    //条件查询
    @Test
    public void findUserListRepository() {
        User user = new User();
        user.setName("张三");
        user.setAge(20);
        Example<User> userExample = Example.of(user);
        List<User> userList = userRepository.findAll(userExample);
        System.out.println(userList);
    }

    //模糊查询
    @Test
    public void findUsersLikeNameRepository() {
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        User user = new User();
        user.setName("三");
        Example<User> userExample = Example.of(user, matcher);
        List<User> userList = userRepository.findAll(userExample);
        System.out.println(userList);
    }

    //分页查询
    @Test
    public void findUsersPageRepository() {
        Sort sort = Sort.by(Sort.Direction.DESC, "age");
//0为第一页
        Pageable pageable = PageRequest.of(0, 10, sort);
//创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        User user = new User();
        user.setName("三");
        Example<User> userExample = Example.of(user, matcher);
//创建实例
        Example<User> example = Example.of(user, matcher);
        Page<User> pages = userRepository.findAll(example, pageable);
        System.out.println(pages);
    }

    //修改
    @Test
    public void updateUserRepository() {
        User user = userRepository.findById("5ffbfe8197f24a07007bd6ce").get();
        user.setName("张三_1");
        user.setAge(25);
        user.setEmail("883220990@qq.com");
        User save = userRepository.save(user);
        System.out.println(save);
    }

    //删除
    @Test
    public void deleteRepository() {
        userRepository.deleteById("5ffbfe8197f24a07007bd6ce");
    }




    ///////////////////////////////////
    //添加
    @Test
    public void createUser() {
        User user = new User();
        user.setAge(21);
        user.setName("admin2");
        user.setEmail("4932200@qq.com");
        User user1 = mongoTemplate.insert(user);
        System.out.println(user1);
    }

    //查询所有
    @Test
    public void findUser() {
        List<User> userList = mongoTemplate.findAll(User.class);
        System.out.println(userList);
    }

    //根据id查询
    @Test
    public void getById() {
        User user =
                mongoTemplate.findById("606985ce4a85b533440a6540", User.class);
        System.out.println(user);
    }

    //条件查询
    @Test
    public void findUserList() {
        Query query = new Query(Criteria
                .where("name").is("test")
                .and("age").is(20));
        List<User> userList = mongoTemplate.find(query, User.class);
        System.out.println(userList);
    }

    //模糊查询
    @Test
    public void findUsersLikeName() {
        String name = "est";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<User> userList = mongoTemplate.find(query, User.class);
        System.out.println(userList);
    }

    //分页查询
    @Test
    public void findUsersPage() {
        String name = "est";
        int pageNo = 1;
        int pageSize = 10;

        Query query = new Query();
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("name").regex(pattern));
        int totalCount = (int) mongoTemplate.count(query, User.class);
        List<User> userList = mongoTemplate.find(query.skip((pageNo - 1) * pageSize).limit(pageSize), User.class);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("list", userList);
        pageMap.put("totalCount",totalCount);
        System.out.println(pageMap);
    }

    //修改
    @Test
    public void updateUser() {
        User user = mongoTemplate.findById("606985ce4a85b533440a6540", User.class);
        user.setName("test_1");
        user.setAge(25);
        user.setEmail("493220990@qq.com");
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("age", user.getAge());
        update.set("email", user.getEmail());
        UpdateResult result = mongoTemplate.upsert(query, update, User.class);
        long count = result.getModifiedCount();
        System.out.println(count);
    }

    //删除操作
    @Test
    public void delete() {
        Query query =
                new Query(Criteria.where("_id").is("606985ce4a85b533440a6540"));
        DeleteResult result = mongoTemplate.remove(query, User.class);
        long count = result.getDeletedCount();
        System.out.println(count);
    }


}
