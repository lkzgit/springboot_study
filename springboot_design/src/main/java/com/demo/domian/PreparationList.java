package com.demo.domian;

public class PreparationList {

    //是否洗脸
    private boolean washFace;

    //是否洗头
    private boolean washHair;

    //是否吃早餐
    private boolean havaBreakfast;

    public boolean isWashFace() {
        return washFace;
    }

    public void setWashFace(boolean washFace) {
        this.washFace = washFace;
    }

    public boolean isWashHair() {
        return washHair;
    }

    public void setWashHair(boolean washHair) {
        this.washHair = washHair;
    }

    public boolean isHavaBreakfast() {
        return havaBreakfast;
    }

    public void setHavaBreakfast(boolean havaBreakfast) {
        this.havaBreakfast = havaBreakfast;
    }

    public PreparationList() {
    }

    public PreparationList(boolean washFace, boolean washHair, boolean havaBreakfast) {
        this.washFace = washFace;
        this.washHair = washHair;
        this.havaBreakfast = havaBreakfast;
    }

    @Override
    public String toString() {
        return "PreparationList{" +
                "washFace=" + washFace +
                ", washHair=" + washHair +
                ", havaBreakfast=" + havaBreakfast +
                '}';
    }
}
