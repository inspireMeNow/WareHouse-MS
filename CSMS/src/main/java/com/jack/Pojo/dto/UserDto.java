package com.jack.Pojo.dto;

public class UserDto {
    private String ucode;//工号
    private String rcode;//仓库号
    private String whname;//仓库名
    private String uname;//姓名
    private String phone;//联系电话

    public UserDto() {
    }

    public UserDto(String ucode, String rcode, String whname, String uname, String phone) {
        this.ucode = ucode;
        this.rcode = rcode;
        this.whname = whname;
        this.uname = uname;
        this.phone = phone;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public String getRcode() {
        return rcode;
    }

    public void setRcode(String rcode) {
        this.rcode = rcode;
    }

    public String getWhname() {
        return whname;
    }

    public void setWhname(String whname) {
        this.whname = whname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "ucode='" + ucode + '\'' +
                ", rcode='" + rcode + '\'' +
                ", whname='" + whname + '\'' +
                ", uname='" + uname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
