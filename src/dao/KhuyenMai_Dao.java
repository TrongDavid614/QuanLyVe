package dao;

import connectSQL.ConnectSQL;
import entity.KhuyenMai;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class KhuyenMai_Dao {
    public ArrayList<KhuyenMai> dskm;
    public KhuyenMai_Dao() {
        dskm = new ArrayList<>();
    }

    public ArrayList<KhuyenMai> getalltbKhuyenMai() {
        try{
            Connection con = connectSQL.ConnectSQL.getConnection();
            String sql = "SELECT * FROM khuyenmai";
            Statement  st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String makhuyenmai = rs.getString("maKM");
                String tenkhuyenmai = rs.getString("tenKM");
                double phantramgiamgia = rs.getDouble("phanTramGiam");
                LocalDate thoigianbatdau = rs.getDate("ngayBatDau").toLocalDate();
                LocalDate thoigianketthuc = rs.getDate("ngayKetThuc").toLocalDate();
                KhuyenMai km = new KhuyenMai(makhuyenmai, tenkhuyenmai, phantramgiamgia, thoigianketthuc, thoigianketthuc);
                dskm.add(km);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dskm;
    }
    public boolean isExits(String makhuyenmai) {
        try{
            Connection con = connectSQL.ConnectSQL.getInstance().getConnection();
            String sql = "select count(*) from khuyenmai where maKM = ?";
            java.sql.PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, makhuyenmai);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean createKM(KhuyenMai km) {
        try{
            Connection con = ConnectSQL.getInstance().getConnection();
            String sql = "INSERT INTO khuyenmai (maKM, tenKM, phanTramGiam, ngayBatDau, ngayKetThuc) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, km.getMaKhuyenMai());
            stmt.setString(2, km.getTenKhuyenMai());
            stmt.setDouble(3, km.getPhanTramGiam());
            stmt.setDate(4, Date.valueOf(km.getNgayGioDat()));
            stmt.setDate(5, Date.valueOf(km.getNgayKetThuc()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteKM(String makhuyenmai) {
        try{
            Connection con = ConnectSQL.getInstance().getConnection();
            String sql = "DELETE FROM khuyenmai WHERE maKM = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, makhuyenmai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean updateKM(KhuyenMai km) {
        try{
            Connection con = ConnectSQL.getInstance().getConnection();
            String sql = "UPDATE khuyenmai SET tenKM = ?, phanTramGiam = ?, ngayBatDau = ?, ngayKetThuc = ? WHERE maKM = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, km.getTenKhuyenMai());
            stmt.setDouble(2, km.getPhanTramGiam());
            stmt.setDate(3, Date.valueOf(km.getNgayGioDat()));
            stmt.setDate(4, Date.valueOf(km.getNgayKetThuc()));
            stmt.setString(5, km.getMaKhuyenMai());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
