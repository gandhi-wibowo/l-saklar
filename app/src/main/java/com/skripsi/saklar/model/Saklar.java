package com.skripsi.saklar.model;

public class Saklar {

    public Saklar() {
    }

    private String idSaklar,namaSaklar,statusSaklar;


    public String getIdSaklar() {
        return idSaklar;
    }

    public void setIdSaklar(String idSaklar) {
        this.idSaklar = idSaklar;
    }

    public String getNamaSaklar() {
        return namaSaklar;
    }

    public void setNamaSaklar(String namaSaklar) {
        this.namaSaklar = namaSaklar;
    }

    public String getStatusSaklar() {
        return statusSaklar;
    }

    public void setStatusSaklar(String statusSaklar) {
        this.statusSaklar = statusSaklar;
    }

    public Saklar(String idSaklar, String namaSaklar, String statusSaklar) {
        this.idSaklar = idSaklar;
        this.namaSaklar = namaSaklar;
        this.statusSaklar = statusSaklar;

    }
}
