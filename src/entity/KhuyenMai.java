package entity;

import java.time.LocalDate;

public class KhuyenMai {
    private String maKhuyenMai;
    private String tenKhuyenMai;
    private double phanTramGiam;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    public KhuyenMai(String maKhuyenMai, String tenKhuyenMai, double phanTramGiam, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.phanTramGiam = phanTramGiam;
        this.ngayBatDau = LocalDate.parse(ngayBatDau.toString());
        this.ngayKetThuc = LocalDate.parse(ngayKetThuc.toString());
    }
    public KhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }

    public double getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(double phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public LocalDate getNgayGioDat() {
        return ngayBatDau;
    }

    public void setNgayGioDat(LocalDate ngayGioDat) {
        this.ngayBatDau = ngayGioDat;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "maKhuyenMai='" + maKhuyenMai + '\'' +
                ", tenKhuyenMai='" + tenKhuyenMai + '\'' +
                ", phanTramGiam=" + phanTramGiam +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                '}';
    }
}
