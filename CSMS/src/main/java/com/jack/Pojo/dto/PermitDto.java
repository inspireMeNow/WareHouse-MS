package com.jack.Pojo.dto;

public class PermitDto {
    private String id;//账号
    private String uname;//姓名
    private String passwd;//密码
    private String email;//邮箱
    private String idtype;//用户类型
    private int state;//用户状态

    @Override
    public String toString() {
        return "PermitDto{" +
                "id='" + id + '\'' +
                ", uname='" + uname + '\'' +
                ", passwd='" + passwd + '\'' +
                ", email='" + email + '\'' +
                ", idtype=" + idtype +
                ", state=" + state +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
