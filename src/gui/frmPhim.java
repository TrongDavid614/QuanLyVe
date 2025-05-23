package gui;

import dao.Phim_Dao;
import entity.Phim;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;
import connectSQL.ConnectSQL;

public class frmPhim extends JFrame implements ActionListener, MouseListener {
    private JLabel title;
    private JTextField txtMaPhim, txtTenPhim, txtTheLoai, txtThoiLuong, txtDaoDien, txtNamSanXuat, txtQuocGia;
    private JTextArea txtMoTa;
    private JButton btnThem, btnXoa, btnSua, btnXoaTrang, btnChonPoster, btnTimKiem;
    private JTextField txtTimKiem;
    private JLabel lblPoster;
    private ImageIcon selectedPoster;
    private String selectedPosterPath; // Biến để lưu đường dẫn ảnh, giống frmDoAn
    private JPanel pPhimGrid;
    private ArrayList<JPanel> phimPanels;
    private JMenuItem miDangXuat;
    private JMenuItem miThoat;
    private JMenuItem miTrangChu;
    private JMenuItem miKhuyenMai;
    private JMenuItem miKhachHang;
    private JLabel lblQuanLy;

    private Phim_Dao phimDAO;
    private List<Phim> danhSachPhimTuDB;

    public frmPhim() {
        ConnectSQL.getInstance().connect();
        setTitle("Quản lý phim");
        setSize(1600, 830);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        phimDAO = new Phim_Dao();
        danhSachPhimTuDB = phimDAO.layTatCaPhim();

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

        lblQuanLy = new JLabel("QUẢN LÝ: Ngọc Hiền");
        lblQuanLy.setFont(new Font("Arial", Font.PLAIN, 18));
        lblQuanLy.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(lblQuanLy);

        setJMenuBar(menuBar);

        // Phần tiêu đề
        JPanel titlePanel = new JPanel(new BorderLayout());
        title = new JLabel("Danh sách phim", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(0x4B5AA1));
        titlePanel.setPreferredSize(new Dimension(1600, 80));
        titlePanel.add(title, BorderLayout.CENTER);
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Phần nội dung chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1600, 700));

        JPanel pTop = new JPanel(new BorderLayout());
        pTop.setPreferredSize(new Dimension(1600, 370));

        JPanel pInput = new JPanel(new BorderLayout());
        pInput.setBorder(BorderFactory.createTitledBorder("Nhập thông tin phim"));

        JPanel pLeft = new JPanel(new GridBagLayout());
        pLeft.setPreferredSize(new Dimension(980, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblMaPhim = new JLabel("Mã phim:");
        txtMaPhim = new JTextField(30);
        JLabel lblTenPhim = new JLabel("Tên phim:");
        txtTenPhim = new JTextField(30);
        JLabel lblTheLoai = new JLabel("Thể loại:");
        txtTheLoai = new JTextField(30);
        JLabel lblThoiLuong = new JLabel("Thời lượng:");
        txtThoiLuong = new JTextField(30);
        JLabel lblDaoDien = new JLabel("Đạo diễn:");
        txtDaoDien = new JTextField(30);
        JLabel lblNamSanXuat = new JLabel("Năm SX:");
        txtNamSanXuat = new JTextField(30);
        JLabel lblQuocGia = new JLabel("Quốc gia:");
        txtQuocGia = new JTextField(30);

        lblMaPhim.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTenPhim.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTheLoai.setFont(new Font("Arial", Font.PLAIN, 16));
        lblThoiLuong.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDaoDien.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNamSanXuat.setFont(new Font("Arial", Font.PLAIN, 16));
        lblQuocGia.setFont(new Font("Arial", Font.PLAIN, 16));
        txtMaPhim.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTenPhim.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTheLoai.setFont(new Font("Arial", Font.PLAIN, 16));
        txtThoiLuong.setFont(new Font("Arial", Font.PLAIN, 16));
        txtDaoDien.setFont(new Font("Arial", Font.PLAIN, 16));
        txtNamSanXuat.setFont(new Font("Arial", Font.PLAIN, 16));
        txtQuocGia.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblMaPhim, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtMaPhim, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblTenPhim, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtTenPhim, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblTheLoai, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtTheLoai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblThoiLuong, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtThoiLuong, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblDaoDien, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtDaoDien, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblNamSanXuat, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtNamSanXuat, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblQuocGia, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtQuocGia, gbc);

        // Phần thêm poster
        JPanel pPoster = new JPanel(new BorderLayout());
        pPoster.setBorder(BorderFactory.createTitledBorder("Poster"));
        pPoster.setPreferredSize(new Dimension(400, 80));
        btnChonPoster = new JButton("Chọn poster");
        btnChonPoster.setFont(new Font("Arial", Font.PLAIN, 16));
        btnChonPoster.setBackground(new Color(30, 144, 255));
        btnChonPoster.setForeground(Color.WHITE);
        btnChonPoster.setPreferredSize(new Dimension(150, 40));
        lblPoster = new JLabel();
        lblPoster.setHorizontalAlignment(SwingConstants.CENTER);
        lblPoster.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblPoster.setPreferredSize(new Dimension(80, 120));
        pPoster.add(btnChonPoster, BorderLayout.WEST);
        pPoster.add(lblPoster, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(pPoster, gbc);

        // Bên phải: Mô tả
        JPanel pRight = new JPanel(new GridBagLayout());
        pRight.setPreferredSize(new Dimension(420, 200));
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 5, 5, 10);
        gbcRight.anchor = GridBagConstraints.NORTHWEST;

        JLabel lblMoTa = new JLabel("Mô tả:");
        txtMoTa = new JTextArea(8, 20);
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

        JButton[] buttons = {btnThem, btnXoa, btnSua, btnXoaTrang};
        Color[] colors = {
                new Color(34, 139, 34),
                new Color(220, 20, 60),
                new Color(255, 140, 0),
                new Color(128, 128, 128)
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

        JPanel pPhim = new JPanel(new BorderLayout());
        pPhim.setBorder(BorderFactory.createTitledBorder("Danh sách phim"));
        pPhim.setPreferredSize(new Dimension(1400, 490));

        phimPanels = new ArrayList<>();
        pPhimGrid = new JPanel();
        pPhimGrid.setLayout(new GridLayout(0, 3, 10, 10));
        pPhimGrid.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(pPhimGrid);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pPhim.add(scrollPane, BorderLayout.CENTER);

        pBottom.add(pButtons, BorderLayout.WEST);
        pBottom.add(pPhim, BorderLayout.CENTER);

        mainPanel.add(pTop, BorderLayout.NORTH);
        mainPanel.add(pBottom, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoaTrang.addActionListener(this);
        btnChonPoster.addActionListener(this);
        btnTimKiem.addActionListener(this);

        // Đóng kết nối khi thoát
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ConnectSQL.getInstance().disconnect();
            }
        });

        loadPhimLenGridView();
        setVisible(true);
    }

    private void loadPhimLenGridView() {
        pPhimGrid.removeAll();
        phimPanels.clear();
        danhSachPhimTuDB = phimDAO.layTatCaPhim();
        if (danhSachPhimTuDB == null || danhSachPhimTuDB.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có phim nào trong cơ sở dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for (Phim phim : danhSachPhimTuDB) {
            String posterPath = phim.getPosterPath() != null ? phim.getPosterPath() : "src/img/default_poster.jpg";
            ImageIcon icon;
            File file = new File(posterPath);
            if (file.exists()) {
                icon = new ImageIcon(posterPath);
            } else {
                icon = new ImageIcon("src/img/default_poster.jpg");
            }
            Image scaledImage = icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
            JPanel phimPanel = createPhimPanel(
                    phim.getMaPhim() != null ? phim.getMaPhim() : "N/A",
                    phim.getTenPhim() != null ? phim.getTenPhim() : "N/A",
                    phim.getTheLoai() != null ? phim.getTheLoai() : "N/A",
                    String.valueOf(phim.getThoiLuong()),
                    phim.getDaoDien() != null ? phim.getDaoDien() : "N/A",
                    String.valueOf(phim.getNamSanXuat()),
                    phim.getQuocGia() != null ? phim.getQuocGia() : "N/A",
                    phim.getMoTa() != null ? phim.getMoTa() : "",
                    new ImageIcon(scaledImage),
                    posterPath
            );
            phimPanels.add(phimPanel);
            pPhimGrid.add(phimPanel);
        }
        pPhimGrid.revalidate();
        pPhimGrid.repaint();
    }

    private JPanel createPhimPanel(String ma, String ten, String theLoai, String thoiLuong, String daoDien, String namSX, String quocGia, String moTa, ImageIcon poster, String posterPath) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(400, 300));

        JLabel lblPoster = new JLabel(poster != null ? poster : new ImageIcon("src/img/default_poster.jpg"));
        lblPoster.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMa = new JLabel("<html><b>Mã:</b> " + (ma != null ? ma : "N/A") + "</html>");
        lblMa.setFont(new Font("Arial", Font.PLAIN, 16));
        lblMa.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTen = new JLabel("<html><b>Tên:</b> " + (ten != null ? ten : "N/A") + "</html>");
        lblTen.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTheLoai = new JLabel("<html><b>Thể loại:</b> " + (theLoai != null ? theLoai : "N/A") + "</html>");
        lblTheLoai.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTheLoai.setAlignmentX(Component.CENTER_ALIGNMENT);

        String tomTat = "Tóm tắt: ";
        moTa = (moTa != null) ? moTa : "";
        if (moTa.length() > 50) {
            tomTat += moTa.substring(0, 50) + "...";
        } else {
            tomTat += moTa;
        }
        JLabel lblTomTat = new JLabel("<html><b>Tóm tắt:</b> " + tomTat + "</html>");
        lblTomTat.setFont(new Font("Arial", Font.ITALIC, 15));
        lblTomTat.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnChiTiet = new JButton("Chi tiết phim");
        JButton btnDatVe = new JButton("Đặt vé ngay");

        btnChiTiet.setFont(new Font("Arial", Font.PLAIN, 15));
        btnDatVe.setFont(new Font("Arial", Font.PLAIN, 15));

        btnChiTiet.setBackground(Color.darkGray);
        btnChiTiet.setForeground(Color.WHITE);

        btnDatVe.setBackground(Color.RED);
        btnDatVe.setForeground(Color.WHITE);

        // Sự kiện nút chi tiết
        String finalMoTa = moTa;
        btnChiTiet.addActionListener(e -> {
            String chiTiet = String.format(
                    "Mã phim: %s\nTên phim: %s\nThể loại: %s\nThời lượng: %s phút\nĐạo diễn: %s\nNăm sản xuất: %s\nQuốc gia: %s\nMô tả: %s",
                    ma != null ? ma : "N/A",
                    ten != null ? ten : "N/A",
                    theLoai != null ? theLoai : "N/A",
                    thoiLuong,
                    daoDien != null ? daoDien : "N/A",
                    namSX,
                    quocGia != null ? quocGia : "N/A",
                    finalMoTa
            );
            JOptionPane.showMessageDialog(panel, chiTiet, "Chi tiết phim", JOptionPane.INFORMATION_MESSAGE);
        });

        // Sự kiện nút đặt vé
        btnDatVe.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "Đặt vé ngay cho phim: " + (ten != null ? ten : "N/A"));
        });

        buttonPanel.add(btnChiTiet);
        buttonPanel.add(btnDatVe);
        buttonPanel.setBackground(Color.WHITE);

        panel.add(Box.createVerticalStrut(5));
        panel.add(lblPoster);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblMa);
        panel.add(lblTen);
        panel.add(lblTheLoai);
        panel.add(lblTomTat);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);

        // Thêm sự kiện click vào panel để điền thông tin lên form
        String finalMoTa1 = moTa;
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtMaPhim.setText(ma);
                txtTenPhim.setText(ten);
                txtTheLoai.setText(theLoai);
                txtThoiLuong.setText(thoiLuong);
                txtDaoDien.setText(daoDien);
                txtNamSanXuat.setText(namSX);
                txtQuocGia.setText(quocGia);
                txtMoTa.setText(finalMoTa1);
                File file = new File(posterPath);
                if (file.exists()) {
                    ImageIcon img = new ImageIcon(posterPath);
                    Image scaled = img.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
                    lblPoster.setIcon(new ImageIcon(scaled));
                    selectedPosterPath = posterPath;
                } else {
                    lblPoster.setIcon(null);
                    selectedPosterPath = null;
                }
            }
        });

        return panel;
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
            themPhimVaoDatabase();
        } else if (o == btnXoa) {
            xoaPhimKhoiDatabase();
        } else if (o == btnSua) {
            suaThongTinPhimTrongDatabase();
        } else if (o == btnXoaTrang) {
            xoaTrangForm();
        } else if (o == btnChonPoster) {
            chonPoster();
        } else if (o == btnTimKiem) {
            timKiemPhimTheoTen();
        }
    }

    private void themPhimVaoDatabase() {
        String ma = txtMaPhim.getText().trim();
        String ten = txtTenPhim.getText().trim();
        String theLoai = txtTheLoai.getText().trim();
        String thoiLuongStr = txtThoiLuong.getText().trim();
        String daoDien = txtDaoDien.getText().trim();
        String namSXStr = txtNamSanXuat.getText().trim();
        String quocGia = txtQuocGia.getText().trim();
        String moTa = txtMoTa.getText().trim();
        String posterPath = selectedPosterPath != null ? selectedPosterPath : "src/img/default_poster.jpg";

        if (ma.isEmpty() || ten.isEmpty() || theLoai.isEmpty() || thoiLuongStr.isEmpty() || daoDien.isEmpty() || namSXStr.isEmpty() || quocGia.isEmpty() || moTa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int thoiLuong = Integer.parseInt(thoiLuongStr);
            int namSX = Integer.parseInt(namSXStr);

            if (phimDAO.isExist(ma)) {
                JOptionPane.showMessageDialog(this, "Mã phim đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Phim phimMoi = new Phim(ma, ten, theLoai, thoiLuong, daoDien, namSX, quocGia, moTa, posterPath);
            if (phimDAO.themPhim(phimMoi)) {
                JOptionPane.showMessageDialog(this, "Thêm phim thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadPhimLenGridView();
                xoaTrangForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm phim thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Thời lượng và năm sản xuất phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaPhimKhoiDatabase() {
        String maPhimXoa = txtMaPhim.getText().trim();
        if (maPhimXoa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phim cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phim có mã: " + maPhimXoa + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (phimDAO.xoaPhim(maPhimXoa)) {
                    JOptionPane.showMessageDialog(this, "Xóa phim thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadPhimLenGridView();
                    xoaTrangForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa phim thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void suaThongTinPhimTrongDatabase() {
        String ma = txtMaPhim.getText().trim();
        String ten = txtTenPhim.getText().trim();
        String theLoai = txtTheLoai.getText().trim();
        String thoiLuongStr = txtThoiLuong.getText().trim();
        String daoDien = txtDaoDien.getText().trim();
        String namSXStr = txtNamSanXuat.getText().trim();
        String quocGia = txtQuocGia.getText().trim();
        String moTa = txtMoTa.getText().trim();
        String posterPath = selectedPosterPath != null ? selectedPosterPath : "src/img/default_poster.jpg";

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phim cần sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (ten.isEmpty() || theLoai.isEmpty() || thoiLuongStr.isEmpty() || daoDien.isEmpty() || namSXStr.isEmpty() || quocGia.isEmpty() || moTa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int thoiLuong = Integer.parseInt(thoiLuongStr);
            int namSX = Integer.parseInt(namSXStr);

            Phim phimCapNhat = new Phim(ma, ten, theLoai, thoiLuong, daoDien, namSX, quocGia, moTa, posterPath);
            if (phimDAO.capNhatPhim(phimCapNhat)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin phim thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadPhimLenGridView();
                xoaTrangForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin phim thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Thời lượng và năm sản xuất phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaTrangForm() {
        txtMaPhim.setText("");
        txtTenPhim.setText("");
        txtTheLoai.setText("");
        txtThoiLuong.setText("");
        txtDaoDien.setText("");
        txtNamSanXuat.setText("");
        txtQuocGia.setText("");
        txtMoTa.setText("");
        lblPoster.setIcon(null);
        selectedPoster = null;
        selectedPosterPath = null;
        txtMaPhim.requestFocus();
    }

    private void chonPoster() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            selectedPosterPath = file.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(selectedPosterPath);
            Image scaledImage = imageIcon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
            selectedPoster = new ImageIcon(imageIcon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH));
            lblPoster.setIcon(new ImageIcon(scaledImage));
        }
    }

    private void timKiemPhimTheoTen() {
        String tenCanTim = txtTimKiem.getText().trim();
        if (tenCanTim.isEmpty()) {
            loadPhimLenGridView();
            return;
        }

        pPhimGrid.removeAll();
        phimPanels.clear();
        List<Phim> ketQuaTimKiem = new ArrayList<>();

        for (Phim phim : danhSachPhimTuDB) {
            if (phim.getTenPhim() != null && phim.getTenPhim().toLowerCase().contains(tenCanTim.toLowerCase())) {
                ketQuaTimKiem.add(phim);
            }
        }

        if (ketQuaTimKiem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phim nào có tên chứa '" + tenCanTim + "'!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadPhimLenGridView();
        } else {
            for (Phim phim : ketQuaTimKiem) {
                String posterPath = phim.getPosterPath() != null ? phim.getPosterPath() : "src/img/default_poster.jpg";
                ImageIcon icon;
                File file = new File(posterPath);
                if (file.exists()) {
                    icon = new ImageIcon(posterPath);
                } else {
                    icon = new ImageIcon("src/img/default_poster.jpg");
                }
                Image scaledImage = icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
                JPanel phimPanel = createPhimPanel(
                        phim.getMaPhim() != null ? phim.getMaPhim() : "N/A",
                        phim.getTenPhim() != null ? phim.getTenPhim() : "N/A",
                        phim.getTheLoai() != null ? phim.getTheLoai() : "N/A",
                        String.valueOf(phim.getThoiLuong()),
                        phim.getDaoDien() != null ? phim.getDaoDien() : "N/A",
                        String.valueOf(phim.getNamSanXuat()),
                        phim.getQuocGia() != null ? phim.getQuocGia() : "N/A",
                        phim.getMoTa() != null ? phim.getMoTa() : "",
                        new ImageIcon(scaledImage),
                        posterPath // Truyền thêm posterPath
                );
                phimPanels.add(phimPanel);
                pPhimGrid.add(phimPanel);
            }
        }

        pPhimGrid.revalidate();
        pPhimGrid.repaint();
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
        SwingUtilities.invokeLater(frmPhim::new);
    }
}