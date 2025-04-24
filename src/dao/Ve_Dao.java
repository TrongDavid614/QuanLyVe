package dao;

import entity.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import connectSQL.ConnectSQL;

public class Ve_Dao {
    private Connection con;

    public Ve_Dao() {
        con = ConnectSQL.getInstance().getConnection();
    }

    // Thêm vé mới
    public boolean themVe(Ve ve) throws SQLException {
        String sql = "INSERT INTO Ve (maVe, maKH, maPhim, maPhong, ngayDat, gioChieu, tongTien) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, ve.getMaVe());
            stmt.setString(2, ve.getKhachHang().getMaKhachHang());
            stmt.setString(3, ve.getPhim().getMaPhim());
            stmt.setInt(4, ve.getPhongChieu().getMaPhong());
            stmt.setDate(5, new java.sql.Date(ve.getNgayDat().getTime()));
            stmt.setTimestamp(6, new java.sql.Timestamp(ve.getGioChieu().getTime()));
            stmt.setDouble(7, ve.getTongTien());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Ve> getAllVe() throws SQLException {
        List<Ve> dsVe = new ArrayList<>();
        String sql = "SELECT v.*, kh.tenKH, p.tenPhim FROM Ve v " +
                    "JOIN KhachHang kh ON v.maKH = kh.maKH " +
                    "JOIN Phim p ON v.maPhim = p.maPhim " +
                    "ORDER BY v.maVe";
                    
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ve ve = chuyenResultSetSangVe(rs);
                dsVe.add(ve);
            }
        }
        return dsVe;
    }

    public boolean capNhatVe(Ve ve) throws SQLException {
        String sql = "UPDATE Ve SET maKH=?, maPhim=?, maPhong=?, ngayDat=?, " +
                    "gioChieu=?, tongTien=?, phuongThucThanhToan=?, maGiaoDich=? WHERE maVe=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, ve.getKhachHang().getMaKhachHang());
            stmt.setString(2, ve.getPhim().getMaPhim());
            stmt.setInt(3, ve.getPhongChieu().getMaPhong());
            stmt.setDate(4, new java.sql.Date(ve.getNgayDat().getTime()));
            stmt.setTimestamp(5, new java.sql.Timestamp(ve.getGioChieu().getTime()));
            stmt.setDouble(6, ve.getTongTien());
            stmt.setString(7, ve.getPhuongThucThanhToan());
            stmt.setString(8, ve.getMaGiaoDich());
            stmt.setInt(9, ve.getMaVe());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean huyVe(int maVe) throws SQLException {
        if (kiemTraVeDaThanhToan(maVe)) {
            return false;
        }

        con.setAutoCommit(false);
        try {
            String sql = "DELETE FROM Ve WHERE maVe = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, maVe);
                boolean result = stmt.executeUpdate() > 0;
                con.commit();
                return result;
            }
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public boolean thanhToanVe(int maVe, String phuongThuc, String maGiaoDich) throws SQLException {
        String sql = "UPDATE Ve SET phuongThucThanhToan=?, ngayThanhToan=?, maGiaoDich=? WHERE maVe=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, phuongThuc);
            stmt.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            stmt.setString(3, maGiaoDich);
            stmt.setInt(4, maVe);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Ve> timKiemVe(String keyword) throws SQLException {
        String sql = "SELECT v.*, kh.tenKH, p.tenPhim FROM Ve v " +
                    "JOIN KhachHang kh ON v.maKH = kh.maKH " +
                    "JOIN Phim p ON v.maPhim = p.maPhim " +
                    "WHERE v.maVe = ? OR kh.tenKH = ? OR p.tenPhim = ? OR " +
                    "v.phuongThucThanhToan = ? OR v.maGiaoDich = ?";
                    
        List<Ve> dsVe = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (int i = 1; i <= 5; i++) {
                stmt.setString(i, keyword);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dsVe.add(chuyenResultSetSangVe(rs));
                }
            }
        }
        return dsVe;
    }

    private boolean kiemTraVeDaThanhToan(int maVe) throws SQLException {
        String sql = "SELECT ngayThanhToan FROM Ve WHERE maVe = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, maVe);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getTimestamp("ngayThanhToan") != null;
            }
        }
    }

    private Ve chuyenResultSetSangVe(ResultSet rs) throws SQLException {
        Ve ve = new Ve();
        ve.setMaVe(rs.getInt("maVe"));
        
        KhachHang kh = new KhachHang(rs.getString("maKH"));
        kh.setTenKhachHang(rs.getString("tenKH"));
        ve.setKhachHang(kh);
        
        Phim phim = new Phim(rs.getString("maPhim"));
        phim.setTenPhim(rs.getString("tenPhim"));
        ve.setPhim(phim);
        
        PhongChieu phong = new PhongChieu();
        phong.setMaPhong(rs.getInt("maPhong"));
        ve.setPhongChieu(phong);
        
        ve.setNgayDat(rs.getDate("ngayDat"));
        ve.setGioChieu(rs.getTimestamp("gioChieu"));
        ve.setTongTien(rs.getDouble("tongTien"));
        ve.setPhuongThucThanhToan(rs.getString("phuongThucThanhToan"));
        ve.setNgayThanhToan(rs.getTimestamp("ngayThanhToan"));
        ve.setMaGiaoDich(rs.getString("maGiaoDich"));
        
        return ve;
    }
}
