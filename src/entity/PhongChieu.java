package entity;

public class PhongChieu {
    private int maPhongChieu;
    private String tenPhong;
    private int sucChua;      
    private String loaiPhong; 
    private boolean trangThai; 
    
    public PhongChieu() {
        this.trangThai = true; 
    }
    
    public PhongChieu(int maPhongChieu, String tenPhong, int sucChua, String loaiPhong, boolean trangThai) {
        this.maPhongChieu = maPhongChieu;
        this.tenPhong = tenPhong;
        this.sucChua = sucChua;
        this.loaiPhong = loaiPhong;
        this.trangThai = trangThai;
    }
    
package entity;

import java.util.ArrayList;
import java.util.List;

public class PhongChieu {
    private int maPhong;
    private String tenPhong;
    private int soGhe;
    private List<Ghe> danhSachGhe;
    
    public PhongChieu() {
        this.danhSachGhe = new ArrayList<>();
    }
    
    public PhongChieu(String tenPhong, int soGhe) {
        this.tenPhong = tenPhong;
        this.soGhe = soGhe;
        this.danhSachGhe = new ArrayList<>();
    }
    
    public PhongChieu(int maPhong, String tenPhong, int soGhe) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.soGhe = soGhe;
        this.danhSachGhe = new ArrayList<>();
    }
    
    public int getMaPhong() {
        return maPhong;
    }
    
    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }
    
    public String getTenPhong() {
        return tenPhong;
    }
    
    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }
    
    public int getSoGhe() {
        return soGhe;
    }
    
    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }
    
    public List<Ghe> getDanhSachGhe() {
        return danhSachGhe;
    }
    
    public void setDanhSachGhe(List<Ghe> danhSachGhe) {
        this.danhSachGhe = danhSachGhe;
    }
    
    public void themGhe(Ghe ghe) {
        if (this.danhSachGhe == null) {
            this.danhSachGhe = new ArrayList<>();
        }
        danhSachGhe.add(ghe);
        ghe.setPhongChieu(this);
    }
    
    public void taoGheTuDong() {
        int tongSoGhe = this.soGhe;
        int soGheMotHang = 10; 
        int soHang = (int) Math.ceil((double) tongSoGhe / soGheMotHang);
        int phanTramGheVIP = 50; 
        int soGheVIP = (tongSoGhe * phanTramGheVIP) / 100;
        
        char hangChu = 'A';
        int gheDem = 0;
    
        for (int i = 0; i < soHang; i++) {
            for (int j = 1; j <= soGheMotHang; j++) {
                if (gheDem >= tongSoGhe) break;
                
                String tenGhe = String.valueOf(hangChu) + j;
                String loaiGhe = (gheDem < soGheVIP) ? "VIP" : "Thường";
    
                Ghe ghe = new Ghe(tenGhe, loaiGhe, this);
                themGhe(ghe);
                gheDem++;
            }
            hangChu++;
        }
    }
    
    @Override
    public String toString() {
        return "PhongChieu [maPhong=" + maPhong + ", tenPhong=" + tenPhong + ", soGhe=" + soGhe + "]";
    }
}
