package com.jack.Pojo.dto;

public class OrderNum {
    private String date;
    private int num;

    public OrderNum() {
    }

    public OrderNum(String date, int num) {
        this.date = date;
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "OrderNum{" +
                "date='" + date + '\'' +
                ", num=" + num +
                '}';
    }
}
