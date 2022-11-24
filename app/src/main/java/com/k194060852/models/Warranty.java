package com.k194060852.models;

public class Warranty {
    int code;
    String name;
    String des;
    byte[] photo;

    public Warranty(int code, String name, String des, byte[] photo) {
        this.code = code;
        this.name = name;
        this.des = des;
        this.photo = photo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
