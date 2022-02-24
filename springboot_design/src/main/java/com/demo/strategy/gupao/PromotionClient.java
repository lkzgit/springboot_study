package com.demo.strategy.gupao;

/**
 * @author lkz
 * @ClassName: PromotionClient
 * @description: 测试
 * @date 2022/2/23 16:15
 */
public class PromotionClient {

    public static void main(String[] args) {
        PromotionActivity activity= new PromotionActivity(new EmptyStrategy());
        activity.execute();
    }
}
