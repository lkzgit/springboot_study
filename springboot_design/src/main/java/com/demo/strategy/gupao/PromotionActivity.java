package com.demo.strategy.gupao;

/**
 * @author lkz
 * @ClassName: PromotionActivity
 * @description: 活动方案类
 * @date 2022/2/23 16:13
 */
public class PromotionActivity {

    private IPromotionStrategy iPromotionStrategy;
    //提供构造方法
    public PromotionActivity(IPromotionStrategy iPromotionStrategy) {
        this.iPromotionStrategy = iPromotionStrategy;
    }

    //活动执行
    public void execute(){
        iPromotionStrategy.doPromotion();
    }
}
