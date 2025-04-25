package dao;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectSQL.ConnectSQL;
import entity.ThongKe;

public class ThongKe_Dao {
    private Connection conn;

    public ThongKe_Dao() {
        conn = ConnectSQL.getInstance().getConnection();
    }

    public List<ThongKe> thongKeTheoNgay(LocalDate ngay) {
        List<ThongKe> ds = new ArrayList<>();
        String sql = "SELECT p.tenPhim, km.tenKhuyenMai, " +
                     "SUM(hd.tongTienVe) as doanhThuVe, " +
                     "SUM(hd.tongTienKhuyenMai) as doanhThuKhuyenMai, " +
                     "SUM(hd.tienBap) as doanhThuBap, " +
                     "SUM(hd.tienNuoc) as doanhThuNuoc, " +
                     "SUM(hd.tienBanh) as doanhThuBanh, " +
                     "SUM(hd.tienCombo) as doanhThuCombo " +
                     "FROM HoaDon hd " +
                     "JOIN Phim p ON hd.maPhim = p.maPhim " +
                     "LEFT JOIN KhuyenMai km ON hd.maKM = km.maKM " +
                     "WHERE CAST(hd.ngayLap AS DATE) = ? " +
                     "GROUP BY p.tenPhim, km.tenKhuyenMai";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(ngay));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ThongKe tk = new ThongKe(
                    ngay,
                    rs.getString("tenPhim"),
                    rs.getString("tenKhuyenMai"),
                    rs.getDouble("doanhThuVe"),
                    rs.getDouble("doanhThuKhuyenMai"),
                    rs.getDouble("doanhThuBap"),
                    rs.getDouble("doanhThuNuoc"),
                    rs.getDouble("doanhThuBanh"),
                    rs.getDouble("doanhThuCombo")
                );
                ds.add(tk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds;
    }
}
