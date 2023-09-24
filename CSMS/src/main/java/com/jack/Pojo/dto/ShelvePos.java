package com.jack.Pojo.dto;

public class ShelvePos {
    private String scode;
    private String stype;

    public ShelvePos(String scode, String stype) {
        this.scode = scode;
        this.stype = stype;
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
}
