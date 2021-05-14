package com.demo;

import com.demo.domain.ListNode;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 力扣算法
 */
public class LeeCodeStudy {

    /**
     * 检测概数是不是2n次方
     */
    @org.junit.Test
    public void test(){
        int a=10;
        while (a%2==0){
            a=a/2;
            System.out.println(a);
            if(a==1){
                System.out.println("是："+a);
            }
            System.out.println(a);
        }

    }

    /**
     * 力扣算法第一题 求两数之和
     */
    @org.junit.Test
    public void twoSum(){
        int [] nums={1,2,6,5};
        int target=6;
        //暴力
        for(int i=0;i<nums.length;i++){
           for(int j=i+1;j<nums.length;j++){
               if(target-nums[i]==nums[j]){
                   System.out.println(i+","+j);
               }
           }
        }
        System.out.println("---------------");
        // 方法二
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                System.out.println(map.get(nums[i])+","+i);
            }
            map.put(target - nums[i], i);
        }

    }
    /**
     * 链表翻转
     * 输入1,2,3
     * 输出3,2,1
     */
    @Test
    public void linkFanzhuang(){
        ListNode node5=new ListNode(5,null);
        ListNode node4=new ListNode(4,node5);
        ListNode node3=new ListNode(3,node4);
        ListNode node2=new ListNode(2,node3);
        ListNode node=new ListNode(1,node2);
        //迭代
        ListNode listNode = ListNode.getListNode(node);
        System.out.println(listNode);
        //递归
        ListNode dIgui = ListNode.getDIgui(node);
        System.out.println("递归："+dIgui);
    }

    /**
     * 统计素数的个数 0,1不算
     */
    @Test
    public void sushu(){
        int a=0;

        for(int i=2;i<=50;i++){
          a+= findSuShu(i)?1:0;
        }

        System.out.println(a);
    }

    public Boolean findSuShu(int x){
        for(int j=2;j<x;j++){
            if(x%j==0){
                System.out.println(x+"不是素数");
                return false;
            }
        }
        System.out.println(x+"是素数");
        return true;
    }

    /**
     * 埃氏筛选法查找素数
     */
    public static int eratosthenes(int n){
        boolean [] isPrime=new boolean[n];//false代表素数
        int count=0;
        for(int i=2;i<n;i++){
            if(!isPrime[i]){
                count++;
                for(int j=2* i;j<n;j+=i){
                    isPrime[j]=true;
                }
            }

        }
        return count;
    }

    /**
     * 输出start到end之间的素数
     *
     * @param start 起始数字
     * @param end   结束数字
     */
    public static void calPrimeNum(int start, int end) {
        // 用作标识能被除1和本身外,多少数字整除
        int state = 0;
        // 用于循环判断start到end是否为质数
        for (int i = start; i <= end; i++) {
            // 用于从i到0依次求余，判断是否除1和本身外有其他数字能整除
            for (int j = i; j > 0; j--) {
                // 判断是否除1和本身外有其他数字能整除
                if (i % j == 0 && j > 1 && j < i) {
                    // 若存在，将状态+1
                    state++;
                }
            }
            if (state > 0) {
                // 清空状态，用于下一个数的判断。注：不清空会影响后面程序的运行结果
                state = 0;
            } else {
                // 输出是素数的数
                System.out.println(i + "是素数");
            }
        }
    }



}
