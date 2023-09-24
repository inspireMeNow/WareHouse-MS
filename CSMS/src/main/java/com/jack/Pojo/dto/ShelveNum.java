package com.jack.Pojo.dto;

public class ShelveNum {
    private String shtype;
    private int num;

    public ShelveNum(String shtype, int num) {
        this.shtype = shtype;
        this.num = num;
    }

    public String getShtype() {
        return shtype;
    }

    public void setShtype(String shtype) {
        this.shtype = shtype;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
