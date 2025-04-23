package entity;

public class ChiTietVe {
    public enum TrangThaiVe {
        CHO_THANH_TOAN,
        DA_THANH_TOAN,
        DA_HUY
    }

    private int maChiTietVe;
    private Ve ve;
    private Ghe ghe;
    private double giaVe;       
    private TrangThaiVe trangThai;
    
    public ChiTietVe() {
        this.trangThai = TrangThaiVe.CHO_THANH_TOAN;
    }

    public ChiTietVe(Ve ve, Ghe ghe, double giaVe) {
        this();
        this.ve = ve;
        this.ghe = ghe;
        this.giaVe = giaVe;
    }
    
    public ChiTietVe(int maChiTietVe, Ve ve, Ghe ghe, double giaVe, TrangThaiVe trangThai) {
        this.maChiTietVe = maChiTietVe;
        this.ve = ve;
        this.ghe = ghe;
        this.giaVe = giaVe;
        this.trangThai = trangThai;
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

    public TrangThaiVe getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(TrangThaiVe trangThai) {
        this.trangThai = trangThai;
    }
    
    @Override
    public String toString() {
        return "ChiTietVe [" +
               "maChiTietVe=" + maChiTietVe + 
               ", ve=" + (ve != null ? ve.getMaVe() : "null") + 
               ", ghe=" + (ghe != null ? ghe.getTenGhe() : "null") +
               ", giaVe=" + giaVe +
               ", trangThai=" + trangThai + 
               "]";
    }
}
