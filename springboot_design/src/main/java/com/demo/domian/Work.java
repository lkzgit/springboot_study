package com.demo.domian;

//请求实体类
public class Work {

   private Integer id;

   private String name;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Work(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Work() {
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
