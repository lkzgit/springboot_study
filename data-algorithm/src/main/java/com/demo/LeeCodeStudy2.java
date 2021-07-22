package com.demo;

import lombok.Data;
import org.junit.Test;

import java.util.*;

public class LeeCodeStudy2 {

    @Test
    public void test(){
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program";
        String s4 = "ming";
        String s6 = s3 + s4;
        String s5 = "Program" + "ming";
        System.out.println(s1 == s2);
        System.out.println(s1 == s5);
        System.out.println(s1 == s6);
        System.out.println(s1 == s6);
        System.out.println(s5 == s6);
        System.out.println(s2.intern() == s6.intern());
    }


    /**
     * 数组中最大数对和的最小值
     * 一个数对 (a,b) 的 数对和 等于 a + b 。最大数对和 是一个数对数组中最大的 数对和 。
     *
     * 比方说，如果我们有数对 (1,5) ，(2,3) 和 (4,4)，最大数对和 为 max(1+5, 2+3, 4+4) = max(6, 5, 8) = 8 。
     * 给你一个长度为 偶数 n 的数组 nums ，请你将 nums 中的元素分成 n / 2 个数对，使得：
     * nums 中每个元素 恰好 在 一个 数对中，且
     * 最大数对和 的值 最小 。
     * 请你在最优数对划分的方案下，返回最小的 最大数对和 。
     */
    @Test
    public void maxSumArr(){
        int[] nums={1,5,2,3};
        Arrays.sort(nums);
        int i = 0;
        int j = nums.length - 1;
        int res = 0;
        while (i < j) {
            res = Math.max(nums[i++] + nums[j--], res);
        }
        System.out.println(res);
    }



    /**
     * 预测玩家
     */
    @Test
    public void yucePlayTest(){
        int[] arr={1,2,5,6};
        this.yuceDihui(arr, 0, arr.length - 1, 1);
    }

    public int yuceDihui(int[] nums,int start,int end,int turn){
        if (start == end) {
            return nums[start] * turn;
        }
        int scoreStart = nums[start] * turn + yuceDihui(nums, start + 1, end, -turn);
        int scoreEnd = nums[end] * turn + yuceDihui(nums, start, end - 1, -turn);
        return Math.max(scoreStart * turn, scoreEnd * turn) * turn;

    }


    /**
     * 省级数量
     */
    @Test
    public void shengjiTest(){
        int[][] arr={{1,1,0},{1,1,0},{0,0,1}};
        int[][] arrs={{0,0,1},{0,1,0},{0,0,1}};
        System.out.println(this.getProvince(arr));
    }
    //广度优先
    public int getProvinces(int[][] arr){
        int provinces = arr.length;
        boolean[] visited = new boolean[provinces];
        int circles = 0;
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < provinces; i++) {
            if (!visited[i]) {
                queue.offer(i);
                while (!queue.isEmpty()) {
                    int j = queue.poll();
                    visited[j] = true;
                    for (int k = 0; k < provinces; k++) {
                        if (arr[j][k] == 1 && !visited[k]) {
                            queue.offer(k);
                        }
                    }
                }
                circles++;
            }
        }
        return circles;

    }

    //深度优先
    public int getProvince(int[][] arr){
       int city= arr.length;
       boolean[] visited=new boolean[city];
       int provinces=0;
       for(int i=0;i<city;i++){
           if(!visited[i]){
               dfs(arr, visited, city, i);
               provinces++;
           }
       }
       return provinces;
    }

    public void dfs(int[][] isConnected, boolean[] visited, int provinces, int i) {
        for (int j = 0; j < provinces; j++) {
            if (isConnected[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfs(isConnected, visited, provinces, j);
            }
        }
    }


    /**
     * 二叉树遍历
     * 前序遍历：根左右
     * 中序遍历 ：左根右
     * 后续遍历：左右根
     * 层序遍历：从上往下 从左往右
     * 递归遍历： 递归
     * 迭代遍历：使用迭代方法实现递归函数
     * Morris遍历
     */
    @Test
    public void ercahshuTest(){

        TreeTwoNode node7 = new TreeTwoNode(7,null,null);
        TreeTwoNode node6 = new TreeTwoNode(6,null,null);
        TreeTwoNode node5 = new TreeTwoNode(5,node6,node7);
        TreeTwoNode node4 = new TreeTwoNode(4,null,null);
        TreeTwoNode node3 = new TreeTwoNode(3,null,null);
        TreeTwoNode node2 = new TreeTwoNode(2,node4,node5);
        TreeTwoNode node1 = new TreeTwoNode(1,node2,node3);
        List<Integer> res=new ArrayList<>();
        System.out.println("前序递归写法："+digui(node1,res));

    }
    //前序递归
    public List<Integer> digui(TreeTwoNode node, List<Integer> res){

        if(node==null){
            return res;
        }
        digui(node.left,res);
        digui(node.right,res);
        res.add(node.val);
        return res;
    }

}

