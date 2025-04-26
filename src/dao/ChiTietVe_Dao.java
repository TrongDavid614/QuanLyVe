package dao;

import entity.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connectSQL.ConnectSQL;

public class ChiTietVe_Dao {
    private Connection con;

    public ChiTietVe_Dao() {
        con = ConnectSQL.getInstance().getConnection();
    }

    public boolean themChiTietVe(ChiTietVe chiTietVe) throws SQLException {
        String sql = "INSERT INTO ChiTietVe (maVe, maGhe, giaVe) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, chiTietVe.getVe().getMaVe());
            stmt.setInt(2, chiTietVe.getGhe().getMaGhe());
            stmt.setDouble(3, chiTietVe.getGiaVe());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        chiTietVe.setMaChiTietVe(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<ChiTietVe> getChiTietVeTheoMaVe(int maVe) throws SQLException {
        List<ChiTietVe> dsChiTietVe = new ArrayList<>();
        String sql = "SELECT ctv.*, g.tenGhe, g.loaiGhe, p.maPhong, p.tenPhong " +
                     "FROM ChiTietVe ctv " +
                     "JOIN Ghe g ON ctv.maGhe = g.maGhe " +
                     "JOIN PhongChieu p ON g.maPhong = p.maPhong " +
                     "WHERE ctv.maVe = ?";
                     
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, maVe);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietVe chiTietVe = new ChiTietVe();
                    chiTietVe.setMaChiTietVe(rs.getInt("maChiTietVe"));
                    
                    Ve ve = new Ve();
                    ve.setMaVe(maVe);
                    chiTietVe.setVe(ve);
                    
                    PhongChieu phongChieu = new PhongChieu();
                    phongChieu.setMaPhong(rs.getInt("maPhong"));
                    phongChieu.setTenPhong(rs.getString("tenPhong"));
                    
                    Ghe ghe = new Ghe();
                    ghe.setMaGhe(rs.getInt("maGhe"));
                    ghe.setTenGhe(rs.getString("tenGhe"));
                    ghe.setLoaiGhe(Ghe.LoaiGhe.valueOf(rs.getString("loaiGhe")));
                    ghe.setPhongChieu(phongChieu);
                    chiTietVe.setGhe(ghe);
                    
                    chiTietVe.setGiaVe(rs.getDouble("giaVe"));
                    dsChiTietVe.add(chiTietVe);
                }
            }
        }
        return dsChiTietVe;
    }

    public List<ChiTietVe> getAllChiTietVe() throws SQLException {
        List<ChiTietVe> dsChiTietVe = new ArrayList<>();
        String sql = "SELECT ctv.*, g.tenGhe, g.loaiGhe, p.maPhong, p.tenPhong " +
                     "FROM ChiTietVe ctv " +
                     "JOIN Ghe g ON ctv.maGhe = g.maGhe " +
                     "JOIN PhongChieu p ON g.maPhong = p.maPhong";
                     
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ChiTietVe chiTietVe = new ChiTietVe();
                chiTietVe.setMaChiTietVe(rs.getInt("maChiTietVe"));
                
                Ve ve = new Ve();
                ve.setMaVe(rs.getInt("maVe"));
                chiTietVe.setVe(ve);
                
                PhongChieu phongChieu = new PhongChieu();
                phongChieu.setMaPhong(rs.getInt("maPhong"));
                phongChieu.setTenPhong(rs.getString("tenPhong"));
                
                Ghe ghe = new Ghe();
                ghe.setMaGhe(rs.getInt("maGhe"));
                ghe.setTenGhe(rs.getString("tenGhe"));
                ghe.setLoaiGhe(Ghe.LoaiGhe.valueOf(rs.getString("loaiGhe")));
                ghe.setPhongChieu(phongChieu);
                chiTietVe.setGhe(ghe);
                
                chiTietVe.setGiaVe(rs.getDouble("giaVe"));
                dsChiTietVe.add(chiTietVe);
            }
        }
        return dsChiTietVe;
    }

    public boolean capNhatChiTietVe(ChiTietVe chiTietVe) throws SQLException {
        String sql = "UPDATE ChiTietVe SET maVe=?, maGhe=?, giaVe=? WHERE maChiTietVe=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, chiTietVe.getVe().getMaVe());
            stmt.setInt(2, chiTietVe.getGhe().getMaGhe());
            stmt.setDouble(3, chiTietVe.getGiaVe());
            stmt.setInt(4, chiTietVe.getMaChiTietVe());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean xoaChiTietVe(int maChiTietVe) throws SQLException {
        String sql = "DELETE FROM ChiTietVe WHERE maChiTietVe=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, maChiTietVe);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean xoaChiTietVeTheoMaVe(int maVe) throws SQLException {
        String sql = "DELETE FROM ChiTietVe WHERE maVe=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, maVe);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean kiemTraGheDaDat(int maGhe, int maPhong, java.util.Date gioChieu) throws SQLException {
        String sql = "SELECT ctv.* FROM ChiTietVe ctv " +
                     "JOIN Ve v ON ctv.maVe = v.maVe " +
                     "JOIN PhongChieu p ON v.maPhong = p.maPhong " +
                     "WHERE ctv.maGhe = ? AND p.maPhong = ? AND v.gioChieu = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, maGhe);
            stmt.setInt(2, maPhong);
            stmt.setTimestamp(3, new java.sql.Timestamp(gioChieu.getTime()));
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Trả về true nếu ghế đã được đặt
            }
        }
    }

    public List<Ghe> getDanhSachGheDaDat(int maLichChieu) throws SQLException {
        List<Ghe> dsGheDaDat = new ArrayList<>();
        String sql = "SELECT g.*, p.maPhong, p.tenPhong FROM Ghe g " +
                     "JOIN PhongChieu p ON g.maPhong = p.maPhong " +
                     "JOIN ChiTietVe ctv ON g.maGhe = ctv.maGhe " +
                     "JOIN Ve v ON ctv.maVe = v.maVe " +
                     "WHERE v.maLichChieu = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, maLichChieu);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Tạo đối tượng PhongChieu
                    PhongChieu phongChieu = new PhongChieu();
                    phongChieu.setMaPhong(rs.getInt("maPhong"));
                    phongChieu.setTenPhong(rs.getString("tenPhong"));
                    
                    // Tạo đối tượng Ghe
                    Ghe ghe = new Ghe();
                    ghe.setMaGhe(rs.getInt("maGhe"));
                    ghe.setTenGhe(rs.getString("tenGhe"));
                    ghe.setLoaiGhe(Ghe.LoaiGhe.valueOf(rs.getString("loaiGhe")));
                    ghe.setPhongChieu(phongChieu);
                    ghe.setTrangThai(Ghe.TrangThaiGhe.DA_DAT);
                    
                    dsGheDaDat.add(ghe);
                }
            }
        }
        return dsGheDaDat;
    }

    public List<Ghe> getDanhSachGheTrong(int maLichChieu, int maPhong) throws SQLException {
        List<Ghe> dsGheTrong = new ArrayList<>();
        String sql = "SELECT g.*, p.maPhong, p.tenPhong FROM Ghe g " +
                     "JOIN PhongChieu p ON g.maPhong = p.maPhong " +
                     "WHERE g.maPhong = ? " +
                     "AND g.maGhe NOT IN (" +
                     "  SELECT ctv.maGhe FROM ChiTietVe ctv " +
                     "  JOIN Ve v ON ctv.maVe = v.maVe " +
                     "  WHERE v.maLichChieu = ?" +
                     ")";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, maPhong);
            stmt.setInt(2, maLichChieu);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Tạo đối tượng PhongChieu
                    PhongChieu phongChieu = new PhongChieu();
                    phongChieu.setMaPhong(rs.getInt("maPhong"));
                    phongChieu.setTenPhong(rs.getString("tenPhong"));
                    
                    // Tạo đối tượng Ghe
                    Ghe ghe = new Ghe();
                    ghe.setMaGhe(rs.getInt("maGhe"));
                    ghe.setTenGhe(rs.getString("tenGhe"));
                    ghe.setLoaiGhe(Ghe.LoaiGhe.valueOf(rs.getString("loaiGhe")));
                    ghe.setPhongChieu(phongChieu);
                    ghe.setTrangThai(Ghe.TrangThaiGhe.CON_TRONG);
                    
                    dsGheTrong.add(ghe);
                }
            }
        }
        return dsGheTrong;
    }

    public double tinhTongTienGhe(int maVe) throws SQLException {
        String sql = "SELECT SUM(giaVe) AS tongTien FROM ChiTietVe WHERE maVe = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, maVe);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("tongTien");
                }
                return 0;
            }
        }
    }

    public ChiTietVe getChiTietVeTheoMa(int maChiTietVe) throws SQLException {
        String sql = "SELECT ctv.*, g.tenGhe, g.loaiGhe, p.maPhong, p.tenPhong " +
                     "FROM ChiTietVe ctv " +
                     "JOIN Ghe g ON ctv.maGhe = g.maGhe " +
                     "JOIN PhongChieu p ON g.maPhong = p.maPhong " +
                     "WHERE ctv.maChiTietVe = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, maChiTietVe);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ChiTietVe chiTietVe = new ChiTietVe();
                    chiTietVe.setMaChiTietVe(rs.getInt("maChiTietVe"));
                    
                    Ve ve = new Ve();
                    ve.setMaVe(rs.getInt("maVe"));
                    chiTietVe.setVe(ve);
                    
                    PhongChieu phongChieu = new PhongChieu();
                    phongChieu.setMaPhong(rs.getInt("maPhong"));
                    phongChieu.setTenPhong(rs.getString("tenPhong"));
                    
                    Ghe ghe = new Ghe();
                    ghe.setMaGhe(rs.getInt("maGhe"));
                    ghe.setTenGhe(rs.getString("tenGhe"));
                    ghe.setLoaiGhe(Ghe.LoaiGhe.valueOf(rs.getString("loaiGhe")));
                    ghe.setPhongChieu(phongChieu);
                    
                    chiTietVe.setGhe(ghe);
                    chiTietVe.setGiaVe(rs.getDouble("giaVe"));
                    return chiTietVe;
                }
                return null;
            }
        }
    }

    public int demSoGheTheoDat(int maLichChieu, Ghe.LoaiGhe loaiGhe) throws SQLException {
        String sql = "SELECT COUNT(*) AS soLuong FROM ChiTietVe ctv " +
                     "JOIN Ve v ON ctv.maVe = v.maVe " +
                     "JOIN Ghe g ON ctv.maGhe = g.maGhe " +
                     "WHERE v.maLichChieu = ? AND g.loaiGhe = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, maLichChieu);
            stmt.setString(2, loaiGhe.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("soLuong");
                }
                return 0;
            }
        }
    }
}
