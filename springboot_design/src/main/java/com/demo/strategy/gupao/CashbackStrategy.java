package com.demo.strategy.gupao;

/**
 * @author lkz
 * @ClassName: CashbackStrategy
 * @description: TODO
 * @date 2022/2/23 16:10
 */
public class CashbackStrategy implements IPromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("返现直接打款到支付宝");
    }
}
