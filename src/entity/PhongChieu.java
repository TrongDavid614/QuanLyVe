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
    
    public int getMaPhongChieu() {
        return maPhongChieu;
    }
    
    public void setMaPhongChieu(int maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }
    
    public String getTenPhong() {
        return tenPhong;
    }
    
    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }
    
    public int getSucChua() {
        return sucChua;
    }
    
    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }
    
    public String getLoaiPhong() {
        return loaiPhong;
    }
    
    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }
    
    public boolean isTrangThai() {
        return trangThai;
    }
    
    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
    public void batHoatDong() {
        this.trangThai = true;
    }
    
    public void tatHoatDong() {
        this.trangThai = false;
    }
    
    @Override
    public String toString() {
        return "PhongChieu [maPhongChieu=" + maPhongChieu + ", tenPhong=" + tenPhong + 
               ", sucChua=" + sucChua + ", loaiPhong=" + loaiPhong + 
               ", trangThai=" + (trangThai ? "Đang hoạt động" : "Đang bảo trì") + "]";
    }
}
