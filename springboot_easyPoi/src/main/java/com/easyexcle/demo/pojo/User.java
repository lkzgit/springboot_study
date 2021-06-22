package com.easyexcle.demo.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @ExcelProperty({"用户信息表", "编号"})
    private Integer user;
    @ExcelProperty({"用户信息表", "姓名"})
    private String name;
    @ExcelProperty({"用户信息表", "年龄"})
    private Integer age;

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "User{" +
                "user=" + user +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
