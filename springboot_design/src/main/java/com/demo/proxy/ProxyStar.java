package com.demo.proxy;

/**
 * @author lkz
 * @version 1.0.0
 * @ClassName RealStar.java
 * @Description TODO
 * @createTime 2022年02月17日 23:21:00
 */
public class ProxyStar implements Star
{
    private Star star;

    public ProxyStar(Star star) {
        super();
        this.star = star;
    }

    @Override
    public void confer() {
        System.out.println("ProxyStar confer");
    }

    @Override
    public void signContract() {
        System.out.println("ProxyStar  signContract");
    }

    @Override
    public void bookTicket() {
        System.out.println("ProxyStar  bookTicket");
    }

    @Override
    public void sign() {
        this.star.sign();
    }
}
