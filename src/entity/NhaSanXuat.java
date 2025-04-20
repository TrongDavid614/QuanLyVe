package entity;

public class NhaSanXuat {
    private String maNhaSanXuat;
    private String tenNhaSanXuat;
    private String quocGia;
    private String diaChi;

    public NhaSanXuat(String maNhaSanXuat, String tenNhaSanXuat, String quocGia, String diaChi) {
        this.maNhaSanXuat = maNhaSanXuat;
        this.tenNhaSanXuat = tenNhaSanXuat;
        this.quocGia = quocGia;
        this.diaChi = diaChi;
    }

    public NhaSanXuat(String maNhaSanXuat) {
        this.maNhaSanXuat = maNhaSanXuat;
    }

    public String getMaNhaSanXuat() {
        return maNhaSanXuat;
    }

    public void setMaNhaSanXuat(String maNhaSanXuat) {
        this.maNhaSanXuat = maNhaSanXuat;
    }

    public String getTenNhaSanXuat() {
        return tenNhaSanXuat;
    }
    public void setTenNhaSanXuat(String tenNhaSanXuat) {
        this.tenNhaSanXuat = tenNhaSanXuat;
    }

    public String getQuocGia() {
        return quocGia;
    }
    public void setQuocGia(String quocGia) {
        this.quocGia = quocGia;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return "NhaSanXuat{" +
                "maNhaSanXuat='" + maNhaSanXuat + '\'' +
                ", tenNhaSanXuat='" + tenNhaSanXuat + '\'' +
                ", quocGia='" + quocGia + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}
