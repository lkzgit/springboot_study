package com.demo;

public class TreeTwoNode {

    public int val;
    public TreeTwoNode left;

    public TreeTwoNode right;

    public int deep;

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public TreeTwoNode(int val, TreeTwoNode left, TreeTwoNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public TreeTwoNode getLeft() {
        return left;
    }

    public void setLeft(TreeTwoNode left) {
        this.left = left;
    }

    public TreeTwoNode getRight() {
        return right;
    }

    public void setRight(TreeTwoNode right) {
        this.right = right;
    }
}
