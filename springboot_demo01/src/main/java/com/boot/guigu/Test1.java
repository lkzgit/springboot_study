package com.boot.guigu;


import org.junit.Test;

public class Test1 {



    //楼梯有几种方法
    @Test
    public  void test1(){
       // System.out.println(louti(5));
        System.out.println(jumpByFor(5));
    }
    // 楼梯递归方法
    public int louti(int num){
        if(num<1){
           throw new IllegalArgumentException(num+"参数不能小于1");
        }
        if(num ==1 || num ==2){
            return num;
        }
        return louti(num-2)+(num-1);
    }
    //for循环方式
    public long jumpByFor(int n){
        int first = 1;
        int second = 2;
        int third = 0;
        for(int i = 3; i <= n; i++) {
            third = first + second;
            first = second;
            second = third;
        }
        return third;
    }

    // 鸡兔同笼
    @Test
    public void test2(){
        jitu(35,94);
    }
    // 鸡有两只叫 兔子四只脚
    public  void jitu(int head, int foot){
        /**
         * 鸡头+秃头=head
         * 鸡+兔脚=foot
         *
         */
//        int ji;int tu;
//        if(foot%2==0){
//            ji = head * 2 - foot/2;
//            tu = head - ji;
//            if(ji>0&&tu>0) {
//                System.out.println("鸡的数量为：" +ji +"兔子的数量为：" + tu);
//            }else {
//                System.out.println("不存在");
//            }
//
//        }
        int ji;
        for(int i=0;i<=head;i++){
            ji=head-i;
            if(2*i+ji*4==foot){
                System.out.println(i+"--"+ji);
            }
        }

    }
}
