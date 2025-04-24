package entity;

import java.time.LocalDate;

public class NhanVien {
	  private String maNhanVien;
	    private String tenNhanVien;
	    private LocalDate ngaySinh;
	    private String diaChi;
	    private String gioiTinh;
	    private String soDienThoai;
	    private double tienLuong;
	    
	    public NhanVien(String maNhanVien, String tenNhanVien, LocalDate ngaySinh, 
	    				String diaChi, String gioiTinh, String soDienThoai, double tienLuong) {
	    	this.maNhanVien = maNhanVien;
	    	this.tenNhanVien = tenNhanVien;
	    	this.ngaySinh = ngaySinh;
	    	this.diaChi = diaChi;
	    	this.gioiTinh = gioiTinh;
	    	this.soDienThoai = soDienThoai;
	    	this.tienLuong = tienLuong;
	    }

		public String getMaNhanVien() {
			return maNhanVien;
		}

		public void setMaNhanVien(String maNhanVien) {
			this.maNhanVien = maNhanVien;
		}

		public String getTenNhanVien() {
			return tenNhanVien;
		}

		public void setTenNhanVien(String tenNhanVien) {
			this.tenNhanVien = tenNhanVien;
		}

		public LocalDate getNgaySinh() {
			return ngaySinh;
		}

		public void setNgaySinh(LocalDate ngaySinh) {
			this.ngaySinh = ngaySinh;
		}

		public String getDiaChi() {
			return diaChi;
		}

		public void setDiaChi(String diaChi) {
			this.diaChi = diaChi;
		}

		public String getGioiTinh() {
			return gioiTinh;
		}

		public void setGioiTinh(String gioiTinh) {
			this.gioiTinh = gioiTinh;
		}

		public String getSoDienThoai() {
			return soDienThoai;
		}

		public void setSoDienThoai(String soDienThoai) {
			this.soDienThoai = soDienThoai;
		}

		public double getTienLuong() {
			return tienLuong;
		}

		public void setTienLuong(double tienLuong) {
			this.tienLuong = tienLuong;
		}
}
