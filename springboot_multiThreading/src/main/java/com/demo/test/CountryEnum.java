package com.demo.test;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public enum CountryEnum {
    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");

    private Integer retcode;
    private String retMessage;

    CountryEnum(Integer retcode, String retMessage) {
        this.retcode = retcode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEach_countryEnum(int index) {

        CountryEnum[] myArray = CountryEnum.values();

        for(CountryEnum ce : myArray) {
            if(Objects.equals(index, ce.getRetcode())) {
                return ce;
            }
        }

        return null;
    }

    public Integer getRetcode() {
        return retcode;
    }

    public void setRetcode(Integer retcode) {
        this.retcode = retcode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

}

 class UnifySixCountriesDemo{

     public static void main(String[] args) throws InterruptedException {
         // 计数器
         CountDownLatch countDownLatch = new CountDownLatch(6);

         for (int i = 1; i <= 6; i++) {
             new Thread(() -> {
                 System.out.println(Thread.currentThread().getName() + "国被灭了！");
                 countDownLatch.countDown();
             }, CountryEnum.forEach_countryEnum(i).getRetMessage()).start();
         }

         countDownLatch.await();

         System.out.println(Thread.currentThread().getName() + " 秦国统一中原。");
     }


}
