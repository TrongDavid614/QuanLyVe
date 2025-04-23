package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Ve {
    private int maVe;
    private KhachHang khachHang;
    private Phim phim;
    private PhongChieu phongChieu;
    private Date ngayDat;
    private Date gioChieu;
    private double tongTien;
    private List<ChiTietVe> chiTietVes;
    private List<DoAn> doAns;
    private KhuyenMai khuyenMai;
    private String trangThaiVe; // "CHO_THANH_TOAN", "DA_THANH_TOAN", "DA_HUY"
    private String phuongThucThanhToan;
    private Date ngayThanhToan;
    private String maGiaoDich;

    public Ve() {
        this.ngayDat = new Date();
        this.chiTietVes = new ArrayList<>();
        this.doAns = new ArrayList<>();
        this.trangThaiVe = "CHO_THANH_TOAN";
    }

    public Ve(KhachHang khachHang, Phim phim, PhongChieu phongChieu, Date gioChieu) {
        this();
        this.khachHang = khachHang;
        this.phim = phim;
        this.phongChieu = phongChieu;
        this.gioChieu = gioChieu;
    }

    public Ve(int maVe, KhachHang khachHang, Phim phim, PhongChieu phongChieu, 
             Date ngayDat, Date gioChieu, double tongTien, String trangThaiVe,
             String phuongThucThanhToan, Date ngayThanhToan, String maGiaoDich) {
        this.maVe = maVe;
        this.khachHang = khachHang;
        this.phim = phim;
        this.phongChieu = phongChieu;
        this.ngayDat = ngayDat;
        this.gioChieu = gioChieu;
        this.tongTien = tongTien;
        this.chiTietVes = new ArrayList<>();
        this.doAns = new ArrayList<>();
        this.trangThaiVe = trangThaiVe;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.ngayThanhToan = ngayThanhToan;
        this.maGiaoDich = maGiaoDich;
    }

    public int getMaVe() { return maVe; }
    public void setMaVe(int maVe) { this.maVe = maVe; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public Phim getPhim() { return phim; }
    public void setPhim(Phim phim) { this.phim = phim; }

    public PhongChieu getPhongChieu() { return phongChieu; }
    public void setPhongChieu(PhongChieu phongChieu) { this.phongChieu = phongChieu; }

    public Date getNgayDat() { return ngayDat; }
    public void setNgayDat(Date ngayDat) { this.ngayDat = ngayDat; }

    public Date getGioChieu() { return gioChieu; }
    public void setGioChieu(Date gioChieu) { this.gioChieu = gioChieu; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public List<ChiTietVe> getChiTietVes() { return chiTietVes; }
    public void setChiTietVes(List<ChiTietVe> chiTietVes) { this.chiTietVes = chiTietVes; }

    public List<DoAn> getDoAns() { return doAns; }
    public void setDoAns(List<DoAn> doAns) { this.doAns = doAns; }

    public KhuyenMai getKhuyenMai() { return khuyenMai; }
    public void setKhuyenMai(KhuyenMai khuyenMai) { this.khuyenMai = khuyenMai; }

    public String getTrangThaiVe() { return trangThaiVe; }
    public void setTrangThaiVe(String trangThaiVe) { this.trangThaiVe = trangThaiVe; }

    public String getPhuongThucThanhToan() { return phuongThucThanhToan; }
    public void setPhuongThucThanhToan(String phuongThucThanhToan) { this.phuongThucThanhToan = phuongThucThanhToan; }

    public Date getNgayThanhToan() { return ngayThanhToan; }
    public void setNgayThanhToan(Date ngayThanhToan) { this.ngayThanhToan = ngayThanhToan; }

    public String getMaGiaoDich() { return maGiaoDich; }
    public void setMaGiaoDich(String maGiaoDich) { this.maGiaoDich = maGiaoDich; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ve ve = (Ve) o;
        return maVe == ve.maVe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maVe);
    }

    @Override
    public String toString() {
        return "Ve [" +
               "maVe=" + maVe +
               ", khachHang=" + (khachHang != null ? khachHang.getTenKhachHang() : "null") +
               ", phim=" + (phim != null ? phim.getTenPhim() : "null") +
               ", phongChieu=" + (phongChieu != null ? phongChieu.getTenPhong() : "null") +
               ", ngayDat=" + ngayDat +
               ", gioChieu=" + gioChieu +
               ", tongTien=" + tongTien +
               ", trangThaiVe='" + trangThaiVe + '\'' +
               ", maGiaoDich='" + maGiaoDich + '\'' +
               ']';
    }
}
