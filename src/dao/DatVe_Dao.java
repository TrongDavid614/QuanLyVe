package dao;

import entity.*;
import connectSQL.ConnectSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatVe_Dao {
    private Connection con;
    private Ve_Dao veDao;
    private KhachHang_Dao khachHangDao;
    private Phim_Dao phimDao;
    private PhongChieu_Dao phongChieuDao;

    public DatVe_Dao() {
        con = ConnectSQL.getInstance().getConnection();
        veDao = new Ve_Dao();
        khachHangDao = new KhachHang_Dao();
        phimDao = new Phim_Dao();
        phongChieuDao = new PhongChieu_Dao();
    }

    public boolean datNhieuVe(DatVe datVe) throws SQLException {
        if (datVe.getDanhSachGhe() == null || datVe.getDanhSachGhe().isEmpty()) {
            return false;
        }

        boolean datVeThanhCong = true;
        con.setAutoCommit(false); 
        try {
            for (Integer maGhe : datVe.getDanhSachGhe()) {
                Ve ve = new Ve();
                ve.setMaVe(taoMaVeTuDong()); // Tạo mã vé tự động
                ve.setKhachHang(datVe.getKhachHang());
                ve.setPhim(datVe.getPhim());
                ve.setPhongChieu(datVe.getPhongChieu());
                ve.setNgayDat(datVe.getNgayDat());
                ve.setGioChieu(new Timestamp(datVe.getGioChieu().getTime()));
                ve.setTongTien(datVe.getTongTien() / datVe.getDanhSachGhe().size()); 
                if (!veDao.themVe(ve)) {
                    datVeThanhCong = false;
                    break;
                }

                if (!capNhatTrangThaiGhe(datVe.getPhim().getMaPhim(), datVe.getPhongChieu().getMaPhong(), datVe.getGioChieu(), maGhe, true)) {
                    datVeThanhCong = false;
                    break;
                }
            }

            if (datVeThanhCong) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }

        return datVeThanhCong;
    }
    private int taoMaVeTuDong() {
        return (int) System.currentTimeMillis();
    }

    public boolean kiemTraGheDaDat(String maPhim, int maPhong, Date gioChieu, int maGhe) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Ve v " +
                     "JOIN LichChieu lc ON v.maPhim = lc.maPhim AND v.maPhong = lc.maPhong AND v.gioChieu = lc.gioChieu " +
                     "WHERE v.maPhim = ? AND v.maPhong = ? AND v.gioChieu = ? AND lc.maGhe = ?"; // Giả sử có liên kết với bảng lịch chiếu và ghế
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maPhim);
            stmt.setInt(2, maPhong);
            stmt.setTimestamp(3, new Timestamp(gioChieu.getTime()));
            stmt.setInt(4, maGhe);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public boolean capNhatTrangThaiGhe(String maPhim, int maPhong, Date gioChieu, int maGhe, boolean daDat) throws SQLException {
        String sql = "UPDATE LichChieu_Ghe SET daDat = ? WHERE maPhim = ? AND maPhong = ? AND gioChieu = ? AND maGhe = ?"; // Giả sử có bảng LichChieu_Ghe
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setBoolean(1, daDat);
            stmt.setString(2, maPhim);
            stmt.setInt(3, maPhong);
            stmt.setTimestamp(4, new Timestamp(gioChieu.getTime()));
            stmt.setInt(5, maGhe);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Integer> layDanhSachGheDaDat(String maPhim, int maPhong, Date gioChieu) throws SQLException {
        List<Integer> danhSachGheDaDat = new ArrayList<>();
        String sql = "SELECT lg.maGhe FROM Ve v " +
                     "JOIN LichChieu lc ON v.maPhim = lc.maPhim AND v.maPhong = lc.maPhong AND v.gioChieu = lc.gioChieu " +
                     "JOIN LichChieu_Ghe lg ON lc.maPhim = lg.maPhim AND lc.maPhong = lg.maPhong AND lc.gioChieu = lg.gioChieu AND v.maVe = lg.maVe " + // Cần xem xét lại join này
                     "WHERE v.maPhim = ? AND v.maPhong = ? AND v.gioChieu = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maPhim);
            stmt.setInt(2, maPhong);
            stmt.setTimestamp(3, new Timestamp(gioChieu.getTime()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                danhSachGheDaDat.add(rs.getInt("maGhe"));
            }
            return danhSachGheDaDat;
        }
    }
}