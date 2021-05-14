package com.demo.domain;

import java.util.List;

public class ListNode {

    private int val;

    private ListNode next;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /**
     * 迭代
     * @param head
     * @return
     */
    public static ListNode getListNode(ListNode head){
        ListNode val=null,next = null;
        ListNode curl=head;
        while (curl!=null){
            val=curl.next;//将下一个节点指针保存到next变量
            curl.next=val;//将下一个节点的指针指向val;
            val=curl;//处理下一个节点 将curl赋值给val
            curl=next;//将下一个节点赋值curl,处理节点
        }
        return val;
    }

    /**
     * 递归方法
     * @return
     */
    public static ListNode getDIgui(ListNode head){
        //判断是否有下一个节点
        if(head==null || head.next==null){
            return head;
        }
        ListNode dIgui = getDIgui(head.next);
        head.next.next=head;
        head.next=null;
        return dIgui;
    }
}
