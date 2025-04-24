package dao;

import connectSQL.ConnectSQL;
import entity.NhanVien;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class NhanVien_Dao {
    public ArrayList<NhanVien> dsnv;

    public NhanVien_Dao() {
        dsnv = new ArrayList<>();
    }

    public ArrayList<NhanVien> getalltbnhanvien() {
        try {
            Connection con = ConnectSQL.getConnection();
            String sql = "SELECT * FROM nhanvien";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                String diaChi = rs.getString("diaChi");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                String gioiTinh = rs.getString("gioiTinh");
                String soDienThoai = rs.getString("soDienThoai");
                double tienLuong = rs.getDouble("tienLuong");

                NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, diaChi, gioiTinh, soDienThoai, tienLuong);
                dsnv.add(nv);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dsnv;
    }

    public boolean isExits(String maNV) {
        try {
            Connection con = ConnectSQL.getInstance().getConnection();
            String sql = "select count(*) from NhanVien where maNV = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean createNhanVien(NhanVien nv) {
        Connection con = ConnectSQL.getInstance().getConnection();
        int n = 0;
        try {
            String sql = "INSERT INTO NhanVien (maNV, tenNV, ngaySinh, diaChi, gioiTinh, soDienThoai, tienLuong) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nv.getMaNhanVien());
            stmt.setString(2, nv.getTenNhanVien());
            stmt.setDate(3, Date.valueOf(nv.getNgaySinh()));
            stmt.setString(4, nv.getDiaChi());
            stmt.setString(5, nv.getGioiTinh());
            stmt.setString(6, nv.getSoDienThoai());
            stmt.setDouble(7, nv.getTienLuong());
            n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(String maNV) {
        Connection con = ConnectSQL.getInstance().getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try {
            String sql = "DELETE FROM NhanVien WHERE maNV = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maNV);
            n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateNhanVien(NhanVien nv) {
        Connection con = ConnectSQL.getInstance().getConnection();
        int n = 0;
        try {
            String sql = "UPDATE NhanVien SET tenNV = ?, ngaySinh = ?, diaChi = ?, gioiTinh = ?, soDienThoai = ?, tienLuong = ? WHERE maNV = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nv.getTenNhanVien());
            stmt.setDate(2, Date.valueOf(nv.getNgaySinh()));
            stmt.setString(3, nv.getDiaChi());
            stmt.setString(4, nv.getGioiTinh());
            stmt.setString(5, nv.getSoDienThoai());
            stmt.setDouble(6, nv.getTienLuong());
            stmt.setString(7, nv.getMaNhanVien());
            n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
