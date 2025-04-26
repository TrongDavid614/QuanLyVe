package gui;

import connectSQL.ConnectSQL;
import dao.KhachHang_Dao;
import dao.Phim_Dao;
import dao.PhongChieu_Dao;
import dao.Ve_Dao;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class frmVe extends JFrame implements ActionListener, MouseListener {
    private JMenuItem miTrangChu;
    private JMenuItem miKhachHang;
    private JMenuItem miKhuyenMai;
    private JMenuItem miPhim;
    private JMenuItem miDangXuat;
    private JMenuItem miThoat;
    private JMenuItem miDoanhThu;

    private JTable tableVe;
    private DefaultTableModel tableModelVe;

    private JTextField txtMaVe;
    private JTextField txtMaKhachHang;
    private JTextField txtTenKhachHang;
    private JTextField txtMaPhim;
    private JTextField txtTenPhim;
    private JTextField txtMaPhong;
    private JTextField txtNgayDat;
    private JTextField txtGioChieu;
    private JTextField txtTongTien;
    private JTextField txtPhuongThucThanhToan;
    private JTextField txtMaGiaoDich;
    private JCheckBox chkDaThanhToan;

    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXoaTrang;
    private JButton btnTimKiem;
    private JButton btnChiTiet;
    private JButton btnChonKhachHang;
    private JButton btnChonPhim;
    private JButton btnChonPhong;
    private JButton btnThanhToan;

    private JTextField txtTimKiem;

    private Ve_Dao ve_dao;
    private KhachHang_Dao kh_dao;
    private Phim_Dao phim_dao;
    private PhongChieu_Dao phong_dao;

    private JLabel lblQuanLy;

    public frmVe() {
        ve_dao = new Ve_Dao();
        kh_dao = new KhachHang_Dao();
        phim_dao = new Phim_Dao();
        phong_dao = new PhongChieu_Dao();

        try {
            ConnectSQL.getInstance().connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối cơ sở dữ liệu: " + e.getMessage());
        }

        setTitle("Quản Lý Vé");
        setSize(1600, 830);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        taoMenu();

        taoGiaoDien();

        docDuLieuVaoTable();

        dangKySuKien();

        setVisible(true);
    }

    private void taoMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(1600, 50));
        menuBar.setBackground(new Color(0xE4E2D8));

        ImageIcon imgHeThong = new ImageIcon("src/img/heThong.png");
        ImageIcon imgDanhMuc = new ImageIcon("src/img/danhMuc.png");
        ImageIcon imgXuLy = new ImageIcon("src/img/xuLy.png");
        ImageIcon imgThongKe = new ImageIcon("src/img/thongKe.png");

        imgHeThong = new ImageIcon(imgHeThong.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        imgDanhMuc = new ImageIcon(imgDanhMuc.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        imgXuLy = new ImageIcon(imgXuLy.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        imgThongKe = new ImageIcon(imgThongKe.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        JMenu menuHeThong = new JMenu("HỆ THỐNG");
        JMenu menuDanhMuc = new JMenu("DANH MỤC");
        JMenu menuXuLy = new JMenu("XỬ LÝ");
        JMenu menuThongKe = new JMenu("THỐNG KÊ");

        Font fontMenu = new Font("Arial", Font.BOLD, 20);
        menuHeThong.setFont(fontMenu);
        menuDanhMuc.setFont(fontMenu);
        menuXuLy.setFont(fontMenu);
        menuThongKe.setFont(fontMenu);

        menuHeThong.setIcon(imgHeThong);
        menuDanhMuc.setIcon(imgDanhMuc);
        menuXuLy.setIcon(imgXuLy);
        menuThongKe.setIcon(imgThongKe);

        String[] heThongItems = {"Trang chủ", "Tài khoản", "Đăng xuất", "Thoát"};
        for (String item : heThongItems) {
            JMenuItem menuItem = createMenuItem(item);
            menuItem.setFont(new Font("Arial", Font.PLAIN, 18));
            if (item.equals("Đăng xuất")) {
                miDangXuat = menuItem;
            } else if (item.equals("Thoát")) {
                miThoat = menuItem;
            } else if (item.equals("Trang chủ")) {
                miTrangChu = menuItem;
            }
            menuHeThong.add(menuItem);
        }

        String[] danhMucItems = {"Khách hàng", "Nhân viên", "Khuyến mại", "Phim"};
        for (String item : danhMucItems) {
            JMenuItem menuItem = createMenuItem(item);
            menuItem.setFont(new Font("Arial", Font.PLAIN, 18));
            if (item.equals("Khách hàng")) {
                miKhachHang = menuItem;
            } else if (item.equals("Khuyến mại")) {
                miKhuyenMai = menuItem;
            } else if (item.equals("Phim")) {
                miPhim = menuItem;
            }
            menuDanhMuc.add(menuItem);
        }

        String[] xuLyItems = {"Vé", "Phòng chiếu", "Đồ ăn"};
        for (String item : xuLyItems) {
            JMenuItem menuItem = createMenuItem(item);
            menuItem.setFont(new Font("Arial", Font.PLAIN, 18));
            menuXuLy.add(menuItem);
        }

        miDoanhThu = createMenuItem("Doanh thu");
        miDoanhThu.setFont(new Font("Arial", Font.PLAIN, 18));
        menuThongKe.add(miDoanhThu);

        menuBar.add(menuHeThong);
        menuBar.add(menuDanhMuc);
        menuBar.add(menuXuLy);
        menuBar.add(menuThongKe);

        lblQuanLy = new JLabel("QUẢN LÝ: VĂN TRỌNG");
        lblQuanLy.setFont(new Font("Arial", Font.PLAIN, 18));
        lblQuanLy.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(lblQuanLy);

        setJMenuBar(menuBar);
    }

    private JMenuItem createMenuItem(String name) {
        return new JMenuItem(name);
    }

    private void taoGiaoDien() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Quản Lý Vé", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(0x4B5AA1));
        titlePanel.setPreferredSize(new Dimension(1600, 80));
        titlePanel.add(title, BorderLayout.CENTER);
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setPreferredSize(new Dimension(1600, 330));

        JPanel leftPanel = createInputPanel();
        JPanel rightPanel = createButtonPanel();

        formPanel.add(leftPanel, BorderLayout.CENTER);
        formPanel.add(rightPanel, BorderLayout.EAST);

        JPanel searchPanel = createSearchPanel();
        JPanel tablePanel = createTablePanel();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin vé"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblMaVe = new JLabel("Mã vé:");
        txtMaVe = new JTextField(10);
        txtMaVe.setEditable(false);

        JLabel lblMaKhachHang = new JLabel("Mã KH:");
        txtMaKhachHang = new JTextField(10);
        txtMaKhachHang.setEditable(false);

        JLabel lblTenKhachHang = new JLabel("Tên KH:");
        txtTenKhachHang = new JTextField(25);
        txtTenKhachHang.setEditable(false);

        JLabel lblMaPhim = new JLabel("Mã phim:");
        txtMaPhim = new JTextField(10);
        txtMaPhim.setEditable(false);

        JLabel lblTenPhim = new JLabel("Tên phim:");
        txtTenPhim = new JTextField(25);
        txtTenPhim.setEditable(false);

        JLabel lblMaPhong = new JLabel("Mã phòng:");
        txtMaPhong = new JTextField(10);
        txtMaPhong.setEditable(false);

        JLabel lblNgayDat = new JLabel("Ngày đặt:");
        txtNgayDat = new JTextField(15);

        JLabel lblGioChieu = new JLabel("Giờ chiếu:");
        txtGioChieu = new JTextField(15);

        JLabel lblTongTien = new JLabel("Tổng tiền:");
        txtTongTien = new JTextField(15);

        JLabel lblPhuongThucThanhToan = new JLabel("Phương thức TT:");
        txtPhuongThucThanhToan = new JTextField(15);

        JLabel lblMaGiaoDich = new JLabel("Mã giao dịch:");
        txtMaGiaoDich = new JTextField(15);

        JLabel lblDaThanhToan = new JLabel("Đã thanh toán:");
        chkDaThanhToan = new JCheckBox();
        chkDaThanhToan.setEnabled(false);

        btnChonKhachHang = new JButton("Chọn KH");
        btnChonPhim = new JButton("Chọn phim");
        btnChonPhong = new JButton("Chọn phòng");
        btnThanhToan = new JButton("Thanh toán");

        btnChonKhachHang.setBackground(new Color(30, 144, 255));
        btnChonKhachHang.setForeground(Color.WHITE);
        btnChonPhim.setBackground(new Color(30, 144, 255));
        btnChonPhim.setForeground(Color.WHITE);
        btnChonPhong.setBackground(new Color(30, 144, 255));
        btnChonPhong.setForeground(Color.WHITE);
        btnThanhToan.setBackground(new Color(50, 205, 50));
        btnThanhToan.setForeground(Color.WHITE);

        btnChonKhachHang.setPreferredSize(new Dimension(100, 30));
        btnChonPhim.setPreferredSize(new Dimension(100, 30));
        btnChonPhong.setPreferredSize(new Dimension(100, 30));
        btnThanhToan.setPreferredSize(new Dimension(120, 30));

        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font textFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        lblMaVe.setFont(labelFont);
        lblMaKhachHang.setFont(labelFont);
        lblTenKhachHang.setFont(labelFont);
        lblMaPhim.setFont(labelFont);
        lblTenPhim.setFont(labelFont);
        lblMaPhong.setFont(labelFont);
        lblNgayDat.setFont(labelFont);
        lblGioChieu.setFont(labelFont);
        lblTongTien.setFont(labelFont);
        lblPhuongThucThanhToan.setFont(labelFont);
        lblMaGiaoDich.setFont(labelFont);
        lblDaThanhToan.setFont(labelFont);

        txtMaVe.setFont(textFont);
        txtMaKhachHang.setFont(textFont);
        txtTenKhachHang.setFont(textFont);
        txtMaPhim.setFont(textFont);
        txtTenPhim.setFont(textFont);
        txtMaPhong.setFont(textFont);
        txtNgayDat.setFont(textFont);
        txtGioChieu.setFont(textFont);
        txtTongTien.setFont(textFont);
        txtPhuongThucThanhToan.setFont(textFont);
        txtMaGiaoDich.setFont(textFont);

        btnChonKhachHang.setFont(buttonFont);
        btnChonPhim.setFont(buttonFont);
        btnChonPhong.setFont(buttonFont);
        btnThanhToan.setFont(buttonFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblMaVe, gbc);

        gbc.gridx = 1;
        panel.add(txtMaVe, gbc);

        gbc.gridx = 2;
        panel.add(lblMaKhachHang, gbc);

        gbc.gridx = 3;
        panel.add(txtMaKhachHang, gbc);

        gbc.gridx = 4;
        panel.add(btnChonKhachHang, gbc);

        gbc.gridx = 5;
        panel.add(lblTenKhachHang, gbc);

        gbc.gridx = 6;
        gbc.gridwidth = 2;
        panel.add(txtTenKhachHang, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblMaPhim, gbc);

        gbc.gridx = 1;
        panel.add(txtMaPhim, gbc);

        gbc.gridx = 2;
        panel.add(lblTenPhim, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 2;
        panel.add(txtTenPhim, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 5;
        panel.add(btnChonPhim, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblMaPhong, gbc);

        gbc.gridx = 1;
        panel.add(txtMaPhong, gbc);

        gbc.gridx = 2;
        panel.add(btnChonPhong, gbc);

        gbc.gridx = 3;
        panel.add(lblNgayDat, gbc);

        gbc.gridx = 4;
        panel.add(txtNgayDat, gbc);

        gbc.gridx = 5;
        panel.add(lblGioChieu, gbc);

        gbc.gridx = 6;
        panel.add(txtGioChieu, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblTongTien, gbc);

        gbc.gridx = 1;
        panel.add(txtTongTien, gbc);

        gbc.gridx = 2;
        panel.add(lblPhuongThucThanhToan, gbc);

        gbc.gridx = 3;
        panel.add(txtPhuongThucThanhToan, gbc);

        gbc.gridx = 4;
        panel.add(lblMaGiaoDich, gbc);

        gbc.gridx = 5;
        panel.add(txtMaGiaoDich, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblDaThanhToan, gbc);

        gbc.gridx = 1;
        panel.add(chkDaThanhToan, gbc);

        gbc.gridx = 5;
        gbc.gridwidth = 2;
        panel.add(btnThanhToan, gbc);
        gbc.gridwidth = 1;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtNgayDat.setText(dateFormat.format(new Date()));

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Chọn tác vụ"));
        panel.setPreferredSize(new Dimension(200, 280));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnSua = new JButton("Sửa");
        btnXoaTrang = new JButton("Xóa trắng");
        btnChiTiet = new JButton("Chi tiết");

        Font btnFont = new Font("Arial", Font.PLAIN, 16);
        Dimension btnSize = new Dimension(150, 40);

        btnThem.setFont(btnFont);
        btnXoa.setFont(btnFont);
        btnSua.setFont(btnFont);
        btnXoaTrang.setFont(btnFont);
        btnChiTiet.setFont(btnFont);

        btnThem.setPreferredSize(btnSize);
        btnXoa.setPreferredSize(btnSize);
        btnSua.setPreferredSize(btnSize);
        btnXoaTrang.setPreferredSize(btnSize);
        btnChiTiet.setPreferredSize(btnSize);

        btnThem.setMaximumSize(btnSize);
        btnXoa.setMaximumSize(btnSize);
        btnSua.setMaximumSize(btnSize);
        btnXoaTrang.setMaximumSize(btnSize);
        btnChiTiet.setMaximumSize(btnSize);

        btnThem.setBackground(new Color(34, 139, 34));
        btnThem.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(220, 20, 60));
        btnXoa.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(255, 140, 0));
        btnSua.setForeground(Color.WHITE);
        btnXoaTrang.setBackground(new Color(128, 128, 128));
        btnXoaTrang.setForeground(Color.WHITE);
        btnChiTiet.setBackground(new Color(70, 130, 180));
        btnChiTiet.setForeground(Color.WHITE);

        panel.add(btnThem);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnXoa);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnSua);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnXoaTrang);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnChiTiet);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        txtTimKiem = new JTextField(40);
        btnTimKiem = new JButton("Tìm kiếm");

        lblTimKiem.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 16));
        btnTimKiem.setFont(new Font("Arial", Font.PLAIN, 16));

        btnTimKiem.setBackground(new Color(30, 144, 255));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setPreferredSize(new Dimension(120, 40));

        panel.add(lblTimKiem);
        panel.add(txtTimKiem);
        panel.add(btnTimKiem);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] headers = {"Mã vé", "Mã KH", "Tên KH", "Mã phim", "Tên phim", "Phòng", "Ngày đặt", "Giờ chiếu", "Tổng tiền", "Phương thức TT", "Đã thanh toán"};
        tableModelVe = new DefaultTableModel(headers, 0);
        tableVe = new JTable(tableModelVe);

        tableVe.setFont(new Font("Arial", Font.PLAIN, 14));
        tableVe.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableVe.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableVe.getColumnModel().getColumn(4).setPreferredWidth(200);

        tableVe.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableVe);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void docDuLieuVaoTable() {
        tableModelVe.setRowCount(0);

        try {
            List<Ve> dsVe = ve_dao.getAllVe();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            for (Ve ve : dsVe) {
                String ngayDat = ve.getNgayDat() != null ? dateFormat.format(ve.getNgayDat()) : "";
                String gioChieu = ve.getGioChieu() != null ? timeFormat.format(ve.getGioChieu()) : "";
                String daThanhToan = ve.getNgayThanhToan() != null ? "Đã TT" : "Chưa TT";

                String[] row = {
                        String.valueOf(ve.getMaVe()),
                        ve.getKhachHang().getMaKhachHang(),
                        ve.getKhachHang().getTenKhachHang(),
                        ve.getPhim().getMaPhim(),
                        ve.getPhim().getTenPhim(),
                        String.valueOf(ve.getPhongChieu().getMaPhong()),
                        ngayDat,
                        gioChieu,
                        String.format("%.0f", ve.getTongTien()),
                        ve.getPhuongThucThanhToan() != null ? ve.getPhuongThucThanhToan() : "",
                        daThanhToan
                };

                tableModelVe.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc dữ liệu vé: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void dangKySuKien() {
        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoaTrang.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnChiTiet.addActionListener(this);
        btnChonKhachHang.addActionListener(this);
        btnChonPhim.addActionListener(this);
        btnChonPhong.addActionListener(this);
        btnThanhToan.addActionListener(this);
        miTrangChu.addActionListener(this);
        miKhachHang.addActionListener(this);
        miKhuyenMai.addActionListener(this);
        miPhim.addActionListener(this);
        miDangXuat.addActionListener(this);
        miThoat.addActionListener(this);
        miDoanhThu.addActionListener(this);

        tableVe.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o == miTrangChu) {
            dispose();
            new frmTrangChu();
        } else if (o == miKhachHang) {
            dispose();
            new frmKhachHang();
        } else if (o == miKhuyenMai) {
            dispose();
            new frmKhuyenMai();
        } else if (o == miPhim) {
            dispose();
            new frmPhim(); 
        } else if (o == miDangXuat) {
            dangXuat();
        } else if (o == miThoat) {
            thoat();
        } else if (o == miDoanhThu) {
            dispose();
            new frmDoanhThu(); 
        } else if (o == btnThem) {
            themVe();
        } else if (o == btnXoa) {
            xoaVe();
        } else if (o == btnSua) {
            suaVe();
        } else if (o == btnXoaTrang) {
            xoaTrang();
        } else if (o == btnTimKiem) {
            timKiem();
        } else if (o == btnChiTiet) {
            xemChiTietVe();
        } else if (o == btnChonKhachHang) {
            chonKhachHang();
        } else if (o == btnChonPhim) {
            chonPhim();
        } else if (o == btnChonPhong) {
            chonPhong();
        } else if (o == btnThanhToan) {
            thanhToanVe();
        }
    }

    private void dangXuat() {
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            dispose();
            new frmLogin();
            JOptionPane.showMessageDialog(null, "Đăng xuất thành công!");
        }
    }

    private void thoat() {
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn thoát?",
                "Xác nhận thoát",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void themVe() {
        try {
            // Lấy thông tin từ form
            String maKH = txtMaKhachHang.getText().trim();
            String maPhim = txtMaPhim.getText().trim();
            String maPhong = txtMaPhong.getText().trim();
            String ngayDatStr = txtNgayDat.getText().trim();
            String gioChieuStr = txtGioChieu.getText().trim();
            String tongTienStr = txtTongTien.getText().trim();

            // Kiểm tra thông tin bắt buộc
            if (maKH.isEmpty() || maPhim.isEmpty() || maPhong.isEmpty() || ngayDatStr.isEmpty() || gioChieuStr.isEmpty() || tongTienStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            // Chuyển đổi định dạng ngày và giờ
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date ngayDat = dateFormat.parse(ngayDatStr);
            Date gioChieu = timeFormat.parse(gioChieuStr);
            double tongTien = Double.parseDouble(tongTienStr);

            // Tạo đối tượng vé
            KhachHang kh = kh_dao.getKhachHangByMa(maKH);
            Phim phim = phim_dao.getPhimByMa(maPhim);
            PhongChieu phong = phong_dao.timPhongChieuTheoMa(Integer.parseInt(maPhong));

            if (kh == null || phim == null || phong == null) {
                JOptionPane.showMessageDialog(this, "Thông tin khách hàng, phim hoặc phòng chiếu không hợp lệ!");
                return;
            }

            Ve ve = new Ve(kh, phim, phong, gioChieu);
            ve.setNgayDat(ngayDat);
            ve.setTongTien(tongTien);

            // Lưu vé vào database
            if (ve_dao.themVe(ve)) {
                JOptionPane.showMessageDialog(this, "Thêm vé thành công!");
                docDuLieuVaoTable();
                xoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm vé thất bại!");
            }
        } catch (SQLException | ParseException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void xoaVe() {
        int row = tableVe.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa vé này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int maVe = Integer.parseInt(tableModelVe.getValueAt(row, 0).toString());
                    if (ve_dao.huyVe(maVe)) {
                        JOptionPane.showMessageDialog(this, "Xóa vé thành công!");
                        docDuLieuVaoTable();
                        xoaTrang();
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể xóa vé đã thanh toán!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa vé: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vé để xóa!");
        }
    }

    private void suaVe() {
        int row = tableVe.getSelectedRow();
        if (row >= 0) {
            try {
                int maVe = Integer.parseInt(txtMaVe.getText().trim());
                String maKH = txtMaKhachHang.getText().trim();
                String maPhim = txtMaPhim.getText().trim();
                String maPhong = txtMaPhong.getText().trim();
                String ngayDatStr = txtNgayDat.getText().trim();
                String gioChieuStr = txtGioChieu.getText().trim();
                String tongTienStr = txtTongTien.getText().trim();
                String phuongThucTT = txtPhuongThucThanhToan.getText().trim();
                String maGiaoDich = txtMaGiaoDich.getText().trim();

                if (maKH.isEmpty() || maPhim.isEmpty() || maPhong.isEmpty() || ngayDatStr.isEmpty() || gioChieuStr.isEmpty() || tongTienStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                Date ngayDat = dateFormat.parse(ngayDatStr);
                Date gioChieu = timeFormat.parse(gioChieuStr);
                double tongTien = Double.parseDouble(tongTienStr);

                KhachHang kh = kh_dao.getKhachHangByMa(maKH);
                Phim phim = phim_dao.getPhimByMa(maPhim);
                PhongChieu phong = phong_dao.timPhongChieuTheoMa(Integer.parseInt(maPhong));

                if (kh == null || phim == null || phong == null) {
                    JOptionPane.showMessageDialog(this, "Thông tin khách hàng, phim hoặc phòng chiếu không hợp lệ!");
                    return;
                }

                Ve ve = new Ve(maVe, kh, phim, phong, ngayDat, gioChieu, tongTien, phuongThucTT, null, maGiaoDich);

                if (ve_dao.capNhatVe(ve)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật vé thành công!");
                    docDuLieuVaoTable();
                    xoaTrang();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật vé thất bại!");
                }
            } catch (SQLException | ParseException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vé để sửa!");
        }
    }

    private void xoaTrang() {
        txtMaVe.setText("");
        txtMaKhachHang.setText("");
        txtTenKhachHang.setText("");
        txtMaPhim.setText("");
        txtTenPhim.setText("");
        txtMaPhong.setText("");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtNgayDat.setText(dateFormat.format(new Date()));
        txtGioChieu.setText("");
        txtTongTien.setText("");
        txtPhuongThucThanhToan.setText("");
        txtMaGiaoDich.setText("");
        chkDaThanhToan.setSelected(false);
        txtTimKiem.setText("");
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!");
            return;
        }

        try {
            List<Ve> dsVe = ve_dao.timKiemVe(keyword);
            tableModelVe.setRowCount(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            for (Ve ve : dsVe) {
                String ngayDat = ve.getNgayDat() != null ? dateFormat.format(ve.getNgayDat()) : "";
                String gioChieu = ve.getGioChieu() != null ? timeFormat.format(ve.getGioChieu()) : "";
                String daThanhToan = ve.getNgayThanhToan() != null ? "Đã TT" : "Chưa TT";

                String[] row = {
                        String.valueOf(ve.getMaVe()),
                        ve.getKhachHang().getMaKhachHang(),
                        ve.getKhachHang().getTenKhachHang(),
                        ve.getPhim().getMaPhim(),
                        ve.getPhim().getTenPhim(),
                        String.valueOf(ve.getPhongChieu().getMaPhong()),
                        ngayDat,
                        gioChieu,
                        String.format("%.0f", ve.getTongTien()),
                        ve.getPhuongThucThanhToan() != null ? ve.getPhuongThucThanhToan() : "",
                        daThanhToan
                };

                tableModelVe.addRow(row);
            }

            if (dsVe.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy vé nào!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm vé: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void xemChiTietVe() {
        int row = tableVe.getSelectedRow();
        if (row >= 0) {
            int maVe = Integer.parseInt(tableModelVe.getValueAt(row, 0).toString());
            dispose();
            new frmChiTietVe(maVe);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vé để xem chi tiết!");
        }
    }

    private void chonKhachHang() {
        JDialog dialog = new JDialog(this, "Chọn khách hàng", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã KH", "Tên KH"}, 0);
        JTable table = new JTable(model);
        try {
            List<KhachHang> dsKH = kh_dao.getalltbkhachhang();
            for (KhachHang kh : dsKH) {
                model.addRow(new Object[]{kh.getMaKhachHang(), kh.getTenKhachHang()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách khách hàng: " + ex.getMessage());
        }

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaKhachHang.setText(table.getValueAt(row, 0).toString());
                    txtTenKhachHang.setText(table.getValueAt(row, 1).toString());
                    dialog.dispose();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void chonPhim() {
        JDialog dialog = new JDialog(this, "Chọn phim", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã phim", "Tên phim"}, 0);
        JTable table = new JTable(model);
        try {
            List<Phim> dsPhim = phim_dao.layTatCaPhim();
            for (Phim phim : dsPhim) {
                model.addRow(new Object[]{phim.getMaPhim(), phim.getTenPhim()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách phim: " + ex.getMessage());
        }

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaPhim.setText(table.getValueAt(row, 0).toString());
                    txtTenPhim.setText(table.getValueAt(row, 1).toString());
                    dialog.dispose();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void chonPhong() {
        JDialog dialog = new JDialog(this, "Chọn phòng chiếu", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã phòng", "Tên phòng"}, 0);
        JTable table = new JTable(model);
        try {
            List<PhongChieu> dsPhong = phong_dao.getAllPhongChieu();
            for (PhongChieu phong : dsPhong) {
                model.addRow(new Object[]{phong.getMaPhong(), phong.getTenPhong()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách phòng chiếu: " + ex.getMessage());
        }

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaPhong.setText(table.getValueAt(row, 0).toString());
                    dialog.dispose();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void thanhToanVe() {
        int row = tableVe.getSelectedRow();
        if (row >= 0) {
            try {
                int maVe = Integer.parseInt(tableModelVe.getValueAt(row, 0).toString());
                String phuongThuc = txtPhuongThucThanhToan.getText().trim();
                String maGiaoDich = txtMaGiaoDich.getText().trim();

                if (phuongThuc.isEmpty() || maGiaoDich.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập phương thức thanh toán và mã giao dịch!");
                    return;
                }

                if (ve_dao.thanhToanVe(maVe, phuongThuc, maGiaoDich)) {
                    JOptionPane.showMessageDialog(this, "Thanh toán vé thành công!");
                    docDuLieuVaoTable();
                    xoaTrang();
                } else {
                    JOptionPane.showMessageDialog(this, "Thanh toán vé thất bại!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán vé: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vé để thanh toán!");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tableVe.getSelectedRow();
        if (row >= 0) {
            txtMaVe.setText(tableModelVe.getValueAt(row, 0).toString());
            txtMaKhachHang.setText(tableModelVe.getValueAt(row, 1).toString());
            txtTenKhachHang.setText(tableModelVe.getValueAt(row, 2).toString());
            txtMaPhim.setText(tableModelVe.getValueAt(row, 3).toString());
            txtTenPhim.setText(tableModelVe.getValueAt(row, 4).toString());
            txtMaPhong.setText(tableModelVe.getValueAt(row, 5).toString());
            txtNgayDat.setText(tableModelVe.getValueAt(row, 6).toString());
            txtGioChieu.setText(tableModelVe.getValueAt(row, 7).toString());
            txtTongTien.setText(tableModelVe.getValueAt(row, 8).toString());
            txtPhuongThucThanhToan.setText(tableModelVe.getValueAt(row, 9).toString());
            chkDaThanhToan.setSelected(tableModelVe.getValueAt(row, 10).toString().equals("Đã TT"));
            txtMaGiaoDich.setText(tableModelVe.getValueAt(row, 9) != null ? tableModelVe.getValueAt(row, 9).toString() : "");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new frmVe();
    }
}
