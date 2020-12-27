package com.example.healthcare.model;

public class Thuoc {
    private int id;
    private String ten;
    private int lieuluong;
    private String donvi;
    private String truocsau;

    public String getTruocsau() {
        return truocsau;
    }

    public void setTruocsau(String truocsau) {
        this.truocsau = truocsau;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getLieuluong() {
        return lieuluong;
    }

    public void setLieuluong(int lieuluong) {
        this.lieuluong = lieuluong;
    }

    public String getDonvi() {
        return donvi;
    }

    public void setDonvi(String donvi) {
        this.donvi = donvi;
    }

}
