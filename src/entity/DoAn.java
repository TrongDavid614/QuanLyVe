package entity;

import javax.swing.*;

public class DoAn {
    private String maDoAn;
    private String tenDoAn;
    private double giaDoAn;
    private String moTa;
    private String hinhAnh;

    public DoAn(String maDoAn, String tenDoAn, double giaDoAn, String moTa , String hinhAnh) {
        this.maDoAn = maDoAn;
        this.tenDoAn = tenDoAn;
        this.giaDoAn = giaDoAn;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
    }

    public DoAn(String maDoAn) {
        this.maDoAn = maDoAn;
    }

    public String getMaDoAn() {
        return maDoAn;
    }

    public void setMaDoAn(String maDoAn) {
        this.maDoAn = maDoAn;
    }

    public String getTenDoAn() {
        return tenDoAn;
    }

    public void setTenDoAn(String tenDoAn) {
        this.tenDoAn = tenDoAn;
    }

    public double getGiaDoAn() {
        return giaDoAn;
    }

    public void setGiaDoAn(double giaDoAn) {
        this.giaDoAn = giaDoAn;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    @Override
    public String toString() {
        return "DoAn{" +
                "maDoAn='" + maDoAn + '\'' +
                ", tenDoAn='" + tenDoAn + '\'' +
                ", giaDoAn=" + giaDoAn +
                ", moTa='" + moTa + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                '}';
    }
}
