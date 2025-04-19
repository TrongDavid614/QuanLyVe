package entity;

import java.time.LocalDate;
import java.util.Date;

public class KhuyenMai {
    private String maKhuyenMai;
    private String tenKhuyenMai;
    private double phanTramGiam;
    private LocalDate ngayGioDat;
    private LocalDate ngayKetThuc;

    public KhuyenMai(String maKhuyenMai, String tenKhuyenMai, double phanTramGiam, LocalDate ngayGioDat, LocalDate ngayKetThuc) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.phanTramGiam = phanTramGiam;
        this.ngayGioDat = LocalDate.parse(ngayGioDat.toString());
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
        return ngayGioDat;
    }

    public void setNgayGioDat(LocalDate ngayGioDat) {
        this.ngayGioDat = ngayGioDat;
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
                ", ngayGioDat=" + ngayGioDat +
                ", ngayKetThuc=" + ngayKetThuc +
                '}';
    }
}
