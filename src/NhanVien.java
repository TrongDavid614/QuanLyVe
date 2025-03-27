
import java.io.Serializable;

public class NhanVien implements Serializable {
    private int maMV;
    private String ho;
    private String ten;
    private boolean phai;
    private int tuoi;
    private double tienLuong;

    public NhanVien(int maMV, String ho, String ten, boolean phai, int tuoi, double tienLuong) {
        this.maMV = maMV;
        this.ho = ho;
        this.ten = ten;
        this.phai = phai;
        this.tuoi = tuoi;
        this.tienLuong = tienLuong;
    }

    public NhanVien(int maMV) {
        this(maMV, "", "", true, 0, 0.0);
    }

    public NhanVien() {
        this(0);
    }

    public int getMaMV() {
        return maMV;
    }

    public void setMaMV(int maMV) {
        this.maMV = maMV;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isPhai() {
        return phai;
    }

    public void setPhai(boolean phai) {
        this.phai = phai;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public double getTienLuong() {
        return tienLuong;
    }

    public void setTienLuong(double tienLuong) {
        this.tienLuong = tienLuong;
    }

    @Override
    public String toString() {
        return maMV + ";" + ho + ";" + ten + ";" + phai + ";" + tuoi + ";" + tienLuong;
    }
}