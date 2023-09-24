package com.jack.Pojo.dto;

public class ShelvesDto {
    private String scode;//货架号
    private String stype;//货架类型
    private int snum;//货架容量
    private int whcode;//仓库号
    private String whname;//仓库名

    public ShelvesDto() {
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

    public String getWhname() {
        return whname;
    }

    public void setWhname(String whname) {
        this.whname = whname;
    }

    public ShelvesDto(String scode, String stype, int snum, int whcode, String whname) {
        this.scode = scode;
        this.stype = stype;
        this.snum = snum;
        this.whcode = whcode;
        this.whname = whname;
    }

    @Override
    public String toString() {
        return "Shelves{" +
                "scode='" + scode + '\'' +
                ", stype='" + stype + '\'' +
                ", snum=" + snum +
                ", whcode=" + whcode +
                ", whname='" + whname + '\'' +
                '}';
    }
}
