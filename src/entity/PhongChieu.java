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
        this();
        this.tenPhong = tenPhong;
        this.soGhe = soGhe;
    }
    
    public PhongChieu(int maPhong, String tenPhong, int soGhe) {
        this(tenPhong, soGhe);
        this.maPhong = maPhong;
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

    @Override
    public String toString() {
        return "PhongChieu [" +
               "maPhong=" + maPhong + 
               ", tenPhong='" + tenPhong + "'" + 
               ", soGhe=" + soGhe + 
               "]";
    }
}
