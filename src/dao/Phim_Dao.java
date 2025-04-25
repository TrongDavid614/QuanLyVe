package dao;

import connectSQL.ConnectSQL;
import entity.Phim;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Phim_Dao {
    public boolean themPhim(Phim phim) {
        if (phim == null || phim.getMaPhim() == null || phim.getTenPhim() == null) {
            JOptionPane.showMessageDialog(null, "Dữ liệu phim không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO phim (maPhim, tenPhim, theLoai, thoiLuong, daoDien, namSanXuat, quocGia, moTa, poster) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, phim.getMaPhim());
            preparedStatement.setString(2, phim.getTenPhim());
            preparedStatement.setString(3, phim.getTheLoai());
            preparedStatement.setInt(4, phim.getThoiLuong());
            preparedStatement.setString(5, phim.getDaoDien());
            preparedStatement.setInt(6, phim.getNamSanXuat());
            preparedStatement.setString(7, phim.getQuocGia());
            preparedStatement.setString(8, phim.getMoTa());
            preparedStatement.setString(9, phim.getPosterPath());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Thêm phim thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi thêm phim: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean isExist(String maPhim) {
        if (maPhim == null || maPhim.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT * FROM phim WHERE maPhim = ?";
        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, maPhim);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi kiểm tra phim tồn tại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<Phim> layTatCaPhim() {
        List<Phim> dsPhim = new ArrayList<>();
        String sql = "SELECT DISTINCT maPhim, tenPhim, theLoai, thoiLuong, daoDien, namSanXuat, quocGia, moTa, poster FROM Phim";
        try (Connection conn = ConnectSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String maPhim = rs.getString("maPhim") != null ? rs.getString("maPhim") : "";
                String tenPhim = rs.getString("tenPhim") != null ? rs.getString("tenPhim") : "";
                String theLoai = rs.getString("theLoai") != null ? rs.getString("theLoai") : "";
                int thoiLuong = rs.getInt("thoiLuong");
                String daoDien = rs.getString("daoDien") != null ? rs.getString("daoDien") : "";
                int namSanXuat = rs.getInt("namSanXuat");
                String quocGia = rs.getString("quocGia") != null ? rs.getString("quocGia") : "";
                String moTa = rs.getString("moTa") != null ? rs.getString("moTa") : "";
                String poster = rs.getString("poster") != null ? rs.getString("poster") : "";
                Phim phim = new Phim(maPhim, tenPhim, theLoai, thoiLuong, daoDien, namSanXuat, quocGia, moTa, poster);
                dsPhim.add(phim);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách phim: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return dsPhim;
    }

    public Phim layPhimTheoMa(String maPhim) {
        if (maPhim == null || maPhim.trim().isEmpty()) {
            return null;
        }

        Phim phim = null;
        String sql = "SELECT * FROM phim WHERE maPhim = ?";
        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maPhim);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String tenPhim = rs.getString("tenPhim") != null ? rs.getString("tenPhim") : "";
                String theLoai = rs.getString("theLoai") != null ? rs.getString("theLoai") : "";
                int thoiLuong = rs.getInt("thoiLuong");
                String daoDien = rs.getString("daoDien") != null ? rs.getString("daoDien") : "";
                int namSanXuat = rs.getInt("namSanXuat");
                String quocGia = rs.getString("quocGia") != null ? rs.getString("quocGia") : "";
                String moTa = rs.getString("moTa") != null ? rs.getString("moTa") : "";
                String poster = rs.getString("poster") != null ? rs.getString("poster") : "";
                phim = new Phim(maPhim, tenPhim, theLoai, thoiLuong, daoDien, namSanXuat, quocGia, moTa, poster);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi lấy phim theo mã: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return phim;
    }

    public boolean capNhatPhim(Phim phim) {
        if (phim == null || phim.getMaPhim() == null || phim.getTenPhim() == null) {
            JOptionPane.showMessageDialog(null, "Dữ liệu phim không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "UPDATE phim SET tenPhim = ?, theLoai = ?, thoiLuong = ?, daoDien = ?, namSanXuat = ?, quocGia = ?, moTa = ?, poster = ? WHERE maPhim = ?";
        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, phim.getTenPhim());
            preparedStatement.setString(2, phim.getTheLoai());
            preparedStatement.setInt(3, phim.getThoiLuong());
            preparedStatement.setString(4, phim.getDaoDien());
            preparedStatement.setInt(5, phim.getNamSanXuat());
            preparedStatement.setString(6, phim.getQuocGia());
            preparedStatement.setString(7, phim.getMoTa());
            preparedStatement.setString(8, phim.getPosterPath());
            preparedStatement.setString(9, phim.getMaPhim());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật phim thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi cập nhật phim: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean xoaPhim(String maPhim) {
        if (maPhim == null || maPhim.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Mã phim không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "DELETE FROM phim WHERE maPhim = ?";
        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, maPhim);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Xóa phim thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi xóa phim: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}