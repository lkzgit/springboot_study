package com.demo.composite;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Client.java
 * @Description TODO 组合模式 sxt
 * @createTime 2022年02月19日 19:36:00
 *      使用场景化；把部分和整体的关系用树形结构类表示，从而使客户端可以使用统一的方式
 *  处理部分对象和整体对象
 *    组合模式的核心；
 *      抽象构建（Component）角色；定义了叶子和容器构件的共同点
 *      叶子构件角色；无子节点
 *      容器构件角色；有容器特征 ，可以包含子节点
 *  应用；
 *  javax.swing.JComponent#add(Component)
 * java.awt.Container#add(Component)
 * java.util.Map#putAll(Map)
 * java.util.List#addAll(Collection)
 * java.util.Set#addAll(Collection)
 *   流程分析；
 *      组合模式为处理树型结构提供了完美的方案，描述了如何将容器和叶子进行递归组合
 *    使得用户在使用是可以一致性的对待容器和✌子
 *     当容器对象的指定方法被调用是 将遍历整个树型结构，寻找也包含这个方法的成员，
 *    并调用执行，其中使用了递归调用的机制对整个结构进行处理
 *
 */
public class Client {

    public static void main(String[] args) {
        AbstractFile f1,f2,f3;
        Folder fo=new Folder("我的收藏");
       f1= new ImageFile("TUPIAN");
        f2=new TextFile("文字");
        fo.add(f1);
        fo.add(f2);
       // f1.killVires();
        fo.killVires();

    }
}
