package entity;

import java.util.ArrayList;
import java.util.List;

public class Ghe {
    public enum LoaiGhe {
        THUONG,
        VIP
    }

    public enum TrangThaiGhe {
        CON_TRONG,
        DA_DAT
    }

    private int maGhe;
    private String tenGhe;  
    private LoaiGhe loaiGhe;
    private PhongChieu phongChieu;
    private List<ChiTietVe> chiTietVes;
    private TrangThaiGhe trangThai;
    
    public Ghe() {
        this.chiTietVes = new ArrayList<>();
        this.loaiGhe = LoaiGhe.THUONG;  
    }
    
    public Ghe(String tenGhe, LoaiGhe loaiGhe, PhongChieu phongChieu) {
        this();
        this.tenGhe = tenGhe;
        this.loaiGhe = loaiGhe;
        this.phongChieu = phongChieu;
    }

    public Ghe(int maGhe, String tenGhe, LoaiGhe loaiGhe, PhongChieu phongChieu) {
        this(tenGhe, loaiGhe, phongChieu);
        this.maGhe = maGhe;
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
    
    public LoaiGhe getLoaiGhe() {
        return loaiGhe;
    }
    
    public void setLoaiGhe(LoaiGhe loaiGhe) {
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

    public TrangThaiGhe getTrangThai() {
        return trangThai;
    }
    
    public void setTrangThai(TrangThaiGhe trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "Ghe [" +
               "maGhe=" + maGhe + 
               ", tenGhe=" + tenGhe + 
               ", loaiGhe=" + loaiGhe + 
               ", phongChieu=" + (phongChieu != null ? phongChieu.getTenPhong() : "null") +
               "]";
    }
}
