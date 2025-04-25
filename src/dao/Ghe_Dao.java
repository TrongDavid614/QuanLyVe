package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectSQL.ConnectSQL;
import entity.Ghe;
import entity.Ghe.LoaiGhe;
import entity.Ghe.TrangThaiGhe;
import entity.PhongChieu;

public class Ghe_Dao {

    public List<Ghe> getAllGhe() {
        List<Ghe> danhSachGhe = new ArrayList<>();
        String sql = "SELECT * FROM Ghe";
        try (Connection con = ConnectSQL.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int maGhe = rs.getInt("maGhe");
                String tenGhe = rs.getString("tenGhe");
                String loaiGheStr = rs.getString("loaiGhe");
                int maPhong = rs.getInt("maPhong");
                String trangThaiStr = rs.getString("trangThai");

                LoaiGhe loaiGhe = LoaiGhe.valueOf(loaiGheStr.toUpperCase());
                TrangThaiGhe trangThai = TrangThaiGhe.valueOf(trangThaiStr.toUpperCase());
                PhongChieu phong = new PhongChieu();
                phong.setMaPhong(maPhong);

                Ghe ghe = new Ghe(maGhe, tenGhe, loaiGhe, phong);
                ghe.setTrangThai(trangThai);
                danhSachGhe.add(ghe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachGhe;
    }

    public boolean themGhe(Ghe ghe) {
        String sql = "INSERT INTO Ghe(tenGhe, loaiGhe, maPhong, trangThai) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ghe.getTenGhe());
            stmt.setString(2, ghe.getLoaiGhe().toString());
            stmt.setInt(3, ghe.getPhongChieu().getMaPhong());
            stmt.setString(4, ghe.getTrangThai().toString());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaGhe(int maGhe) {
        String sql = "DELETE FROM Ghe WHERE maGhe = ?";
        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, maGhe);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatGhe(Ghe ghe) {
        String sql = "UPDATE Ghe SET tenGhe = ?, loaiGhe = ?, maPhong = ?, trangThai = ? WHERE maGhe = ?";
        try (Connection con = ConnectSQL.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ghe.getTenGhe());
            stmt.setString(2, ghe.getLoaiGhe().toString());
            stmt.setInt(3, ghe.getPhongChieu().getMaPhong());
            stmt.setString(4, ghe.getTrangThai().toString());
            stmt.setInt(5, ghe.getMaGhe());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
