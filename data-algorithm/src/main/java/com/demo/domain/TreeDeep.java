package com.demo.domain;

import javax.swing.tree.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树 从跟节点到叶子节点最短路径的节点数量
 *
 */
public class TreeDeep {

    public static void main(String[] args) {
        TreeNode node7=new TreeNode(7,null,null);
        TreeNode node6=new TreeNode(6,node7,null);
        TreeNode node5=new TreeNode(5,null,null);
        TreeNode node4=new TreeNode(4,null,null);
        TreeNode node3=new TreeNode(3,node6,null);
        TreeNode node2=new TreeNode(2,node4,node5);
        TreeNode node1=new TreeNode(1,node2,node3);
        System.out.println(minDepts(node1));
    }

    /**
     * 二叉树最小深度 深度优先
     * 给定一个二叉树 找出其最小深度 从跟节点到最近的叶子节点的最短路经的数量
     *
     */
    public static int minDept(TreeNode node){
            if(node==null){
                return 0;
            }
            //如果两边都为null 说明是最小子节点
            if(node.left==null&&node.right==null){
                return 1;
            }
            int min=Integer.MAX_VALUE;
            if(node.left!=null){
                min=Math.min(minDept(node.left),min);
            }
            if(node.right!=null){
                min=Math.min(minDept(node.right),min);
            }
        return min+1;
    }

    /**
     * 广度优先
     */
    public static int minDepts(TreeNode node){
        if(node==null){
            return 0;
        }
        Queue<TreeNode>   queue=new LinkedList<TreeNode>();
        node.deep=1;
        queue.offer(node);
        while (!queue.isEmpty()){
            TreeNode treeNode = queue.poll();
            //叶子节点
            if(treeNode.left==null&&treeNode.right==null){
                return treeNode.deep;
            }
            if(treeNode.left!=null){//如果不是叶子节点
                treeNode.left.deep= treeNode.deep+1;
                queue.offer(treeNode.left);
            }
            if(treeNode.right!=null){
                treeNode.right.deep=treeNode.deep+1;
                queue.offer(treeNode.right);
            }
        }
        return 0;
    }

    static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        int deep;//广度使用

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public int getDeep() {
            return deep;
        }

        public void setDeep(int deep) {
            this.deep = deep;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
