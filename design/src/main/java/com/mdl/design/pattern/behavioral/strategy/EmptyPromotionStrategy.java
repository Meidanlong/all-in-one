package com.mdl.design.pattern.behavioral.strategy;

/**
 * Created by meidanlong
 */
public class EmptyPromotionStrategy implements PromotionStrategy {
    @Override
    public void doPromotion() {
        System.out.println("无促销活动");
    }
}
