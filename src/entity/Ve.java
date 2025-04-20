package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ve {
    private int maVe;
    private KhachHang khachHang;
    private Phim phim;
    private PhongChieu phongChieu;
    private Date ngayDat;
    private Date gioChieu;
    private double tongTien;
    private List<ChiTietVe> chiTietVes;
    private ThongKe thongKe;
    private List<DoAn> doAns;
    private KhuyenMai khuyenMai;
    
    public Ve() {
        this.ngayDat = new Date();
        this.chiTietVes = new ArrayList<>();
        this.doAns = new ArrayList<>();
    }
    
    public Ve(KhachHang khachHang, Phim phim, PhongChieu phongChieu, Date gioChieu) {
        this.khachHang = khachHang;
        this.phim = phim;
        this.phongChieu = phongChieu;
        this.gioChieu = gioChieu;
        this.ngayDat = new Date();
        this.chiTietVes = new ArrayList<>();
        this.doAns = new ArrayList<>();
    }
    
    public Ve(int maVe, KhachHang khachHang, Phim phim, PhongChieu phongChieu, 
             Date ngayDat, Date gioChieu, double tongTien) {
        this.maVe = maVe;
        this.khachHang = khachHang;
        this.phim = phim;
        this.phongChieu = phongChieu;
        this.ngayDat = ngayDat;
        this.gioChieu = gioChieu;
        this.tongTien = tongTien;
        this.chiTietVes = new ArrayList<>();
        this.doAns = new ArrayList<>();
    }
    
    public int getMaVe() {
        return maVe;
    }
    
    public void setMaVe(int maVe) {
        this.maVe = maVe;
    }
    
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
    
    public Date getNgayDat() {
        return ngayDat;
    }
    
    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }
    
    public Date getGioChieu() {
        return gioChieu;
    }
    
    public void setGioChieu(Date gioChieu) {
        this.gioChieu = gioChieu;
    }
    
    public double getTongTien() {
        return tongTien;
    }
    
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
    public List<ChiTietVe> getChiTietVes() {
        return chiTietVes;
    }
    
    public void setChiTietVes(List<ChiTietVe> chiTietVes) {
        this.chiTietVes = chiTietVes;
    }
    
    public ThongKe getThongKe() {
        return thongKe;
    }
    
    public void setThongKe(ThongKe thongKe) {
        this.thongKe = thongKe;
    }
    
    public List<DoAn> getDoAns() {
        return doAns;
    }
    
    public void setDoAns(List<DoAn> doAns) {
        this.doAns = doAns;
    }
    
    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }
    
    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }
    
    public void themGhe(Ghe ghe) {
        if (ghe.daCoNguoiDat()) {
            throw new IllegalStateException("Ghế này đã được đặt!");
        }
        
        if (this.chiTietVes == null) {
            this.chiTietVes = new ArrayList<>();
        }
        
        ChiTietVe chiTietVe = new ChiTietVe(this, ghe);
        this.chiTietVes.add(chiTietVe);
        ghe.themChiTietVe(chiTietVe);
        
        tinhTongTien();
    }
    
    public void xoaGhe(Ghe ghe) {
        if (this.chiTietVes == null) {
            return;
        }
        
        ChiTietVe chiTietVeToRemove = null;
        for (ChiTietVe ctv : chiTietVes) {
            if (ctv.getGhe().getMaGhe() == ghe.getMaGhe()) {
                chiTietVeToRemove = ctv;
                break;
            }
        }
        
        if (chiTietVeToRemove != null) {
            chiTietVes.remove(chiTietVeToRemove);
            ghe.getChiTietVes().remove(chiTietVeToRemove);
            
            tinhTongTien();
        }
    }
    
    public void themDoAn(DoAn doAn) {
        if (this.doAns == null) {
            this.doAns = new ArrayList<>();
        }
        this.doAns.add(doAn);
        tinhTongTien();
    }
    

    public void xoaDoAn(DoAn doAn) {
        if (this.doAns != null) {
            this.doAns.remove(doAn);
            tinhTongTien();
        }
    }
 
    public void tinhTongTien() {
        double tienGhe = 0;
        if (chiTietVes != null) {
            for (ChiTietVe ctv : chiTietVes) {
               
                if ("VIP".equals(ctv.getGhe().getLoaiGhe())) {
                    tienGhe += 120000; 
                } else {
                    tienGhe += 80000; 
                }
            }
        }
        
        double tienDoAn = 0;
        if (doAns != null) {
            for (DoAn doAn : doAns) {
                tienDoAn += doAn.getGiaDoAn();
            }
        }
        
        double tongTienTruocGiam = tienGhe + tienDoAn;
        
        if (khuyenMai != null) {
            LocalDate ngayHienTai = LocalDate.now();

            if (ngayHienTai.isAfter(khuyenMai.getNgayGioDat()) && 
                ngayHienTai.isBefore(khuyenMai.getNgayKetThuc())) {
                double phanTramGiam = khuyenMai.getPhanTramGiam();
                this.tongTien = tongTienTruocGiam * (1 - phanTramGiam / 100.0);
                return;
            }
        }
        
        this.tongTien = tongTienTruocGiam;
    }
    
    @Override
    public String toString() {
        return "Ve [maVe=" + maVe + 
               ", khachHang=" + (khachHang != null ? khachHang.getTenKhachHang() : "null") + 
               ", phim=" + (phim != null ? phim.getTenPhim() : "null") +
               ", phongChieu=" + (phongChieu != null ? phongChieu.getTenPhong() : "null") + 
               ", ngayDat=" + ngayDat + 
               ", gioChieu=" + gioChieu + 
               ", tongTien=" + tongTien + "]";
    }
}
