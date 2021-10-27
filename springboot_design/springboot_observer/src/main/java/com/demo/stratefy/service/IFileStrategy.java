package com.demo.stratefy.service;

/**
 * @author lkz
 * @date 2021/10/27 9:14
 * @description 策略模式
 * 一个接口或者抽象类，里面两个方法（一个方法匹配类型，一个可替换的逻辑实现方法）
 * 不同策略的差异化实现(就是说，不同策略的实现类)
 * 使用策略模式
 */
public interface IFileStrategy {

    //属于哪种文件解析类型
    FileTypeResolveEnum gainFileType();

    //封装的公用算法（具体的解析方法）
    void resolve(Object objectparam);
}
