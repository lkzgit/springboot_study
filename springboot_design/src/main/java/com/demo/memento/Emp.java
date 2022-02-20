package com.demo.memento;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Emp.java
 * @Description TODO 源发器类
 * @createTime 2022年02月20日 18:17:00
 */
public class Emp {

    private String name;
    private  int age;

    //进行备忘操作 并返回备忘录对象
    public EmpMement mement(){
        return new EmpMement(this);
    }
    // 进行数据恢复 指定恢复对象
    public void recovery(EmpMement emp){
        this.name=emp.getName();
        this.age= emp.getAge();

    }



    public Emp() {
    }

    public Emp(String name, int age) {
        this.name = name;
        this.age = age;
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
}
