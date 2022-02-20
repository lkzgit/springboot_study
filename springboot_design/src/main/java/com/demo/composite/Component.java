package com.demo.composite;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName Component.java
 * @Description TODO 抽象组件
 * @createTime 2022年02月19日 19:44:00
 */
public interface Component {
    void operation();
}
//叶子节点
interface   Leaf extends Component{

}
// 容器组件
    interface  Composite extends Component{
    void add(Component component);
    void remove(Component component);
    Component getChild(int index);//索引获取
}