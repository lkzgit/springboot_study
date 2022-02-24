package com.demo.strategy.gupao;

/**
 * @author lkz
 * @ClassName: CouponStrategy
 * @description: TODO
 * @date 2022/2/23 16:10
 */
public class CouponStrategy implements IPromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("使用优惠券抵扣");
    }
}
