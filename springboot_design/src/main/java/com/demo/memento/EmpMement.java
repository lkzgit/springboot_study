package com.demo.memento;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName EmpMement.java
 * @Description TODO 备忘录类
 * @createTime 2022年02月20日 18:18:00
 */
public class EmpMement {
    private String name;

    private int age;

    public EmpMement(Emp emp){
        this.age=emp.getAge();
        this.name=emp.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public EmpMement(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
