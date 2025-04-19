package entity;

public class Ghe {
    private int maGhe;
    private String tenGhe;         
    private int maPhongChieu;      
    private boolean daChon;        
    private String loaiGhe;        
    private double giaGhe;         
    
    public Ghe() {
        this.daChon = false;
    }
    
    public Ghe(int maGhe, String tenGhe, int maPhongChieu, boolean daChon, String loaiGhe, double giaGhe) {
        this.maGhe = maGhe;
        this.tenGhe = tenGhe;
        this.maPhongChieu = maPhongChieu;
        this.daChon = daChon;
        this.loaiGhe = loaiGhe;
        this.giaGhe = giaGhe;
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
    
    public int getMaPhongChieu() {
        return maPhongChieu;
    }
    
    public void setMaPhongChieu(int maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }
    
    public boolean isDaChon() {
        return daChon;
    }
    
    public void setDaChon(boolean daChon) {
        this.daChon = daChon;
    }
    
    public String getLoaiGhe() {
        return loaiGhe;
    }
    
    public void setLoaiGhe(String loaiGhe) {
        this.loaiGhe = loaiGhe;
    }
    
    public double getGiaGhe() {
        return giaGhe;
    }
    
    public void setGiaGhe(double giaGhe) {
        this.giaGhe = giaGhe;
    }
    
    public boolean chonGhe() {
        if(!daChon) {
            daChon = true;
            return true;
        }
        return false;
    }
    
    public boolean huyChonGhe() {
        if(daChon) {
            daChon = false;
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Ghe [maGhe=" + maGhe + ", tenGhe=" + tenGhe + 
               ", phongChieu=" + maPhongChieu + ", loaiGhe=" + loaiGhe + 
               ", giaGhe=" + giaGhe + ", trangThai=" + (daChon ? "Đã chọn" : "Trống") + "]";
    }
}
