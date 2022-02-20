package com.demo.flyweight.sxt;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName ChessFlyWeight.java
 * @Description TODO 享元类
 * @createTime 2022年02月19日 22:45:00
 */
public interface ChessFlyWeight {
      void setColor(String c);
      String getColor();
      void display(Coordinate c);

}
// 具体享元类
class ConcreteChess implements ChessFlyWeight{

    private String color;

    public ConcreteChess(String color) {
        this.color = color;
    }

    @Override
    public void setColor(String c) {
        color=c;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void display(Coordinate c) {
        System.out.println("棋子颜色："+color);
        System.out.println("棋子位置："+c.getX()+"--"+c.getY());
    }
}
//享元工厂
class ChessFactory{

    //享元池
    private static Map<String,ChessFlyWeight> map=new HashMap<>();

    public static ChessFlyWeight getChess(String color){
        if(map.get(color)!=null){
            return map.get(color);
        }else{
            ChessFlyWeight cc=new ConcreteChess(color);
                map.put(color,cc);
                return cc;
        }
    }

}
