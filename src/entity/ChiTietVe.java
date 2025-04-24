package entity;

public class ChiTietVe {
    
    private int maChiTietVe;
    private Ve ve;
    private Ghe ghe;
    private double giaVe;       
    
    public ChiTietVe() {
    }

    public ChiTietVe(Ve ve, Ghe ghe, double giaVe) {
        this.ve = ve;
        this.ghe = ghe;
        this.giaVe = giaVe;
    }
    
    public ChiTietVe(int maChiTietVe, Ve ve, Ghe ghe, double giaVe) {
        this.maChiTietVe = maChiTietVe;
        this.ve = ve;
        this.ghe = ghe;
        this.giaVe = giaVe;
    }

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

    public double getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }
    
    @Override
    public String toString() {
        return "ChiTietVe [" +
               "maChiTietVe=" + maChiTietVe + 
               ", ve=" + (ve != null ? ve.getMaVe() : "null") + 
               ", ghe=" + (ghe != null ? ghe.getTenGhe() : "null") +
               ", giaVe=" + giaVe +
               "]";
    }
}
