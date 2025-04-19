package entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Ve {
    private int maVe;          
    private Date ngayBan;     
    private double tongTien;  
    private boolean daThanhToan;
    private int maNhanVien;    
    private int maKhachHang;  
    private List<ChiTietVe> dsChiTietVe;  
    
    public Ve() {
        this.ngayBan = new Date();
        this.daThanhToan = false;
        this.dsChiTietVe = new ArrayList<>();
    }
    
    public Ve(int maVe, Date ngayBan, double tongTien, boolean daThanhToan, 
              int maNhanVien, int maKhachHang) {
        this.maVe = maVe;
        this.ngayBan = ngayBan;
        this.tongTien = tongTien;
        this.daThanhToan = daThanhToan;
        this.maNhanVien = maNhanVien;
        this.maKhachHang = maKhachHang;
        this.dsChiTietVe = new ArrayList<>();
    }
    
    public int getMaVe() {
        return maVe;
    }
    
    public void setMaVe(int maVe) {
        this.maVe = maVe;
    }
    
    public Date getNgayBan() {
        return ngayBan;
    }
    
    public void setNgayBan(Date ngayBan) {
        this.ngayBan = ngayBan;
    }
    
    public double getTongTien() {
        return tongTien;
    }
    
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
    public boolean isDaThanhToan() {
        return daThanhToan;
    }
    
    public void setDaThanhToan(boolean daThanhToan) {
        this.daThanhToan = daThanhToan;
    }
    
    public int getMaNhanVien() {
        return maNhanVien;
    }
    
    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }
    
    public int getMaKhachHang() {
        return maKhachHang;
    }
    
    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
    
    public List<ChiTietVe> getDsChiTietVe() {
        return dsChiTietVe;
    }
    
    public void setDsChiTietVe(List<ChiTietVe> dsChiTietVe) {
        this.dsChiTietVe = dsChiTietVe;
    }
    
    public void themChiTietVe(ChiTietVe chiTietVe) {
        dsChiTietVe.add(chiTietVe);
        tinhTongTien();
    }
    
    public boolean xoaChiTietVe(ChiTietVe chiTietVe) {
        boolean ketQua = dsChiTietVe.remove(chiTietVe);
        if(ketQua) {
            tinhTongTien();
        }
        return ketQua;
    }
    
    public void tinhTongTien() {
        double tong = 0;
        for(ChiTietVe ctv : dsChiTietVe) {
            tong += ctv.getGiaVe();
        }
        this.tongTien = tong;
    }
    
    public void thanhToan() {
        this.daThanhToan = true;
    }
    
    @Override
    public String toString() {
        return "Ve [maVe=" + maVe + ", ngayBan=" + ngayBan + 
               ", tongTien=" + tongTien + ", daThanhToan=" + daThanhToan + 
               ", soLuongVe=" + dsChiTietVe.size() + "]";
    }
}
