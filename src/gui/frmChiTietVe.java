package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietVe_Dao;
import dao.Ghe_Dao;
import dao.Ve_Dao;
import entity.ChiTietVe;
import entity.Ghe;
import entity.Ve;

public class frmChiTietVe extends JFrame {
    private JTextField txtMaVe, txtTenKhachHang, txtTenPhim, txtNgayDat, txtGioChieu;
    private JTextField txtMaChiTietVe, txtMaGhe, txtTenGhe, txtLoaiGhe, txtPhongChieu, txtGiaVe;
    private JTable tblChiTietVe;
    private DefaultTableModel model;
    private JButton btnThem, btnXoa, btnSua, btnTimKiem, btnXoaTrang;
    private Ve_Dao ve_dao;
    private Ghe_Dao ghe_dao;
    private ChiTietVe_Dao chiTietVe_dao;
    private Ve ve;

    public frmChiTietVe(int maVe) {
        setTitle("Chi Tiết Vé");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ve_dao = new Ve_Dao();
        ghe_dao = new Ghe_Dao();
        chiTietVe_dao = new ChiTietVe_Dao();

        createGUI(maVe);
    }

    private void createGUI(int maVe) {
        JPanel pnlMain = new JPanel(new BorderLayout(10, 10));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel thông tin vé
        JPanel pnlVeInfo = new JPanel(new GridLayout(5, 2, 10, 10));
        pnlVeInfo.setBorder(BorderFactory.createTitledBorder("Thông Tin Vé"));

        txtMaVe = new JTextField(10);
        txtMaVe.setEditable(false);
        txtTenKhachHang = new JTextField(10);
        txtTenKhachHang.setEditable(false);
        txtTenPhim = new JTextField(10);
        txtTenPhim.setEditable(false);
        txtNgayDat = new JTextField(10);
        txtNgayDat.setEditable(false);
        txtGioChieu = new JTextField(10);
        txtGioChieu.setEditable(false);

        pnlVeInfo.add(new JLabel("Mã Vé:"));
        pnlVeInfo.add(txtMaVe);
        pnlVeInfo.add(new JLabel("Tên Khách Hàng:"));
        pnlVeInfo.add(txtTenKhachHang);
        pnlVeInfo.add(new JLabel("Tên Phim:"));
        pnlVeInfo.add(txtTenPhim);
        pnlVeInfo.add(new JLabel("Ngày Đặt:"));
        pnlVeInfo.add(txtNgayDat);
        pnlVeInfo.add(new JLabel("Giờ Chiếu:"));
        pnlVeInfo.add(txtGioChieu);

        // Panel thông tin chi tiết vé
        JPanel pnlChiTietVe = new JPanel(new BorderLayout());
        pnlChiTietVe.setBorder(BorderFactory.createTitledBorder("Chi Tiết Vé"));

        JPanel pnlForm = new JPanel(new GridLayout(6, 2, 10, 10));
        txtMaChiTietVe = new JTextField(10);
        txtMaChiTietVe.setEditable(false);
        txtMaGhe = new JTextField(10);
        txtTenGhe = new JTextField(10);
        txtTenGhe.setEditable(false);
        txtLoaiGhe = new JTextField(10);
        txtLoaiGhe.setEditable(false);
        txtPhongChieu = new JTextField(10);
        txtPhongChieu.setEditable(false);
        txtGiaVe = new JTextField(10);

        pnlForm.add(new JLabel("Mã Chi Tiết Vé:"));
        pnlForm.add(txtMaChiTietVe);
        pnlForm.add(new JLabel("Mã Ghế:"));
        pnlForm.add(txtMaGhe);
        pnlForm.add(new JLabel("Tên Ghế:"));
        pnlForm.add(txtTenGhe);
        pnlForm.add(new JLabel("Loại Ghế:"));
        pnlForm.add(txtLoaiGhe);
        pnlForm.add(new JLabel("Phòng Chiếu:"));
        pnlForm.add(txtPhongChieu);
        pnlForm.add(new JLabel("Giá Vé:"));
        pnlForm.add(txtGiaVe);

        // Panel nút chức năng
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnSua = new JButton("Sửa");
        btnTimKiem = new JButton("Tìm Kiếm");
        btnXoaTrang = new JButton("Xóa Trắng");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnTimKiem);
        pnlButtons.add(btnXoaTrang);

