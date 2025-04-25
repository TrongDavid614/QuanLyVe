package entity;

import java.util.List;

public class DatVe {
    private KhachHang khachHang;
    private Phim phim;
    private PhongChieu phongChieu;
    private java.util.Date ngayDat;
    private java.util.Date gioChieu;
    private List<Integer> danhSachGhe;
    private double tongTien;

    public DatVe() {
    }

    public DatVe(KhachHang khachHang, Phim phim, PhongChieu phongChieu, java.util.Date ngayDat, java.util.Date gioChieu, List<Integer> danhSachGhe, double tongTien) {
        this.khachHang = khachHang;
        this.phim = phim;
        this.phongChieu = phongChieu;
        this.ngayDat = ngayDat;
        this.gioChieu = gioChieu;
        this.danhSachGhe = danhSachGhe;
        this.tongTien = tongTien;
    }

    // Getters and setters
    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public Phim getPhim() {
        return phim;
    }

    public void setPhim(Phim phim) {
        this.phim = phim;
    }

    public PhongChieu getPhongChieu() {
        return phongChieu;
    }

    public void setPhongChieu(PhongChieu phongChieu) {
        this.phongChieu = phongChieu;
    }

    public java.util.Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(java.util.Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public java.util.Date getGioChieu() {
        return gioChieu;
    }

    public void setGioChieu(java.util.Date gioChieu) {
        this.gioChieu = gioChieu;
    }

    public List<Integer> getDanhSachGhe() {
        return danhSachGhe;
    }

    public void setDanhSachGhe(List<Integer> danhSachGhe) {
        this.danhSachGhe = danhSachGhe;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}