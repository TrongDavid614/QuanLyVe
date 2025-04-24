package entity;

public class Phim {
    private String maPhim;
    private String tenPhim;
    private String theLoai;
    private int thoiLuong;
    private String daoDien;
    private int namSanXuat;
    private String quocGia;
    private String moTa;
    private String posterPath; // Đường dẫn đến file poster



    public Phim(String maPhim, String tenPhim, String theLoai, int thoiLuong, String daoDien, int namSanXuat, String quocGia, String moTa, String posterPath) {
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.theLoai = theLoai;
        this.thoiLuong = thoiLuong;
        this.daoDien = daoDien;
        this.namSanXuat = namSanXuat;
        this.quocGia = quocGia;
        this.moTa = moTa;
        this.posterPath = posterPath;
    }
    public Phim(String maPhim) {
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

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getDaoDien() {
        return daoDien;
    }

    public void setDaoDien(String daoDien) {
        this.daoDien = daoDien;
    }

    public int getNamSanXuat() {
        return namSanXuat;
    }

    public void setNamSanXuat(int namSanXuat) {
        this.namSanXuat = namSanXuat;
    }

    public String getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "Phim{" +
                "maPhim='" + maPhim + '\'' +
                ", tenPhim='" + tenPhim + '\'' +
                ", theLoai='" + theLoai + '\'' +
                ", thoiLuong='" + thoiLuong + '\'' +
                ", daoDien='" + daoDien + '\'' +
                ", namSanXuat=" + namSanXuat +
                ", quocGia='" + quocGia + '\'' +
                ", moTa='" + moTa + '\'' +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }
}