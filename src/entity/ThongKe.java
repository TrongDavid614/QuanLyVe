package entity;

import java.time.LocalDate;

public class ThongKe {
    private LocalDate ngay;
    private String tenPhim;
    private String tenKhuyenMai;
    private double doanhThuVe;
    private double doanhThuKhuyenMai;
    private double doanhThuBap;
    private double doanhThuNuoc;
    private double doanhThuBanh;
    private double doanhThuCombo;

    public ThongKe(LocalDate ngay, String tenPhim, String tenKhuyenMai,
                   double doanhThuVe, double doanhThuKhuyenMai,
                   double doanhThuBap, double doanhThuNuoc,
                   double doanhThuBanh, double doanhThuCombo) {
        this.ngay = ngay;
        this.tenPhim = tenPhim;
        this.tenKhuyenMai = tenKhuyenMai;
        this.doanhThuVe = doanhThuVe;
        this.doanhThuKhuyenMai = doanhThuKhuyenMai;
        this.doanhThuBap = doanhThuBap;
        this.doanhThuNuoc = doanhThuNuoc;
        this.doanhThuBanh = doanhThuBanh;
        this.doanhThuCombo = doanhThuCombo;
    }

	public LocalDate getNgay() {
		return ngay;
	}

	public void setNgay(LocalDate ngay) {
		this.ngay = ngay;
	}

	public String getTenPhim() {
		return tenPhim;
	}

	public void setTenPhim(String tenPhim) {
		this.tenPhim = tenPhim;
	}

	public String getTenKhuyenMai() {
		return tenKhuyenMai;
	}

	public void setTenKhuyenMai(String tenKhuyenMai) {
		this.tenKhuyenMai = tenKhuyenMai;
	}

	public double getDoanhThuVe() {
		return doanhThuVe;
	}

	public void setDoanhThuVe(double doanhThuVe) {
		this.doanhThuVe = doanhThuVe;
	}

	public double getDoanhThuKhuyenMai() {
		return doanhThuKhuyenMai;
	}

	public void setDoanhThuKhuyenMai(double doanhThuKhuyenMai) {
		this.doanhThuKhuyenMai = doanhThuKhuyenMai;
	}

	public double getDoanhThuBap() {
		return doanhThuBap;
	}

	public void setDoanhThuBap(double doanhThuBap) {
		this.doanhThuBap = doanhThuBap;
	}

	public double getDoanhThuNuoc() {
		return doanhThuNuoc;
	}

	public void setDoanhThuNuoc(double doanhThuNuoc) {
		this.doanhThuNuoc = doanhThuNuoc;
	}

	public double getDoanhThuBanh() {
		return doanhThuBanh;
	}

	public void setDoanhThuBanh(double doanhThuBanh) {
		this.doanhThuBanh = doanhThuBanh;
	}

	public double getDoanhThuCombo() {
		return doanhThuCombo;
	}

	public void setDoanhThuCombo(double doanhThuCombo) {
		this.doanhThuCombo = doanhThuCombo;
	}

  
}
