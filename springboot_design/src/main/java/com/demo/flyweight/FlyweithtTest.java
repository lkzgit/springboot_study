package com.demo.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元模式
 * 模式定义: 运用共享技术有效地支持大量细粒度的对象
 * 优点： 如果系统有大量类似的对象，可以节省大量的内存及CPU资源
 */
public class FlyweithtTest {
    public static void main(String[] args) {
        TreeType treeTypeByName=TreeFactory.getTreeTypeByName( "xxx", "yyyyy" );
        TreeNode treeNode1=new TreeNode( 0,0, treeTypeByName );
        TreeNode treeNode2=new TreeNode( 4,6, treeTypeByName );



    }
}

class TreeNode{

    private int x;
    private int y;
    private TreeType treeType;

    public TreeNode(int x, int y, TreeType treeType) {
        this.x=x;
        this.y=y;
        this.treeType=treeType;
    }
}
class TreeType{
    private final String name;
    private final String data;

    public TreeType(String name, String data) {
        System.out.println("TreeType");
        this.name=name;
        this.data=data;
    }

    public String getName() {
        return name;
    }


    public String getData() {
        return data;
    }


}

class TreeFactory {
    static Map<String, TreeType> map = new HashMap<>();

    public static TreeType getTreeTypeByName(String name, String data) {

        if (map.containsKey(name)) {
            return map.get(name);

        }
        TreeType treeType = new TreeType(name, data);
        map.put(name, treeType);
        return treeType;
    }

}