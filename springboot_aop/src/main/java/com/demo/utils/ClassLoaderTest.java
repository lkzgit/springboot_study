package com.demo.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lkz
 * @date 2021/12/1 14:43
 * @description 获取文件路径
 */
public class ClassLoaderTest {

    // Java同过类加载器读取资源路径问题
    @Test
    public void test1() throws IOException {
        // 获取classpath路径
        System.out.println(ClassLoaderTest.class.getClassLoader().getResource(""));
        // file:/E:/EclipseWorkspace/AllTest/bin/

        // 获取classpath路径下txtFile文件夹指定内容(第一个目录不加/)
        String path1 = ClassLoaderTest.class.getClassLoader().getResource("txtFile/test.txt").getPath();
        System.out.println(path1);
        // /E:/EclipseWorkspace/AllTest/bin/txtFile/test.txt

        // 获取classpath路径下指定内容
        String path2 = ClassLoaderTest.class.getClassLoader().getResource("test.txt").getPath();
        System.out.println(path2);
        // /E:/EclipseWorkspace/AllTest/bin/test.txt
    }

    // 通过系统的绝对路径获取，先获取工程目录，再获取工程目录下指定的文件
    @Test
    public void fun2() {
        // 获取工程路径
        System.out.println(System.getProperty("user.dir"));
        // E:\EclipseWorkspace\AllTest

        // 获取工程路径下的文件
        String path = System.getProperty("user.dir") + "\\test.txt";
        System.out.println(path);
        // E:\EclipseWorkspace\AllTest\test.txt

        // 获取工程指定文件夹下的文件(第一个目录加/)
        String path1 = System.getProperty("user.dir") + "\\src\\txtFile\\test.txt";
        System.out.println(path1);
        // E:\EclipseWorkspace\AllTest\src\txtFile\test.txt

    }

    // 读取src文件夹下的文件(txtFile从classpath下开始，前面无/)
    @Test
    public void test3() throws IOException {
        // txtFile代表src下面的一个文件夹
        String path = ClassLoaderTest.class.getClassLoader().getResource("txtFile/test.txt").getPath();
        System.out.println(path);
        // /E:/EclipseWorkspace/AllTest/bin/txtFile/test.txt
        InputStream inputStream = new FileInputStream(new File(path));
        int value1;
        while ((value1 = inputStream.read()) != -1) { // 读取文件
            System.out.print((char) value1); // www
        }
    }

    // 直接以流的形式读取获取
    @Test
    public void test4() throws IOException {
        InputStream resourceAsStream = ClassLoaderTest.class.getClassLoader().getResourceAsStream("Java_Test/test.txt");
        int value;
        while ((value = resourceAsStream.read()) != -1) { // 读取文件
            System.out.print((char) value);
        }

    }


}
