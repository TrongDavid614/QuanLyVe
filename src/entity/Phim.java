package entity;

public class Phim {
    private String maPhim;
    private String tenPhim;
    private String theLoai;
    private String tomTat;
    private int thoiLuong;
    private NhaSanXuat nhaSanXuat;

    public Phim(String maPhim, String tenPhim, String theLoai, String tomTat, int thoiLuong, NhaSanXuat nhaSanXuat) {
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.theLoai = theLoai;
        this.tomTat = tomTat;
        this.thoiLuong = thoiLuong;
        this.nhaSanXuat = nhaSanXuat;
    }

    public Phim(String maPhim){
        this.maPhim = maPhim;
    }

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }

    public String getTenPhim() {
        return tenPhim;
    }

    public void setTenPhim(String tenPhim) {
        this.tenPhim = tenPhim;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getTomTat() {
        return tomTat;
    }

    public void setTomTat(String tomTat) {
        this.tomTat = tomTat;
    }

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public NhaSanXuat getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(NhaSanXuat nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    @Override
    public String toString() {
        return "Phim{" +
                "maPhim='" + maPhim + '\'' +
                ", tenPhim='" + tenPhim + '\'' +
                ", theLoai='" + theLoai + '\'' +
                ", tomTat='" + tomTat + '\'' +
                ", thoiLuong=" + thoiLuong +
                ", nhaSanXuat=" + nhaSanXuat +
                '}';
    }
}