        // Bảng chi tiết vé
        String[] columns = {"Mã Chi Tiết Vé", "Mã Vé", "Mã Ghế", "Tên Ghế", "Loại Ghế", "Phòng Chiếu", "Giá Vé"};
        model = new DefaultTableModel(columns, 0);
        tblChiTietVe = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblChiTietVe);

        // Thêm các panel vào panel chính
        pnlChiTietVe.add(pnlForm, BorderLayout.NORTH);
        pnlChiTietVe.add(pnlButtons, BorderLayout.CENTER);
        pnlMain.add(pnlVeInfo, BorderLayout.NORTH);
        pnlMain.add(pnlChiTietVe, BorderLayout.CENTER);
        pnlMain.add(scrollPane, BorderLayout.SOUTH);

        add(pnlMain);

        // Load thông tin vé
        try {
            ve = ve_dao.getVeTheoMa(maVe);
            if (ve != null) {
                txtMaVe.setText(String.valueOf(ve.getMaVe()));
                txtTenKhachHang.setText(ve.getKhachHang().getTenKhachHang());
                txtTenPhim.setText(ve.getPhim().getTenPhim());
                txtNgayDat.setText(new SimpleDateFormat("dd/MM/yyyy").format(ve.getNgayDat()));
                txtGioChieu.setText(new SimpleDateFormat("HH:mm:ss").format(ve.getGioChieu()));
                loadDataByMaVe();
            } else {
                JOptionPane.showMessageDialog(this, "Mã vé không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải thông tin vé: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
        }

        // Sự kiện
        txtMaGhe.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateGheInfo();
            }
        });

        btnThem.addActionListener(e -> themChiTietVe());
        btnXoa.addActionListener(e -> xoaChiTietVe());
        btnSua.addActionListener(e -> capNhatChiTietVe());
        btnTimKiem.addActionListener(e -> timKiemChiTietVe());
        btnXoaTrang.addActionListener(e -> clearFields());

        tblChiTietVe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblChiTietVe.getSelectedRow();
                if (row >= 0) {
                    txtMaChiTietVe.setText(model.getValueAt(row, 0).toString());
                    txtMaVe.setText(model.getValueAt(row, 1).toString());
                    txtMaGhe.setText(model.getValueAt(row, 2).toString());
                    txtTenGhe.setText(model.getValueAt(row, 3).toString());
                    txtLoaiGhe.setText(model.getValueAt(row, 4).toString());
                    txtPhongChieu.setText(model.getValueAt(row, 5).toString());
                    txtGiaVe.setText(model.getValueAt(row, 6).toString());
                }
            }
        });
    }

    private void updateGheInfo() {
        try {
            String maGheText = txtMaGhe.getText().trim();
            if (!maGheText.isEmpty()) {
                int maGhe = Integer.parseInt(maGheText);
                Ghe ghe = ghe_dao.getGheTheoMa(maGhe);
                if (ghe != null) {
                    txtTenGhe.setText(ghe.getTenGhe());
                    txtLoaiGhe.setText(ghe.getLoaiGhe().toString());
                    txtPhongChieu.setText(ghe.getPhongChieu() != null ? ghe.getPhongChieu().getTenPhong() : "");
                    txtGiaVe.setText(ghe.getLoaiGhe().toString().equals("VIP") ? "120000" : "90000");
                } else {
                    clearGheInfo();
                    JOptionPane.showMessageDialog(this, "Mã ghế không tồn tại",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                clearGheInfo();
            }
        } catch (NumberFormatException e) {
            clearGheInfo();
            JOptionPane.showMessageDialog(this, "Mã ghế phải là số nguyên",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void themChiTietVe() {
        if (!validateInput()) {
            return;
        }

        try {
            int maVe = Integer.parseInt(txtMaVe.getText().trim());
            int maGhe = Integer.parseInt(txtMaGhe.getText().trim());
            double giaVe = Double.parseDouble(txtGiaVe.getText().trim());

            // Kiểm tra vé tồn tại
            ve = ve_dao.getVeTheoMa(maVe);
            if (ve == null) {
                JOptionPane.showMessageDialog(this, "Mã vé không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra ghế tồn tại
            Ghe ghe = ghe_dao.getGheTheoMa(maGhe);
            if (ghe == null) {
                JOptionPane.showMessageDialog(this, "Mã ghế không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra ghế đã được đặt
            if (chiTietVe_dao.kiemTraGheDaDat(maGhe, ve.getPhongChieu().getMaPhong(), ve.getGioChieu())) {
                JOptionPane.showMessageDialog(this, "Ghế này đã được đặt cho phòng và giờ chiếu này",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tạo đối tượng chi tiết vé
            ChiTietVe chiTietVe = new ChiTietVe(ve, ghe, giaVe);

            // Thêm vào CSDL
            boolean result = chiTietVe_dao.themChiTietVe(chiTietVe);
            if (result) {
                JOptionPane.showMessageDialog(this, "Thêm chi tiết vé thành công",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadDataByMaVe();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm chi tiết vé thất bại",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã vé, mã ghế và giá vé phải là số",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi SQL: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaChiTietVe() {
        if (txtMaChiTietVe.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết vé cần xóa",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa chi tiết vé này?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maChiTietVe = Integer.parseInt(txtMaChiTietVe.getText().trim());
                boolean result = chiTietVe_dao.xoaChiTietVe(maChiTietVe);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Xóa chi tiết vé thành công",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadDataByMaVe();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa chi tiết vé thất bại",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã chi tiết vé phải là số",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi SQL: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void capNhatChiTietVe() {
        if (txtMaChiTietVe.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chi tiết vé cần cập nhật",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!validateInput()) {
            return;
        }

        try {
            int maChiTietVe = Integer.parseInt(txtMaChiTietVe.getText().trim());
            int maVe = Integer.parseInt(txtMaVe.getText().trim());
            int maGhe = Integer.parseInt(txtMaGhe.getText().trim());
            double giaVe = Double.parseDouble(txtGiaVe.getText().trim());

            // Kiểm tra vé tồn tại
            ve = ve_dao.getVeTheoMa(maVe);
            if (ve == null) {
                JOptionPane.showMessageDialog(this, "Mã vé không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra ghế tồn tại
            Ghe ghe = ghe_dao.getGheTheoMa(maGhe);
            if (ghe == null) {
                JOptionPane.showMessageDialog(this, "Mã ghế không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra ghế đã được đặt
            ChiTietVe chiTietVeHienTai = chiTietVe_dao.getChiTietVeTheoMa(maChiTietVe);
            if (chiTietVeHienTai != null && chiTietVeHienTai.getGhe().getMaGhe() != maGhe) {
                if (chiTietVe_dao.kiemTraGheDaDat(maGhe, ve.getPhongChieu().getMaPhong(), ve.getGioChieu())) {
                    JOptionPane.showMessageDialog(this, "Ghế này đã được đặt cho phòng và giờ chiếu này",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Tạo đối tượng chi tiết vé
            ChiTietVe chiTietVe = new ChiTietVe(maChiTietVe, ve, ghe, giaVe);

            // Cập nhật vào CSDL
            boolean result = chiTietVe_dao.capNhatChiTietVe(chiTietVe);
            if (result) {
                JOptionPane.showMessageDialog(this, "Cập nhật chi tiết vé thành công",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadDataByMaVe();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật chi tiết vé thất bại",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã vé, mã ghế và giá vé phải là số",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi SQL: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemChiTietVe() {
        try {
            String maChiTietVeText = txtMaChiTietVe.getText().trim();
            String maGheText = txtMaGhe.getText().trim();

            if (maChiTietVeText.isEmpty() && maGheText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã chi tiết vé hoặc mã ghế để tìm kiếm",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (!maChiTietVeText.isEmpty()) {
                int maChiTietVe = Integer.parseInt(maChiTietVeText);
                ChiTietVe ctv = chiTietVe_dao.getChiTietVeTheoMa(maChiTietVe);
                if (ctv != null) {
                    model.setRowCount(0);
                    model.addRow(new Object[]{
                            ctv.getMaChiTietVe(),
                            ctv.getVe().getMaVe(),
                            ctv.getGhe().getMaGhe(),
                            ctv.getGhe().getTenGhe(),
                            ctv.getGhe().getLoaiGhe(),
                            ctv.getGhe().getPhongChieu().getTenPhong(),
                            ctv.getGiaVe()
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết vé",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                int maGhe = Integer.parseInt(maGheText);
                List<ChiTietVe> dsChiTietVe = chiTietVe_dao.getChiTietVeTheoMaVe(Integer.parseInt(txtMaVe.getText().trim()));
                model.setRowCount(0);
                boolean found = false;
                for (ChiTietVe ctv : dsChiTietVe) {
                    if (ctv.getGhe().getMaGhe() == maGhe) {
                        model.addRow(new Object[]{
                                ctv.getMaChiTietVe(),
                                ctv.getVe().getMaVe(),
                                ctv.getGhe().getMaGhe(),
                                ctv.getGhe().getTenGhe(),
                                ctv.getGhe().getLoaiGhe(),
                                ctv.getGhe().getPhongChieu().getTenPhong(),
                                ctv.getGiaVe()
                        });
                        found = true;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết vé với mã ghế này",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã chi tiết vé hoặc mã ghế phải là số",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi SQL: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDataByMaVe() {
        try {
            int maVe = Integer.parseInt(txtMaVe.getText().trim());
            List<ChiTietVe> dsChiTietVe = chiTietVe_dao.getChiTietVeTheoMaVe(maVe);
            model.setRowCount(0);
            for (ChiTietVe ctv : dsChiTietVe) {
                model.addRow(new Object[]{
                        ctv.getMaChiTietVe(),
                        ctv.getVe().getMaVe(),
                        ctv.getGhe().getMaGhe(),
                        ctv.getGhe().getTenGhe(),
                        ctv.getGhe().getLoaiGhe(),
                        ctv.getGhe().getPhongChieu().getTenPhong(),
                        ctv.getGiaVe()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách chi tiết vé: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtMaChiTietVe.setText("");
        txtMaGhe.setText("");
        clearGheInfo();
    }

    private void clearGheInfo() {
        txtTenGhe.setText("");
        txtLoaiGhe.setText("");
        txtPhongChieu.setText("");
        txtGiaVe.setText("");
    }

    private boolean validateInput() {
        String maVe = txtMaVe.getText().trim();
        String maGhe = txtMaGhe.getText().trim();
        String giaVe = txtGiaVe.getText().trim();

        if (maVe.isEmpty() || maGhe.isEmpty() || giaVe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(maVe);
            Integer.parseInt(maGhe);
            Double.parseDouble(giaVe);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã vé, mã ghế và giá vé phải là số",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
