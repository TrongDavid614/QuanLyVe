package dao;

import connectSQL.ConnectSQL;
import entity.DoAn;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoAn_Dao {
        public ArrayList<DoAn> dsda;
        public DoAn_Dao (){
            dsda = new ArrayList<>();
        }
    public List<DoAn> layTatCaDoAn() {
        List<DoAn> dsDoAn = new ArrayList<>();
        String sql = "SELECT DISTINCT maDoAn, tenDoAn, gia, moTa, hinhAnh FROM doan";
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String maDoAn = rs.getString("maDoAn") != null ? rs.getString("maDoAn") : "";
                String tenDoAn = rs.getString("tenDoAn") != null ? rs.getString("tenDoAn") : "";
                double gia = rs.getDouble("gia");
                String moTa = rs.getString("moTa") != null ? rs.getString("moTa") : "";
                String hinhAnh = rs.getString("hinhAnh") != null ? rs.getString("hinhAnh") : "";
                DoAn doAn = new DoAn(maDoAn, tenDoAn, gia, moTa, hinhAnh);
                dsDoAn.add(doAn);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách đồ ăn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return dsDoAn;
    }
        public boolean isExits(String maDoAn) {
            try{
                Connection con = connectSQL.ConnectSQL.getInstance().getConnection();
                String sql = "select count(*) from DoAn where maDoAn = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, maDoAn);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
        public boolean createDoAn(DoAn da) {
            try{
                Connection con = connectSQL.ConnectSQL.getInstance().getConnection();
                String sql = "INSERT INTO doan (maDoAn, tenDoAn, gia, moTa, hinhAnh) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, da.getMaDoAn());
                stmt.setString(2, da.getTenDoAn());
                stmt.setDouble(3, da.getGiaDoAn());
                stmt.setString(4, da.getMoTa());
                stmt.setString(5, da.getHinhAnh());
                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public boolean deleteDoAn(String maDoAn) {
            try{
                Connection con = connectSQL.ConnectSQL.getInstance().getConnection();
                String sql = "DELETE FROM doan WHERE maDoAn = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, maDoAn);
                int rowsDeleted = stmt.executeUpdate();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public boolean updateDoAn(DoAn da) {
            try{
                Connection con = connectSQL.ConnectSQL.getInstance().getConnection();
                String sql = "UPDATE doan SET tenDoAn = ?, gia = ?, moTa = ?, hinhAnh = ? WHERE maDoAn = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, da.getTenDoAn());
                stmt.setDouble(2, da.getGiaDoAn());
                stmt.setString(3, da.getMoTa());
                stmt.setString(4, da.getHinhAnh());
                stmt.setString(5, da.getMaDoAn());
                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
}
