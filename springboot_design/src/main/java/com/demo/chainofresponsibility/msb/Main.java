package com.demo.chainofresponsibility.msb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Main.java
 * @Description TODO
 * @createTime 2022年02月23日 23:19:00
 */
public class Main {

    public static void main(String[] args) {
        Msg msg=new Msg();
        msg.setMsg("大家好,<ok!!1>,欢迎来到代码的世界！天天110");

        new HtmlFilter().doFilter(msg);
        new SentiFilter().doFilter(msg);

        System.out.println(msg);

        System.out.println("-----filterChain----");
        FilterChain chain=new FilterChain();
       // chain.addFilter(new HtmlFilter()).addFilter(new SentiFilter());//链式编程
        chain.addFilter(new HtmlFilter());
        chain.addFilter(new SentiFilter());
        chain.addFilter(new UrlFilter());
        chain.doFilter(msg);
        System.out.println("chain----:"+msg);

    }
}
class Msg{
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "msg='" + msg + '\'' +
                '}';
    }
}


interface Filter{
    boolean doFilter(Msg msg);
}

class HtmlFilter implements Filter{

    @Override
    public boolean doFilter(Msg msg) {
        String str=msg.getMsg();
         str= str.replace("<","[");
         str= str.replace(">","]");
         msg.setMsg(str);
        return true;
    }
}

class SentiFilter implements Filter{

    @Override
    public boolean doFilter(Msg msg) {
        String str=msg.getMsg();
        str= str.replace("110","955");
        msg.setMsg(str);
        return false; //模拟返回false不继续向下执行
    }
}

class UrlFilter implements Filter{

    @Override
    public boolean doFilter(Msg msg) {
        String str=msg.getMsg();
        str= str.replace("!","**");
        msg.setMsg(str);
        return true;
    }
}

class FilterChain implements Filter{ //实现Filter接口可以 合并链条 f1.add(f2)

    private List<Filter> filters=new ArrayList<>();


    public void addFilter(Filter filter){
        this.filters.add(filter);
    }
    //链式编程
//    public FilterChain addFilter(Filter filter){
//        this.filters.add(filter);
//        return this;
//    }

    public boolean doFilter(Msg msg){
        for(Filter f:filters){
            if(!f.doFilter(msg)){
                return false;
            }
        }
        return true;
    }
}
