package gui;

import connectSQL.ConnectSQL;
import dao.DoAn_Dao;
import dao.KhachHang_Dao;
import entity.DoAn;
import entity.KhachHang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class frmDoAn extends JFrame implements ActionListener, MouseListener {

    private KhachHang_Dao kh_dao;
    private ConnectSQL connectSQL;
    private final JLabel title;
    private DoAn_Dao da_dao;
    private JMenuItem miTrangChu;
    private JMenuItem miKhuyenMai;
    private JMenuItem miKhachHang;
    private JLabel lblQuanLy;
    private JMenuItem miDangXuat;
    private JMenuItem miThoat;

    private JTextField txtMaDoAn;
    private JTextField txtTenDoAn;
    private JTextField txtDonGia;
    private JTextArea txtMoTa;
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    private JButton btnChonHinhAnh;
    private JLabel lblHinhAnh;

    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXoaTrang;
    private JButton btnDatDoAn;

    private JPanel pDoAnGrid;
    private ArrayList<JPanel> foodPanels;
    private ImageIcon selectedImage;
    private String selectedImagePath; // Biến để lưu đường dẫn hình ảnh

    public frmDoAn() {
        kh_dao = new KhachHang_Dao();
        da_dao = new DoAn_Dao();
        connectSQL.getInstance().connect();
        setTitle("Đồ Ăn");
        setSize(1600, 830);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu
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
                menuItem.addActionListener(this);
            } else if (item.equals("Thoát")) {
                miThoat = menuItem;
                menuItem.addActionListener(this);
            } else if (item.equals("Trang chủ")) {
                miTrangChu = menuItem;
                menuItem.addActionListener(this);
            }
            menuHeThong.add(menuItem);
        }

        String[] danhMucItems = {"Khách hàng", "Nhân viên", "Khuyến mại", "Phim"};
        for (String item : danhMucItems) {
            JMenuItem menuItem = createMenuItem(item);
            menuItem.setFont(new Font("Arial", Font.PLAIN, 18));
            if (item.equals("Khuyến mại")) {
                miKhuyenMai = menuItem;
            } else if (item.equals("Khách hàng")) {
                miKhachHang = menuItem;
            }
            menuItem.addActionListener(this);
            menuDanhMuc.add(menuItem);
        }

        String[] xuLyItems = {"Vé", "Phòng chiếu", "Đồ ăn"};
        for (String item : xuLyItems) {
            JMenuItem menuItem = createMenuItem(item);
            menuItem.setFont(new Font("Arial", Font.PLAIN, 18));
            menuItem.addActionListener(this);
            menuXuLy.add(menuItem);
        }

        JMenuItem doanhThuItem = createMenuItem("Doanh thu");
        doanhThuItem.setFont(new Font("Arial", Font.PLAIN, 18));
        doanhThuItem.addActionListener(this);
        menuThongKe.add(doanhThuItem);

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

        // Phần tiêu đề
        JPanel titlePanel = new JPanel(new BorderLayout());
        title = new JLabel("Danh sách đồ ăn", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(0x4B5AA1));
        titlePanel.setPreferredSize(new Dimension(1600, 80));
        titlePanel.add(title, BorderLayout.CENTER);
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Phần nội dung chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1600, 700));

        // Phần trên: Nhập liệu (30%)
        JPanel pTop = new JPanel(new BorderLayout());
        pTop.setPreferredSize(new Dimension(1600, 270));

        // Phần nhập liệu và tìm kiếm
        JPanel pInput = new JPanel(new BorderLayout());
        pInput.setBorder(BorderFactory.createTitledBorder("Nhập thông tin đồ ăn"));

        // Bên trái: Mã đồ ăn, Tên đồ ăn, Đơn giá, và Hình ảnh
        JPanel pLeft = new JPanel(new GridBagLayout());
        pLeft.setPreferredSize(new Dimension(980, 180));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblMaDoAn = new JLabel("Mã đồ ăn:");
        txtMaDoAn = new JTextField(30);
        JLabel lblTenDoAn = new JLabel("Tên đồ ăn:");
        txtTenDoAn = new JTextField(30);
        JLabel lblDonGia = new JLabel("Đơn giá:");
        txtDonGia = new JTextField(30);

        lblMaDoAn.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTenDoAn.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDonGia.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMaDoAn.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTenDoAn.setFont(new Font("Arial", Font.PLAIN, 16));
        txtDonGia.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblMaDoAn, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtMaDoAn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblTenDoAn, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtTenDoAn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblDonGia, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtDonGia, gbc);

        // Phần thêm hình ảnh
        JPanel pImage = new JPanel(new BorderLayout());
        pImage.setBorder(BorderFactory.createTitledBorder("Hình ảnh"));
        pImage.setPreferredSize(new Dimension(400, 60));
        btnChonHinhAnh = new JButton("Chọn hình ảnh");
        btnChonHinhAnh.setFont(new Font("Arial", Font.PLAIN, 16));
        btnChonHinhAnh.setBackground(new Color(30, 144, 255));
        btnChonHinhAnh.setForeground(Color.WHITE);
        btnChonHinhAnh.setPreferredSize(new Dimension(150, 40));
        lblHinhAnh = new JLabel();
        lblHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);
        lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblHinhAnh.setPreferredSize(new Dimension(50, 50));
        pImage.add(btnChonHinhAnh, BorderLayout.WEST);
        pImage.add(lblHinhAnh, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(pImage, gbc);

        // Bên phải: Mô tả
        JPanel pRight = new JPanel(new GridBagLayout());
        pRight.setPreferredSize(new Dimension(420, 180));
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 5, 5, 10);
        gbcRight.anchor = GridBagConstraints.NORTHWEST;

        JLabel lblMoTa = new JLabel("Mô tả:");
        txtMoTa = new JTextArea(6, 20);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        JScrollPane spMoTa = new JScrollPane(txtMoTa);

        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMoTa.setFont(new Font("Arial", Font.PLAIN, 16));

        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.weightx = 0.0;
        gbcRight.fill = GridBagConstraints.NONE;
        pRight.add(lblMoTa, gbcRight);

        gbcRight.gridx = 1;
        gbcRight.weightx = 1.0;
        gbcRight.weighty = 1.0;
        gbcRight.fill = GridBagConstraints.BOTH;
        pRight.add(spMoTa, gbcRight);

        pInput.add(pLeft, BorderLayout.WEST);
        pInput.add(pRight, BorderLayout.EAST);

        JPanel pSearch = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTimKiem = new JLabel("Tìm kiếm:");
        txtTimKiem = new JTextField(40);
        btnTimKiem = new JButton("Tìm kiếm");

        lblTimKiem.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 16));
        btnTimKiem.setFont(new Font("Arial", Font.PLAIN, 16));
        btnTimKiem.setPreferredSize(new Dimension(100, 40));
        btnTimKiem.setBackground(new Color(30, 144, 255));
        btnTimKiem.setForeground(Color.WHITE);

        pSearch.add(lblTimKiem);
        pSearch.add(txtTimKiem);
        pSearch.add(btnTimKiem);

        pTop.add(pInput, BorderLayout.CENTER);
        pTop.add(pSearch, BorderLayout.SOUTH);

        JPanel pBottom = new JPanel(new BorderLayout());
        pBottom.setPreferredSize(new Dimension(1600, 550));

        JPanel pButtons = new JPanel();
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.Y_AXIS));
        pButtons.setPreferredSize(new Dimension(200, 490));
        pButtons.setBorder(BorderFactory.createTitledBorder("Chức năng"));

        Dimension buttonSize = new Dimension(160, 40);

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnSua = new JButton("Sửa");
        btnXoaTrang = new JButton("Xóa trắng");
        btnDatDoAn = new JButton("Đặt đồ ăn");

        JButton[] buttons = {btnThem, btnXoa, btnSua, btnXoaTrang, btnDatDoAn};
        Color[] colors = {
                new Color(34, 139, 34),
                new Color(220, 20, 60),
                new Color(255, 140, 0),
                new Color(128, 128, 128),
                new Color(147, 112, 219)
        };

        for (int i = 0; i < buttons.length; i++) {
            pButtons.add(Box.createVerticalStrut(15));
            JButton btn = buttons[i];
            btn.setFont(new Font("Arial", Font.PLAIN, 16));
            btn.setPreferredSize(buttonSize);
            btn.setMaximumSize(buttonSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBackground(colors[i]);
            btn.setForeground(Color.WHITE);
            pButtons.add(btn);
            pButtons.add(Box.createVerticalStrut(15));
        }

        JPanel pDoAn = new JPanel(new BorderLayout());
        pDoAn.setBorder(BorderFactory.createTitledBorder("Danh sách đồ ăn"));
        pDoAn.setPreferredSize(new Dimension(1400, 490));

        foodPanels = new ArrayList<>();
        pDoAnGrid = new JPanel();
        pDoAnGrid.setLayout(new GridLayout(0, 3, 10, 10));
        pDoAnGrid.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(pDoAnGrid);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pDoAn.add(scrollPane, BorderLayout.CENTER);

        pBottom.add(pButtons, BorderLayout.WEST);
        pBottom.add(pDoAn, BorderLayout.CENTER);

        mainPanel.add(pTop, BorderLayout.NORTH);
        mainPanel.add(pBottom, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoaTrang.addActionListener(this);
        btnDatDoAn.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnChonHinhAnh.addActionListener(this);
        loadDoAnLenGridView();
        setVisible(true);
    }

    private JPanel createFoodPanel(String ma, String ten, String gia, String moTa, ImageIcon icon) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(400, 200));

        JLabel lblImage = new JLabel(icon);
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMa = new JLabel("Mã: " + ma);
        JLabel lblTen = new JLabel("Tên: " + ten);
        JLabel lblGia = new JLabel("Giá: " + gia);
        JLabel lblMoTa = new JLabel("Mô tả: " + moTa);

        lblMa.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTen.setFont(new Font("Arial", Font.PLAIN, 14));
        lblGia.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 14));

        lblMa.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblGia.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMoTa.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(5));
        panel.add(lblImage);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblMa);
        panel.add(lblTen);
        panel.add(lblGia);
        panel.add(lblMoTa);
        panel.add(Box.createVerticalStrut(5));

        // Thêm sự kiện khi click vào panel món ăn
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtMaDoAn.setText(ma);
                txtTenDoAn.setText(ten);
                txtDonGia.setText(gia);
                txtMoTa.setText(moTa);

                // Hiển thị hình ảnh đã chọn
                File file = new File(icon.toString());
                if (file.exists()) {
                    ImageIcon img = new ImageIcon(icon.toString());
                    Image scaled = img.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    lblHinhAnh.setIcon(new ImageIcon(scaled));
                    selectedImagePath = icon.toString();
                }
            }
        });

        return panel;
    }

    public void loadDoAnLenGridView() {
        pDoAnGrid.removeAll();
        foodPanels.clear();
        List<DoAn> danhSachDoAnTuDB = da_dao.layTatCaDoAn();
        if (danhSachDoAnTuDB == null || danhSachDoAnTuDB.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có đồ ăn nào trong cơ sở dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for (DoAn da : danhSachDoAnTuDB) {
            String ma = da.getMaDoAn() != null ? da.getMaDoAn() : "N/A";
            String ten = da.getTenDoAn() != null ? da.getTenDoAn() : "N/A";
            String gia = String.valueOf(da.getGiaDoAn());
            String moTa = da.getMoTa() != null ? da.getMoTa() : "";
            String hinhAnhPath = da.getHinhAnh() != null ? da.getHinhAnh() : "src/img/default.png";

            ImageIcon icon;
            File file = new File(hinhAnhPath);
            if (file.exists()) {
                icon = new ImageIcon(hinhAnhPath);
            } else {
                icon = new ImageIcon("src/img/default.png");
            }
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JPanel foodPanel = createFoodPanel(ma, ten, gia, moTa, new ImageIcon(scaledImage));
            foodPanels.add(foodPanel);
            pDoAnGrid.add(foodPanel);
        }
        pDoAnGrid.revalidate();
        pDoAnGrid.repaint();
    }

    private JMenuItem createMenuItem(String name) {
        return new JMenuItem(name);
    }

    private JMenuItem createMenuItem(String name, ActionListener action) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(action);
        return item;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == miDangXuat) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn đăng xuất?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                dispose();
                try {
                    new frmLogin();
                    JOptionPane.showMessageDialog(null, "Đăng xuất thành công!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: Không thể mở frmLogin!");
                }
            }
        } else if (o == miThoat) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn thoát?",
                    "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                dispose();
            }
        } else if (o == miTrangChu) {
            dispose();
            new frmTrangChu();
        } else if (o == miKhuyenMai) {
            dispose();
            new frmKhuyenMai();
        } else if (o == miKhachHang) {
            dispose();
            new frmKhachHang();
        } else if (o == btnThem) {
            ThemDoAn();
        } else if (o == btnChonHinhAnh) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                selectedImagePath = file.getAbsolutePath();
                ImageIcon imageIcon = new ImageIcon(selectedImagePath);
                Image scaledImage = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                selectedImage = new ImageIcon(imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                lblHinhAnh.setIcon(new ImageIcon(scaledImage));
            }
        } else if (o == btnXoaTrang) {
            XoaTrang();
        } else if (o == btnXoa) {
            XoaDoAn();
        } else if (o == btnSua) {
            SuaDoAn();
        } else if (o == btnDatDoAn) {
            DatDoAn();
        }
    }

    public void ThemDoAn() {
        String ma = txtMaDoAn.getText().trim();
        String ten = txtTenDoAn.getText().trim();
        String gia = txtDonGia.getText().trim();
        String moTa = txtMoTa.getText().trim();
        String hinhAnhPath = selectedImagePath != null ? selectedImagePath : "src/img/doan1.png";

        if (ma.isEmpty() || ten.isEmpty() || gia.isEmpty() || moTa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra mã đồ ăn đã tồn tại
        if (da_dao.isExits(ma)) {
            JOptionPane.showMessageDialog(this, "Mã đồ ăn đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra định dạng đơn giá
        try {
            Double.parseDouble(gia.replace(",", ""));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hiển thị món ăn
        ImageIcon icon = new ImageIcon(hinhAnhPath);
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JPanel foodPanel = createFoodPanel(ma, ten, gia, moTa, new ImageIcon(scaledImage));
        foodPanels.add(foodPanel);
        pDoAnGrid.add(foodPanel);

        DoAn da = new DoAn(ma, ten, Double.parseDouble(gia.replace(",", "")), moTa, hinhAnhPath);

        try {
            if (da_dao.createDoAn(da)) {
                JOptionPane.showMessageDialog(this, "Thêm đồ ăn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                XoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm đồ ăn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Cập nhật giao diện
        pDoAnGrid.revalidate();
        pDoAnGrid.repaint();
    }

    public void XoaTrang() {
        txtMaDoAn.setText("");
        txtTenDoAn.setText("");
        txtDonGia.setText("");
        txtMoTa.setText("");
        lblHinhAnh.setIcon(null);
        selectedImage = null;
        selectedImagePath = null;
        txtMaDoAn.requestFocus();
    }

    public void XoaDoAn() {

        String maDoAn = txtMaDoAn.getText().trim();
        if (maDoAn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đồ ăn cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa đồ ăn có mã: " + maDoAn + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (da_dao.deleteDoAn(maDoAn)) {
                    JOptionPane.showMessageDialog(this, "Xóa đồ ăn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadDoAnLenGridView();
                    XoaTrang();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa đồ ăn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void SuaDoAn() {
        int selected = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn sửa món ăn này?", "Xác nhận sửa", JOptionPane.YES_NO_OPTION);
        if (selected == JOptionPane.YES_OPTION) {
            String maDoAn = txtMaDoAn.getText().trim();
            String ten = txtTenDoAn.getText().trim();
            String gia = txtDonGia.getText().trim();
            String moTa = txtMoTa.getText().trim();
            String hinhAnhPath = selectedImagePath != null ? selectedImagePath : "src/img/doan1.png";

            if (maDoAn.isEmpty() || ten.isEmpty() || gia.isEmpty() || moTa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Double.parseDouble(gia.replace(",", ""));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DoAn da = new DoAn(maDoAn, ten, Double.parseDouble(gia.replace(",", "")), moTa, hinhAnhPath);

                try {
                    boolean isUpdated = da_dao.updateDoAn(da);
                    SwingUtilities.invokeLater(() -> {
                        if (isUpdated) {
                            JOptionPane.showMessageDialog(null, "Sửa món ăn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new frmDoAn();
                        } else {
                            JOptionPane.showMessageDialog(null, "Sửa món ăn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    });
                }
        }
    }

    public void DatDoAn() {
        String maDoAn = txtMaDoAn.getText().trim();
        String tenDoAn = txtTenDoAn.getText().trim();
        String donGia = txtDonGia.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (maDoAn.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn món ăn để đặt!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo hộp thoại đặt đồ ăn
        JDialog dialog = new JDialog(this, "Đặt Đồ Ăn", true);
        dialog.setSize(600, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel pFoodInfo = new JPanel(new GridBagLayout());
        pFoodInfo.setBorder(BorderFactory.createTitledBorder("Thông tin món ăn"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblMa = new JLabel("Mã đồ ăn: " + maDoAn);
        JLabel lblTen = new JLabel("Tên đồ ăn: " + tenDoAn);
        JLabel lblGia = new JLabel("Đơn giá: " + donGia);
        JLabel lblMoTa = new JLabel("Mô tả: " + moTa);

        lblMa.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTen.setFont(new Font("Arial", Font.PLAIN, 16));
        lblGia.setFont(new Font("Arial", Font.PLAIN, 16));
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        pFoodInfo.add(lblMa, gbc);
        gbc.gridy = 1;
        pFoodInfo.add(lblTen, gbc);
        gbc.gridy = 2;
        pFoodInfo.add(lblGia, gbc);
        gbc.gridy = 3;
        pFoodInfo.add(lblMoTa, gbc);

        JPanel pCustomerInfo = new JPanel(new GridBagLayout());
        pCustomerInfo.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        GridBagConstraints gbcCustomer = new GridBagConstraints();
        gbcCustomer.insets = new Insets(5, 5, 5, 5);
        gbcCustomer.anchor = GridBagConstraints.WEST;

        JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
        JTextField txtSoDienThoai = new JTextField(20);
        JButton btnKiemTra = new JButton("Kiểm tra");

        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        JTextField txtMaKH = new JTextField(20);
        JLabel lblTenKH = new JLabel("Tên khách hàng:");
        JTextField txtTenKH = new JTextField(20);
        JLabel lblNgaySinh = new JLabel("Ngày sinh (dd/MM/yyyy):");
        JTextField txtNgaySinh = new JTextField(20);
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        JTextField txtDiaChi = new JTextField(20);
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        JComboBox<String> cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});

        lblSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 16));
        txtSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 16));
        btnKiemTra.setFont(new Font("Arial", Font.PLAIN, 16));
        lblMaKH.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMaKH.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTenKH.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTenKH.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNgaySinh.setFont(new Font("Arial", Font.PLAIN, 16));
        txtNgaySinh.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDiaChi.setFont(new Font("Arial", Font.PLAIN, 16));
        txtDiaChi.setFont(new Font("Arial", Font.PLAIN, 16));
        lblGioiTinh.setFont(new Font("Arial", Font.PLAIN, 16));
        cbGioiTinh.setFont(new Font("Arial", Font.PLAIN, 16));

        txtMaKH.setEnabled(false);
        txtTenKH.setEnabled(false);
        txtNgaySinh.setEnabled(false);
        txtDiaChi.setEnabled(false);
        cbGioiTinh.setEnabled(false);

        gbcCustomer.gridx = 0;
        gbcCustomer.gridy = 0;
        pCustomerInfo.add(lblSoDienThoai, gbcCustomer);
        gbcCustomer.gridx = 1;
        pCustomerInfo.add(txtSoDienThoai, gbcCustomer);
        gbcCustomer.gridx = 2;
        pCustomerInfo.add(btnKiemTra, gbcCustomer);

        gbcCustomer.gridx = 0;
        gbcCustomer.gridy = 1;
        pCustomerInfo.add(lblMaKH, gbcCustomer);
        gbcCustomer.gridx = 1;
        pCustomerInfo.add(txtMaKH, gbcCustomer);

        gbcCustomer.gridx = 0;
        gbcCustomer.gridy = 2;
        pCustomerInfo.add(lblTenKH, gbcCustomer);
        gbcCustomer.gridx = 1;
        pCustomerInfo.add(txtTenKH, gbcCustomer);

        gbcCustomer.gridx = 0;
        gbcCustomer.gridy = 3;
        pCustomerInfo.add(lblNgaySinh, gbcCustomer);
        gbcCustomer.gridx = 1;
        pCustomerInfo.add(txtNgaySinh, gbcCustomer);

        gbcCustomer.gridx = 0;
        gbcCustomer.gridy = 4;
        pCustomerInfo.add(lblDiaChi, gbcCustomer);
        gbcCustomer.gridx = 1;
        pCustomerInfo.add(txtDiaChi, gbcCustomer);

        gbcCustomer.gridx = 0;
        gbcCustomer.gridy = 5;
        pCustomerInfo.add(lblGioiTinh, gbcCustomer);
        gbcCustomer.gridx = 1;
        pCustomerInfo.add(cbGioiTinh, gbcCustomer);

        JPanel pButtons = new JPanel(new FlowLayout());
        JButton btnXacNhan = new JButton("Xác nhận đặt");
        JButton btnHuy = new JButton("Hủy");
        btnXacNhan.setFont(new Font("Arial", Font.PLAIN, 16));
        btnHuy.setFont(new Font("Arial", Font.PLAIN, 16));
        btnXacNhan.setBackground(new Color(34, 139, 34));
        btnXacNhan.setForeground(Color.WHITE);
        btnHuy.setBackground(new Color(220, 20, 60));
        btnHuy.setForeground(Color.WHITE);

        pButtons.add(btnXacNhan);
        pButtons.add(btnHuy);

        btnKiemTra.addActionListener(e -> {
            String soDienThoai = txtSoDienThoai.getText().trim();
            System.out.println("Số điện thoại nhập vào: '" + soDienThoai + "'");
            if (soDienThoai.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KhachHang kh = kh_dao.getKhachHangBySoDienThoai(soDienThoai);
            if (kh != null) {
                txtMaKH.setText(kh.getMaKhachHang());
                txtTenKH.setText(kh.getTenKhachHang());
                txtNgaySinh.setText(kh.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                txtDiaChi.setText(kh.getDiaChi());
                cbGioiTinh.setSelectedItem(kh.getGioiTinh());
                txtMaKH.setEnabled(false);
                txtTenKH.setEnabled(false);
                txtNgaySinh.setEnabled(false);
                txtDiaChi.setEnabled(false);
                cbGioiTinh.setEnabled(false);
                JOptionPane.showMessageDialog(dialog, "Khách hàng đã tồn tại!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                txtMaKH.setEnabled(true);
                txtTenKH.setEnabled(true);
                txtNgaySinh.setEnabled(true);
                txtDiaChi.setEnabled(true);
                cbGioiTinh.setEnabled(true);
                txtMaKH.setText("KH" + System.currentTimeMillis());
                txtTenKH.setText("");
                txtNgaySinh.setText("");
                txtDiaChi.setText("");
                cbGioiTinh.setSelectedIndex(0);
                JOptionPane.showMessageDialog(dialog, "Khách hàng chưa tồn tại. Vui lòng nhập thông tin!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnXacNhan.addActionListener(e -> {
            String soDienThoai = txtSoDienThoai.getText().trim();
            String maKH = txtMaKH.getText().trim();
            String tenKH = txtTenKH.getText().trim();
            String ngaySinh = txtNgaySinh.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String gioiTinh = (String) cbGioiTinh.getSelectedItem();

            if (soDienThoai.isEmpty() || maKH.isEmpty() || tenKH.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ thông tin khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate ngaySinhDate;
            try {
                ngaySinhDate = LocalDate.parse(ngaySinh, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Ngày sinh không đúng định dạng (dd/MM/yyyy)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KhachHang kh = new KhachHang(maKH, tenKH, ngaySinhDate, diaChi, gioiTinh, soDienThoai);

            KhachHang existingKh = kh_dao.getKhachHangBySoDienThoai(soDienThoai);
            if (existingKh == null) {
                try {
                    if (kh_dao.createKH(kh)) {
                        JOptionPane.showMessageDialog(dialog, "Thêm khách hàng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Thêm khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi khi thêm khách hàng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            JOptionPane.showMessageDialog(dialog, "Đặt đồ ăn thành công cho khách hàng " + tenKH + "!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnHuy.addActionListener(e -> dialog.dispose());

        dialog.add(pFoodInfo, BorderLayout.NORTH);
        dialog.add(pCustomerInfo, BorderLayout.CENTER);
        dialog.add(pButtons, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new frmDoAn();
    }
}