package com.demo.strategy.gupao;

/**
 * @author lkz
 * @ClassName: EmptyStrategy
 * @description: TODO
 * @date 2022/2/23 16:10
 */
public class EmptyStrategy implements IPromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("没有优惠");
    }
}
