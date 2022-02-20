package com.demo.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName AbstractFile.java
 * @Description TODO
 * @createTime 2022年02月19日 20:24:00
 */
public interface AbstractFile {
     void killVires();
}
class ImageFile implements AbstractFile
{
    private String name;

    public ImageFile(String name) {
        this.name = name;
    }

    @Override
    public void killVires() {
        System.out.println("删除 图像文件:"+name+"杀毒");
    }
}

 // 叶子组件
class TextFile implements AbstractFile
{
    private String name;

    public TextFile(String name) {
        this.name = name;
    }

    @Override
    public void killVires() {
        System.out.println("删除 文字文件:"+name+"杀毒");
    }
}
//容器
class Folder implements AbstractFile{

    private String name;
    //定义容器；用来存放本容器构建下的子节点
    private List<AbstractFile> list=new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(AbstractFile file){
        list.add(file);
    }
    public void remove(AbstractFile file){
        list.remove(file);
    }
    public AbstractFile getChild(int index){
       return list.get(index);
    }

    @Override
    public void killVires() {
        System.out.println(" 文件夹："+name+"查杀");
        for(AbstractFile file:list){
            file.killVires();
        }
    }
}