package com.demo.strategy.gupao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lkz
 * @ClassName: PromotionStrategyFactory
 * @description: 结合单例模式和工厂模式
 * @date 2022/2/23 16:20
 */
public class PromotionStrategyFactory {

    private static Map<String,IPromotionStrategy> PROMOTIONS=new HashMap<>();

    static{
        PROMOTIONS.put(PromotionKey.COUPON,new CouponStrategy());
        PROMOTIONS.put(PromotionKey.CASHBACK,new CashbackStrategy());
        PROMOTIONS.put(PromotionKey.GROUPBUY,new GroupBuyStrategy());
    }

    private static final IPromotionStrategy EMPTY=new EmptyStrategy();

    private PromotionStrategyFactory(){}

    public static IPromotionStrategy getPromotionStrategy(String promotionKey){
        IPromotionStrategy strategy=PROMOTIONS.get(promotionKey);
        return strategy==null?EMPTY:strategy;
    }

    private interface PromotionKey{
        String COUPON="COUPON";
        String CASHBACK="CASHBACK";
        String GROUPBUY="GROUPBUY";
    }

    private static Set<String> getPromotionKeys(){
        return PROMOTIONS.keySet();
    }


    public static void main(String[] args) {
        PromotionStrategyFactory.getPromotionKeys();
        String op="GROUPBUY";
        IPromotionStrategy promotionStrategy = PromotionStrategyFactory.getPromotionStrategy(op);
        promotionStrategy.doPromotion();
    }
}
