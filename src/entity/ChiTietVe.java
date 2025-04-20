package entity;

public class ChiTietVe {
    private int maChiTietVe;
    private Ve ve;
    private Ghe ghe;
    
    // Constructors
    public ChiTietVe() {
    }
    
    public ChiTietVe(Ve ve, Ghe ghe) {
        this.ve = ve;
        this.ghe = ghe;
    }
    
    public ChiTietVe(int maChiTietVe, Ve ve, Ghe ghe) {
        this.maChiTietVe = maChiTietVe;
        this.ve = ve;
        this.ghe = ghe;
    }
    
    // Getters and Setters
    public int getMaChiTietVe() {
        return maChiTietVe;
    }
    
    public void setMaChiTietVe(int maChiTietVe) {
        this.maChiTietVe = maChiTietVe;
    }
    
    public Ve getVe() {
        return ve;
    }
    
    public void setVe(Ve ve) {
        this.ve = ve;
    }
    
    public Ghe getGhe() {
        return ghe;
    }
    
    public void setGhe(Ghe ghe) {
        this.ghe = ghe;
    }
    
    @Override
    public String toString() {
        return "ChiTietVe [maChiTietVe=" + maChiTietVe + 
               ", ve=" + (ve != null ? ve.getMaVe() : "null") + 
               ", ghe=" + (ghe != null ? ghe.getTenGhe() : "null") + "]";
    }
}
