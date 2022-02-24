package com.demo.strategy.gupao;

/**
 * @author lkz
 * @ClassName: GroupBuyStrategy
 * @description: TODO
 * @date 2022/2/23 16:10
 */
public class GroupBuyStrategy implements IPromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("5人成团 ，可以优惠");
    }
}
