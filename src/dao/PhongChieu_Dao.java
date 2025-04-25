package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectSQL.ConnectSQL;
import entity.Ghe;
import entity.PhongChieu;

public class PhongChieu_Dao {

    public List<PhongChieu> getAllPhongChieu() {
        List<PhongChieu> ds = new ArrayList<>();
        String sql = "SELECT * FROM PhongChieu";

        try (Connection con = ConnectSQL.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int maPhong = rs.getInt("maPhong");
                String tenPhong = rs.getString("tenPhong");
                int soGhe = rs.getInt("soGhe");

                PhongChieu pc = new PhongChieu(maPhong, tenPhong, soGhe);
                ds.add(pc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }

    public boolean themPhongChieu(PhongChieu pc) {
        String sql = "INSERT INTO PhongChieu(tenPhong, soGhe) VALUES (?, ?)";

        try (Connection con = ConnectSQL.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pc.getTenPhong());
            stmt.setInt(2, pc.getSoGhe());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatPhongChieu(PhongChieu pc) {
        String sql = "UPDATE PhongChieu SET tenPhong = ?, soGhe = ? WHERE maPhong = ?";

        try (Connection con = ConnectSQL.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pc.getTenPhong());
            stmt.setInt(2, pc.getSoGhe());
            stmt.setInt(3, pc.getMaPhong());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaPhongChieu(int maPhong) {
        String sql = "DELETE FROM PhongChieu WHERE maPhong = ?";

        try (Connection con = ConnectSQL.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, maPhong);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PhongChieu timPhongChieuTheoMa(int maPhong) {
        String sql = "SELECT * FROM PhongChieu WHERE maPhong = ?";

        try (Connection con = ConnectSQL.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, maPhong);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String tenPhong = rs.getString("tenPhong");
                    int soGhe = rs.getInt("soGhe");

                    return new PhongChieu(maPhong, tenPhong, soGhe);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean kiemTraTonTaiMaPhong(int maPhong) {
        String sql = "SELECT COUNT(*) FROM PhongChieu WHERE maPhong = ?";

        try (Connection con = ConnectSQL.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, maPhong);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
