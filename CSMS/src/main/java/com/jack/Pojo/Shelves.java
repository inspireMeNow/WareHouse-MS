package com.jack.Pojo;

public class Shelves {
    private String scode;//货架号
    private String stype;//货架类型
    private int snum;//货架容量
    private int whcode;//仓库号

    public Shelves() {
    }


    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public int getSnum() {
        return snum;
    }

    public void setSnum(int snum) {
        this.snum = snum;
    }

    public int getWhcode() {
        return whcode;
    }

    public void setWhcode(int whcode) {
        this.whcode = whcode;
    }


    public Shelves(String scode, String stype, int snum, int whcode) {
        this.scode = scode;
        this.stype = stype;
        this.snum = snum;
        this.whcode = whcode;
    }

    @Override
    public String toString() {
        return "Shelves{" +
                "scode='" + scode + '\'' +
                ", stype='" + stype + '\'' +
                ", snum=" + snum +
                ", whcode=" + whcode +
                '}';
    }
}
