package entity;

import java.util.ArrayList;
import java.util.List;

public class Ghe {
    private int maGhe;
    private String tenGhe; 
    private String loaiGhe; 
    private PhongChieu phongChieu;
    private List<ChiTietVe> chiTietVes;
    
    public Ghe() {
        this.chiTietVes = new ArrayList<>();
    }
    
    public Ghe(String tenGhe, String loaiGhe, PhongChieu phongChieu) {
        this.tenGhe = tenGhe;
        this.loaiGhe = loaiGhe;
        this.phongChieu = phongChieu;
        this.chiTietVes = new ArrayList<>();
    }
    
    public Ghe(int maGhe, String tenGhe, String loaiGhe, PhongChieu phongChieu) {
        this.maGhe = maGhe;
        this.tenGhe = tenGhe;
        this.loaiGhe = loaiGhe;
        this.phongChieu = phongChieu;
        this.chiTietVes = new ArrayList<>();
    }
    
    public int getMaGhe() {
        return maGhe;
    }
    
    public void setMaGhe(int maGhe) {
        this.maGhe = maGhe;
    }
    
    public String getTenGhe() {
        return tenGhe;
    }
    
    public void setTenGhe(String tenGhe) {
        this.tenGhe = tenGhe;
    }
    
    public String getLoaiGhe() {
        return loaiGhe;
    }
    
    public void setLoaiGhe(String loaiGhe) {
        this.loaiGhe = loaiGhe;
    }
    
    public PhongChieu getPhongChieu() {
        return phongChieu;
    }
    
    public void setPhongChieu(PhongChieu phongChieu) {
        this.phongChieu = phongChieu;
    }
    
    public List<ChiTietVe> getChiTietVes() {
        return chiTietVes;
    }
    
    public void setChiTietVes(List<ChiTietVe> chiTietVes) {
        this.chiTietVes = chiTietVes;
    }
    
    public boolean daCoNguoiDat() {
        return chiTietVes != null && !chiTietVes.isEmpty();
    }
    
    public void themChiTietVe(ChiTietVe chiTietVe) {
        if (this.chiTietVes == null) {
            this.chiTietVes = new ArrayList<>();
        }
        this.chiTietVes.add(chiTietVe);
    }
    
    @Override
    public String toString() {
        return "Ghe [maGhe=" + maGhe + ", tenGhe=" + tenGhe + ", loaiGhe=" + loaiGhe + "]";
    }
}
