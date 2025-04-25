package dao;

import connectSQL.ConnectSQL;
import entity.Phim;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Phim_Dao {
    public ArrayList<Phim> dsp;
    public Phim_Dao() {
        dsp = new ArrayList<>();
    }

    public boolean themPhim(Phim phim) {
        String sql = "INSERT INTO phim (maPhim, tenPhim, theLoai, thoiLuong, daoDien, namSanXuat, quocGia, moTa, posterPath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi thêm phim: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Phim> layTatCaPhim() {
        try{
            Connection connection = connectSQL.ConnectSQL.getConnection();
            String sql = "SELECT * FROM phim";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String maPhim = resultSet.getString("maPhim");
                String tenPhim = resultSet.getString("tenPhim");
                String theLoai = resultSet.getString("theLoai");
                int thoiLuong = resultSet.getInt("thoiLuong");
                String daoDien = resultSet.getString("daoDien");
                int namSanXuat = resultSet.getInt("namSanXuat");
                String quocGia = resultSet.getString("quocGia");
                String moTa = resultSet.getString("moTa");
                String posterPath = resultSet.getString("posterPath");
                Phim phim = new Phim(maPhim, tenPhim, theLoai, thoiLuong, daoDien, namSanXuat, quocGia, moTa, posterPath);
                dsp.add(phim);
            }


        } catch (SQLException e) {
            System.err.println("Lỗi lấy tất cả phim: " + e.getMessage());
            return null;
        }
        return dsp;
    }

    // Phương thức lấy thông tin một phim theo mã phim
//    public Phim layPhimTheoMa(String maPhim) {
//        Phim phim = null;
//        String sql = "SELECT * FROM phim WHERE maPhim = ?";
//        try (Connection connection = ConnectSQL.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, maPhim);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    phim = new Phim();
//                    phim.setMaPhim(resultSet.getString("maPhim"));
//                    phim.setTenPhim(resultSet.getString("tenPhim"));
//                    phim.setTheLoai(resultSet.getString("theLoai"));
//                    phim.setThoiLuong(resultSet.getInt("thoiLuong"));
//                    phim.setDaoDien(resultSet.getString("daoDien"));
//                    phim.setNamSanXuat(resultSet.getInt("namSanXuat"));
//                    phim.setQuocGia(resultSet.getString("quocGia"));
//                    phim.setMoTa(resultSet.getString("moTa"));
//                    phim.setPosterPath(resultSet.getString("posterPath"));
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Lỗi lấy phim theo mã: " + e.getMessage());
//        }
//        return phim;
//    }

    // Phương thức cập nhật thông tin một phim
    public boolean capNhatPhim(Phim phim) {
        String sql = "UPDATE phim SET tenPhim = ?, theLoai = ?, thoiLuong = ?, daoDien = ?, namSanXuat = ?, quocGia = ?, moTa = ?, posterPath = ? WHERE maPhim = ?";
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
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật phim: " + e.getMessage());
            return false;
        }
    }

    // Phương thức xóa một phim theo mã phim
    public boolean xoaPhim(String maPhim) {
        String sql = "DELETE FROM phim WHERE maPhim = ?";
        try (Connection connection = ConnectSQL.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, maPhim);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi xóa phim: " + e.getMessage());
            return false;
        }
    }
}