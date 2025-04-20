package entity;

public class ChiTietVe {
    private int maChiTietVe;
    private int maVe;          
    private int maPhim;     
    private int maPhongChieu;  
    private int maLichChieu;  
    private String viTriGhe;  
    private double giaVe;      
    
    public ChiTietVe() {
    }
    
    public ChiTietVe(int maChiTietVe, int maVe, int maPhim, int maPhongChieu, 
                    int maLichChieu, String viTriGhe, double giaVe) {
        this.maChiTietVe = maChiTietVe;
        this.maVe = maVe;
        this.maPhim = maPhim;
        this.maPhongChieu = maPhongChieu;
        this.maLichChieu = maLichChieu;
        this.viTriGhe = viTriGhe;
        this.giaVe = giaVe;
    }
    
    public int getMaChiTietVe() {
        return maChiTietVe;
    }
    
    public void setMaChiTietVe(int maChiTietVe) {
        this.maChiTietVe = maChiTietVe;
    }
    
    public int getMaVe() {
        return maVe;
    }
    
    public void setMaVe(int maVe) {
        this.maVe = maVe;
    }
    
    public int getMaPhim() {
        return maPhim;
    }
    
    public void setMaPhim(int maPhim) {
        this.maPhim = maPhim;
    }
    
    public int getMaPhongChieu() {
        return maPhongChieu;
    }
    
    public void setMaPhongChieu(int maPhongChieu) {
        this.maPhongChieu = maPhongChieu;
    }
    
    public int getMaLichChieu() {
        return maLichChieu;
    }
    
    public void setMaLichChieu(int maLichChieu) {
        this.maLichChieu = maLichChieu;
    }
    
    public String getViTriGhe() {
        return viTriGhe;
    }
    
    public void setViTriGhe(String viTriGhe) {
        this.viTriGhe = viTriGhe;
    }
    
    public double getGiaVe() {
        return giaVe;
    }
    
    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }
    
    @Override
    public String toString() {
        return "ChiTietVe [maChiTietVe=" + maChiTietVe + ", phim=" + maPhim + 
               ", phongChieu=" + maPhongChieu + ", ghe=" + viTriGhe + 
               ", giaVe=" + giaVe + "]";
    }
}
