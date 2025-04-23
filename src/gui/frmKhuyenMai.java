package gui;

import connectSQL.ConnectSQL;
import dao.KhuyenMai_Dao;
import entity.KhuyenMai;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class frmKhuyenMai extends JFrame implements ActionListener, MouseListener {
    private final JLabel lblTenKM;
    private final JTextField txtTenKM;
    private final JLabel lblPhanTramGiam;
    private final JTextField txtPhanTramGiam;
    private final JLabel lblNgayBatDau;
    private final JLabel lblNgayKetThuc;
    private JMenuItem miKhachHang;
    private JTextField txtMaKM;
    private JLabel lblMaKM;
    private KhuyenMai_Dao km_dao;
    private ConnectSQL connectSQL;
    private JMenuItem miTrangChu;
    private JButton btnTimKiem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXoaTrang;
    private JFormattedTextField txtNgayBatDau;
    private JFormattedTextField txtNgayKetThuc;
    private JTextField txtTimKiem;
    private JLabel lblTiemKiem;
    private JButton btnThem;
    private JLabel title;
    private JLabel lblQuanLy;
    private JMenuItem miDangXuat;
    private JMenuItem miThoat;
    private DefaultTableModel tableModel;
    private JTable table;

    public frmKhuyenMai() {
        km_dao = new KhuyenMai_Dao();
        connectSQL.getInstance().connect();
        System.out.println("Kết nối thành công!");
        setTitle("Khuyến Mãi");
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
            if (item.equals("Khách hàng")) {
                miKhachHang = menuItem;
                menuItem.addActionListener(this);
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
        title = new JLabel("Danh sách khuyến mãi", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(0x4B5AA1));
        titlePanel.setPreferredSize(new Dimension(1600, 80));
        titlePanel.add(title, BorderLayout.CENTER);
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Phần nhập liệu
        JPanel pLeft = new JPanel(new GridBagLayout());
        pLeft.setBorder(BorderFactory.createTitledBorder("Nhập thông tin khuyến mãi"));
        pLeft.setPreferredSize(new Dimension(1120, 180));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        lblMaKM = new JLabel("Mã khuyến mãi:");
        txtMaKM = new JTextField(50);
        lblTenKM = new JLabel("Tên khuyến mãi:");
        txtTenKM = new JTextField(50);
        lblPhanTramGiam = new JLabel("Phần trăm giảm:");
        txtPhanTramGiam = new JTextField(50);
        lblNgayBatDau = new JLabel("Ngày bắt đầu:");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtNgayBatDau = new JFormattedTextField(new DateFormatter(dateFormat));
        txtNgayBatDau.setColumns(50);
        txtNgayBatDau.setFont(new Font("Arial", Font.PLAIN, 16));
        txtNgayBatDau.setToolTipText("Nhập ngày bắt đầu theo định dạng dd/MM/yyyy");
        lblNgayKetThuc = new JLabel("Ngày kết thúc:");
        txtNgayKetThuc = new JFormattedTextField(new DateFormatter(dateFormat));
        txtNgayKetThuc.setColumns(50);
        txtNgayKetThuc.setFont(new Font("Arial", Font.PLAIN, 16));
        txtNgayKetThuc.setToolTipText("Nhập ngày kết thúc theo định dạng dd/MM/yyyy");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblMaKM, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtMaKM, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblTenKM, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtTenKM, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblPhanTramGiam, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtPhanTramGiam, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblNgayBatDau, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtNgayBatDau, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblNgayKetThuc, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtNgayKetThuc, gbc);

        lblMaKM.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTenKM.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPhanTramGiam.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNgayBatDau.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNgayKetThuc.setFont(new Font("Arial", Font.PLAIN, 16));

        // Phần thao tác
        JPanel pRight = new JPanel(new BorderLayout());
        pRight.setPreferredSize(new Dimension(480, 200));

        JPanel pButtons = new JPanel();
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.Y_AXIS));
        pButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnSua = new JButton("Sửa");
        btnXoaTrang = new JButton("Xóa trắng");

        btnThem.setFont(new Font("Arial", Font.PLAIN, 16));
        btnXoa.setFont(new Font("Arial", Font.PLAIN, 16));
        btnSua.setFont(new Font("Arial", Font.PLAIN, 16));
        btnXoaTrang.setFont(new Font("Arial", Font.PLAIN, 16));

        btnThem.setPreferredSize(new Dimension(120, 40));
        btnXoa.setPreferredSize(new Dimension(120, 40));
        btnSua.setPreferredSize(new Dimension(120, 40));
        btnXoaTrang.setPreferredSize(new Dimension(120, 40));

        btnThem.setBackground(new Color(34, 139, 34));
        btnThem.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(220, 20, 60));
        btnXoa.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(255, 140, 0));
        btnSua.setForeground(Color.WHITE);
        btnXoaTrang.setBackground(new Color(128, 128, 128));
        btnXoaTrang.setForeground(Color.WHITE);

        row1.add(btnThem);
        row1.add(btnXoa);
        row2.add(btnSua);
        row2.add(btnXoaTrang);

        pButtons.add(row1);
        pButtons.add(Box.createVerticalStrut(10));
        pButtons.add(row2);

        pButtons.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        pRight.add(pButtons, BorderLayout.CENTER);

        // Kết hợp pLeft và pRight trong pTop
        JPanel pTop = new JPanel(new BorderLayout());
        pTop.setPreferredSize(new Dimension(1600, 270));
        JPanel pInputAndOps = new JPanel(new BorderLayout());
        pInputAndOps.add(pLeft, BorderLayout.WEST);
        pInputAndOps.add(pRight, BorderLayout.EAST);
        pTop.add(pInputAndOps, BorderLayout.CENTER);

        // Phần tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblTiemKiem = new JLabel("Tìm kiếm:");
        txtTimKiem = new JTextField(40);
        btnTimKiem = new JButton("Tìm kiếm");

        lblTiemKiem.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 16));
        btnTimKiem.setFont(new Font("Arial", Font.PLAIN, 16));
        btnTimKiem.setPreferredSize(new Dimension(100, 40));

        btnTimKiem.setBackground(new Color(30, 144, 255));
        btnTimKiem.setForeground(Color.WHITE);

        searchPanel.add(lblTiemKiem);
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);

        // Thêm pTop và searchPanel vào pSouth
        JPanel pSouth = new JPanel(new BorderLayout());
        pSouth.setPreferredSize(new Dimension(1600, 630));
        pSouth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pTopAndSearch = new JPanel(new BorderLayout());
        pTopAndSearch.add(pTop, BorderLayout.NORTH);
        pTopAndSearch.add(searchPanel, BorderLayout.CENTER);

        JPanel pBottom = new JPanel(new BorderLayout());
        String[] headers = {"Mã KM", "Tên KM", "Phần trăm giảm", "Ngày bắt đầu", "Ngày kết thúc"};
        tableModel = new DefaultTableModel(headers, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                }
                return c;
            }
        });

        pBottom.add(new JScrollPane(table), BorderLayout.CENTER);

        pSouth.add(pTopAndSearch, BorderLayout.NORTH);
        pSouth.add(pBottom, BorderLayout.CENTER);

        getContentPane().add(pSouth, BorderLayout.CENTER);

        btnThem.addActionListener(this);
        btnXoa.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoaTrang.addActionListener(this);
        btnTimKiem.addActionListener(this);
        table.addMouseListener(this);
        DocDuLieuDatabaseVaoTable();
        setVisible(true);
    }

    private JMenuItem createMenuItem(String name) {
        return new JMenuItem(name);
    }

    private JMenuItem createMenuItem(String name, ActionListener action) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(action);
        return item;
    }

    public void DocDuLieuDatabaseVaoTable() {
        List<KhuyenMai> dskm = km_dao.getalltbKhuyenMai();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (KhuyenMai km : dskm) {
            String[] row = {
                    km.getMaKhuyenMai(),
                    km.getTenKhuyenMai(),
                    String.valueOf(km.getPhanTramGiam()),
                    km.getNgayGioDat().format(formatter),
                    km.getNgayKetThuc().format(formatter)
            };
            tableModel.addRow(row);
        }
        table.setModel(tableModel);
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
        } else if (o == miKhachHang) {
            dispose();
            new frmKhachHang();
        } else if (o == miTrangChu) {
            dispose();
            new frmTrangChu();
        } else if (o == btnThem) {
            themKM();
        } else if (o == btnXoa) {
            Xoa();
        } else if (o == btnSua) {
            Sua();
        } else if (o == btnXoaTrang) {
            XoaTrang();
        } else if (o == btnTimKiem) {
            TiemKiem();
        }
    }

    public void themKM() {
        String maKM = txtMaKM.getText().trim();
        String tenKM = txtTenKM.getText().trim();
        String phanTramGiamStr = txtPhanTramGiam.getText().trim();
        String ngayBatDau = txtNgayBatDau.getText().trim();
        String ngayKetThuc = txtNgayKetThuc.getText().trim();

        // Kiểm tra trường trống
        if (maKM.isEmpty() || tenKM.isEmpty() || phanTramGiamStr.isEmpty() || ngayBatDau.isEmpty() || ngayKetThuc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra mã khuyến mãi duy nhất
        if (km_dao.isExits(maKM)) {
            JOptionPane.showMessageDialog(this, "Mã khuyến mãi đã tồn tại!");
            return;
        }

        double phanTramGiam;
        try {
            phanTramGiam = Double.parseDouble(phanTramGiamStr);
            if (phanTramGiam < 0 || phanTramGiam > 100) {
                JOptionPane.showMessageDialog(this, "Phần trăm giảm phải từ 0 đến 100!");
                return;
            }
        } catch (NumberFormatException e) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate, endDate;
        try {
            startDate = LocalDate.parse(ngayBatDau, formatter);
            endDate = LocalDate.parse(ngayKetThuc, formatter);
            if (endDate.isBefore(startDate)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu!");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày không đúng định dạng dd/MM/yyyy!");
            return;
        }

        KhuyenMai km = new KhuyenMai(maKM, tenKM, phanTramGiam, startDate, endDate);
        try {
            if (km_dao.createKM(km)) {
                String[] rowData = {maKM, tenKM, String.valueOf(phanTramGiam), ngayBatDau, ngayKetThuc};
                tableModel.addRow(rowData);
                JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
                XoaTrang();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không thể thêm khuyến mãi!");
            e.printStackTrace();
        }
    }

    public void Xoa() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khuyến mãi này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maKM = (String) tableModel.getValueAt(row, 0);
                if (km_dao.deleteKM(maKM)) {
                    tableModel.removeRow(row);
                    JOptionPane.showMessageDialog(this, "Đã xóa khuyến mãi!");
                    XoaTrang();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi: Không thể xóa khuyến mãi!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi để xóa!");
        }
    }

    public void Sua() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String maKM = txtMaKM.getText().trim();
            String tenKM = txtTenKM.getText().trim();
            String phanTramGiamStr = txtPhanTramGiam.getText().trim();
            String ngayBatDau = txtNgayBatDau.getText().trim();
            String ngayKetThuc = txtNgayKetThuc.getText().trim();

            if (maKM.isEmpty() || tenKM.isEmpty() || phanTramGiamStr.isEmpty() || ngayBatDau.isEmpty() || ngayKetThuc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (i != selectedRow && tableModel.getValueAt(i, 0).equals(maKM)) {
                    JOptionPane.showMessageDialog(this, "Mã khuyến mãi đã tồn tại!");
                    return;
                }
            }

            // Kiểm tra phần trăm giảm
            int phanTramGiam;
            try {
                phanTramGiam = Integer.parseInt(phanTramGiamStr);
                if (phanTramGiam < 0 || phanTramGiam > 100) {
                    JOptionPane.showMessageDialog(this, "Phần trăm giảm phải từ 0 đến 100!");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Phần trăm giảm phải là số nguyên!");
                return;
            }

            // Kiểm tra định dạng ngày và so sánh ngày
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate startDate, endDate;
            try {
                startDate = LocalDate.parse(ngayBatDau, formatter);
                endDate = LocalDate.parse(ngayKetThuc, formatter);
                if (endDate.isBefore(startDate)) {
                    JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu!");
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ngày không đúng định dạng dd/MM/yyyy!");
                return;
            }

            tableModel.setValueAt(maKM, selectedRow, 0);
            tableModel.setValueAt(tenKM, selectedRow, 1);
            tableModel.setValueAt(phanTramGiam, selectedRow, 2);
            tableModel.setValueAt(ngayBatDau, selectedRow, 3);
            tableModel.setValueAt(ngayKetThuc, selectedRow, 4);

            KhuyenMai km = new KhuyenMai(maKM, tenKM, phanTramGiam, startDate, endDate);
            if (km_dao.updateKM(km)) {
                JOptionPane.showMessageDialog(this, "Đã sửa khuyến mãi!");
                XoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi: Không thể sửa khuyến mãi!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi để sửa!");
        }
    }

    public void XoaTrang() {
        txtMaKM.setText("");
        txtTenKM.setText("");
        txtPhanTramGiam.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtTimKiem.setText("");
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        sorter.setRowFilter(null);
        txtMaKM.requestFocus();
    }

    public void TiemKiem() {
        String text = txtTimKiem.getText().trim().toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        if (!text.isEmpty()) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào!");
                return;
            }
            table.setRowSelectionInterval(0, 0);
            int modelRow = sorter.convertRowIndexToModel(0);
            txtMaKM.setText(tableModel.getValueAt(modelRow, 0).toString());
            txtTenKM.setText(tableModel.getValueAt(modelRow, 1).toString());
            txtPhanTramGiam.setText(tableModel.getValueAt(modelRow, 2).toString());
            txtNgayBatDau.setText(tableModel.getValueAt(modelRow, 3).toString());
            txtNgayKetThuc.setText(tableModel.getValueAt(modelRow, 4).toString());
            JOptionPane.showMessageDialog(this, "Đã tìm thấy kết quả!");
        } else {
            sorter.setRowFilter(null);
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaKM.setText(tableModel.getValueAt(row, 0).toString());
            txtTenKM.setText(tableModel.getValueAt(row, 1).toString());
            txtPhanTramGiam.setText(tableModel.getValueAt(row, 2).toString());
            txtNgayBatDau.setText(tableModel.getValueAt(row, 3).toString());
            txtNgayKetThuc.setText(tableModel.getValueAt(row, 4).toString());
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
        new frmKhuyenMai();
    }
}