package com.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LeeCodeStudy2 {


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
