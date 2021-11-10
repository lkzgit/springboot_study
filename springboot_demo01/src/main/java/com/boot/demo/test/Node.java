package com.boot.demo.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lkz
 * @date 2021/11/1 19:34
 * @description
 */
public class Node {

    private Node next;
    private Object data;

    public Node(Object object) {
        this.data = object;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static void main(String[] args) {
       
        Node head=new Node("张三");
         head.next=new Node("张3");;
         head.next.next=new Node("张4");
        System.out.println(head.data);
        System.out.println(head.next.data);
        System.out.println(head.next.next.data);
    }

}
