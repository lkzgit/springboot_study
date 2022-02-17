package com.demo.create.builder;



/**
 * 建造者模式
 * 模式定义: 将一个复杂对象的创建与他的表示分离，使得同样的构建过程可以创建 不同的表示
 * 分离了对象子组件的单独构造(由Builder来负责)和装配(由Director负责) 从而构造出
 * 复杂的对象 适用于；某个对象构建过程复杂的情况下使用
 *  由于实现了构建和装配的解耦 不同的构建器 ，相同的的装配 也可以做出不同的对象、
 *  相同的构建器 不同的装配顺序也可以做出不同的对象 也就是实现了构建算法 装配算法的解耦 实现了更好的复用
 * 应用场景：
 * 1.需要生成的对象具有复杂的内部结构
 * 2.需要生成的对象内部属性本身相互依赖
 * 3.与不可变对象配合使用
 *
 */
public class BuilderTest {

    public static void main(String[] args) {
        ProductBuilder productBuilder=new DefaultProductBuilder();
        //建造者
        Director director=new Director( productBuilder );
        Product product=director.makeProduct( "part1", "part2", "part3", "part4" );
        System.out.println(product);

    }
}

interface ProductBuilder{
    void buildPart1(String part1 );
    void buildPart2(String part2 );
    void buildPart3(String part3 );
    void buildPart4(String part4 );
    Product build();
}

class DefaultProductBuilder implements ProductBuilder{
    private String part1;
    private String part2;
    private String part3;
    private String part4;
    @Override
    public void buildPart1(String part1) {
        this.part1=part1;
    }

    @Override
    public void buildPart2(String part2) {
        this.part2=part2;
    }

    @Override
    public void buildPart3(String part3) {
        this.part3=part3;
    }

    @Override
    public void buildPart4(String part4) {
        this.part4=part4;
    }

    @Override
    public Product build() {
        return new Product(part1, part2, part3, part4 );
    }


}


class Director{
    private ProductBuilder productBuilder;

    public Director(ProductBuilder productBuilder) {
        this.productBuilder=productBuilder;
    }

    public Product makeProduct(String part1, String part2, String part3, String part4){
        productBuilder.buildPart1( part1 );
        productBuilder.buildPart2( part2 );
        productBuilder.buildPart3( part3 );
        productBuilder.buildPart4( part4 );
        Product product=productBuilder.build();
        return product;

    }
}

class Product{

    private String part1;
    private String part2;
    private String part3;
    private String part4;
    //  .....


    public Product(String part1, String part2, String part3, String part4) {
        this.part1=part1;
        this.part2=part2;
        this.part3=part3;
        this.part4=part4;
    }

    public String getPart1() {
        return part1;
    }

    public void setPart1(String part1) {
        this.part1=part1;
    }

    public String getPart2() {
        return part2;
    }

    public void setPart2(String part2) {
        this.part2=part2;
    }

    public String getPart3() {
        return part3;
    }

    public void setPart3(String part3) {
        this.part3=part3;
    }

    public String getPart4() {
        return part4;
    }

    public void setPart4(String part4) {
        this.part4=part4;
    }

    @Override
    public String toString() {
        return "Product{" +
                "part1='" + part1 + '\'' +
                ", part2='" + part2 + '\'' +
                ", part3='" + part3 + '\'' +
                ", part4='" + part4 + '\'' +
                '}';
    }

}
