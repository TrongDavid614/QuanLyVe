package dao;

import connectSQL.ConnectSQL;
import entity.KhachHang;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class KhachHang_Dao {
    public ArrayList<KhachHang> dskh;
    public KhachHang_Dao() {
        dskh = new ArrayList<>();
    }
    public ArrayList<KhachHang> getalltbkhachhang() {
        try{
            Connection con = ConnectSQL.getConnection();
            String sql = "SELECT * FROM khachhang";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String makh = rs.getString("maKH");
                String tenkh = rs.getString("tenKH");
                String diachi = rs.getString("diaChi");
                LocalDate ngaysinh = rs.getDate("ngaySinh").toLocalDate();
                String gioitinh = rs.getString("gioiTinh");
                String sdt = rs.getString("soDienThoai");
                KhachHang kh = new KhachHang(makh,tenkh,ngaysinh, diachi,gioitinh, sdt);
                dskh.add(kh);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dskh;
    }
    public boolean isExits(String makh) {
        try{
            Connection con = ConnectSQL.getInstance().getConnection();
            String sql = "select count(*) from KhachHang where maKH = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, makh);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean createKH(KhachHang kh){
        Connection con = ConnectSQL.getInstance().getConnection();
        int n=0;
        try {
            String sql = "INSERT INTO KhachHang (maKH, tenKH, ngaySinh, diaChi,gioiTinh, soDienThoai) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, kh.getMaKhachHang());
            stmt.setString(2, kh.getTenKhachHang());
            stmt.setDate(3, Date.valueOf(kh.getNgaySinh()));
            stmt.setString(4, kh.getDiaChi());
            stmt.setString(5, kh.getGioiTinh());
            stmt.setString(6, kh.getSoDienThoai());
            n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean delete(String maKH){
        Connection con = ConnectSQL.getInstance().getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try{
            String sql = "DELETE FROM KhachHang WHERE maKH = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maKH);
            n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
        public boolean updateKH(KhachHang kh){
            Connection con = ConnectSQL.getInstance().getConnection();
            int n=0;
            try {
                String sql = "UPDATE KhachHang SET tenKH = ?, ngaySinh = ?, diaChi = ?, gioiTinh = ?, soDienThoai = ? WHERE maKH = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, kh.getTenKhachHang());
                stmt.setDate(2, Date.valueOf(kh.getNgaySinh()));
                stmt.setString(3, kh.getDiaChi());
                stmt.setString(4, kh.getGioiTinh());
                stmt.setString(5, kh.getSoDienThoai());
                stmt.setString(6, kh.getMaKhachHang());
                n = stmt.executeUpdate();
                return n > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


    }
    public KhachHang getKhachHangBySoDienThoai(String soDienThoai) {
        String sql = "SELECT * FROM KhachHang WHERE soDienThoai = ?";
        try (Connection conn = ConnectSQL.getInstance().getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, soDienThoai);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String maKH = rs.getString("maKH");
                    String tenKH = rs.getString("tenKH");
                    LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                    String diaChi = rs.getString("diaChi");
                    String gioiTinh = rs.getString("gioiTinh");
                    KhachHang khachHang = new KhachHang(maKH, tenKH, ngaySinh, diaChi, gioiTinh, rs.getString("soDienThoai"));
                    return khachHang;
                } else {
                    System.out.println("Không tìm thấy khách hàng với số điện thoại: " + soDienThoai);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi truy vấn cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
