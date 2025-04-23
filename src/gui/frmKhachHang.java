package gui;

import connectSQL.ConnectSQL;
import dao.KhachHang_Dao;
import entity.KhachHang;
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

public class frmKhachHang extends JFrame implements ActionListener, MouseListener {
    private KhachHang_Dao kh_dao;
    private ConnectSQL connectSQL;
    private JMenuItem miTrangChu;
    private JButton btnTimKiem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXoaTrang;
    private JLabel lblMaKH;
    private JTextField txtMaKH;
    private JLabel lblTenKH;
    private JTextField txtTenKH;
    private JLabel lbldate;
    private JFormattedTextField txtDate;
    private JLabel lblDiaChi;
    private JTextField txtDiaChi;
    private JLabel lblGioiTinh;
    private JRadioButton rbNam;
    private JRadioButton rbNu;
    private JRadioButton rbKhac;
    private ButtonGroup genderGroup;
    private JLabel lblSoDT;
    private JTextField txtSoDT;
    private JTextField txtTimKiem;
    private JLabel lblTiemKiem;
    private JButton btnThem;
    private JLabel title;
    private JLabel lblQuanLy;
    private JMenuItem miDangXuat;
    private JMenuItem miThoat;
    private DefaultTableModel tableModel;
    private JTable table;

    public frmKhachHang() {
        kh_dao = new KhachHang_Dao();
        connectSQL.getInstance().connect();
        setTitle("Khách Hàng");
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
        title = new JLabel("Danh sách khách hàng", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(0x4B5AA1));
        titlePanel.setPreferredSize(new Dimension(1600, 80));
        titlePanel.add(title, BorderLayout.CENTER);
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Phần nhập liệu
        JPanel pLeft = new JPanel(new GridBagLayout());
        pLeft.setBorder(BorderFactory.createTitledBorder("Nhập thông tin khách hàng"));
        pLeft.setPreferredSize(new Dimension(1120, 180));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        lblMaKH = new JLabel("Mã khách hàng:");
        txtMaKH = new JTextField(50);
        lblTenKH = new JLabel("Tên khách hàng:");
        txtTenKH = new JTextField(50);
        lbldate = new JLabel("Ngày sinh:");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtDate = new JFormattedTextField(new DateFormatter(dateFormat));
        txtDate.setColumns(50);
        txtDate.setFont(new Font("Arial", Font.PLAIN, 16));
        txtDate.setToolTipText("Nhập ngày sinh theo định dạng dd/MM/yyyy");
        lblDiaChi = new JLabel("Địa chỉ:");
        txtDiaChi = new JTextField(50);
        lblGioiTinh = new JLabel("Giới tính:");
        genderGroup = new ButtonGroup();
        rbNam = new JRadioButton("Nam");
        rbNu = new JRadioButton("Nữ");
        rbKhac = new JRadioButton("Khác");
        genderGroup.add(rbNam);
        genderGroup.add(rbNu);
        genderGroup.add(rbKhac);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(rbNam);
        genderPanel.add(rbNu);
        genderPanel.add(rbKhac);
        lblSoDT = new JLabel("Số điện thoại:");
        txtSoDT = new JTextField(50);

        // Hàng 1: Mã KH
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblMaKH, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtMaKH, gbc);

        // Hàng 2: Tên KH
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblTenKH, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtTenKH, gbc);

        // Hàng 3: Ngày sinh
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lbldate, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtDate, gbc);

        // Hàng 4: Địa chỉ
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblDiaChi, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtDiaChi, gbc);

        // Hàng 5: Giới tính
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblGioiTinh, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(genderPanel, gbc);

        // Hàng 6: Số ĐT
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblSoDT, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtSoDT, gbc);

        lblMaKH.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTenKH.setFont(new Font("Arial", Font.PLAIN, 16));
        lbldate.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDiaChi.setFont(new Font("Arial", Font.PLAIN, 16));
        lblGioiTinh.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSoDT.setFont(new Font("Arial", Font.PLAIN, 16));
        rbNam.setFont(new Font("Arial", Font.PLAIN, 16));
        rbNu.setFont(new Font("Arial", Font.PLAIN, 16));
        rbKhac.setFont(new Font("Arial", Font.PLAIN, 16));

        // Phần thao tác
        JPanel pRight = new JPanel(new BorderLayout());
        pRight.setPreferredSize(new Dimension(480, 180));

        JPanel pButtons = new JPanel();
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.Y_AXIS));
        pButtons.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Hàng 1: "Thêm" và "Xóa"
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

        // Đảm bảo kích thước đồng đều cho các nút
        btnThem.setPreferredSize(new Dimension(120, 40));
        btnXoa.setPreferredSize(new Dimension(120, 40));
        btnSua.setPreferredSize(new Dimension(120, 40));
        btnXoaTrang.setPreferredSize(new Dimension(120, 40));

        // Thêm màu cho các nút
        btnThem.setBackground(new Color(34, 139, 34));
        btnThem.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(220, 20, 60));
        btnXoa.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(255, 140, 0));
        btnSua.setForeground(Color.WHITE);
        btnXoaTrang.setBackground(new Color(128, 128, 128));
        btnXoaTrang.setForeground(Color.WHITE);

        //Hang 1
        row1.add(btnThem);
        row1.add(btnXoa);
        //Hang 2
        row2.add(btnSua);
        row2.add(btnXoaTrang);
        pButtons.add(row1);
        pButtons.add(row2);
        pRight.add(pButtons, BorderLayout.CENTER);

        // Kết hợp pLeft và pRight trong pTop
        JPanel pTop = new JPanel(new BorderLayout());
        pTop.setPreferredSize(new Dimension(1600, 250));
        JPanel pInputAndOps = new JPanel(new BorderLayout());
        pInputAndOps.add(pLeft, BorderLayout.WEST);
        pInputAndOps.add(pRight, BorderLayout.EAST);
        pTop.add(pInputAndOps, BorderLayout.CENTER);

        pRight.setBorder(BorderFactory.createTitledBorder("Chọn tác vụ"));

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

        JPanel pSouth = new JPanel(new BorderLayout());
        pSouth.setPreferredSize(new Dimension(1600, 650));
        pSouth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pTopAndSearch = new JPanel(new BorderLayout());
        pTopAndSearch.add(pTop, BorderLayout.NORTH);
        pTopAndSearch.add(searchPanel, BorderLayout.CENTER);

        JPanel pBottom = new JPanel(new BorderLayout());
        String[] headers = {"Mã KH", "Tên KH", "Ngày sinh", "Địa chỉ", "Giới tính", "Số ĐT"};
        tableModel = new DefaultTableModel(headers, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(300);

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

    public void DocDuLieuDatabaseVaoTable() {
        KhachHang_Dao ds = new KhachHang_Dao();
        List<KhachHang> dskh = ds.getalltbkhachhang();
        for (KhachHang kh : dskh) {
            String[] row = {kh.getMaKhachHang(), kh.getTenKhachHang(), kh.getNgaySinh().toString(), kh.getDiaChi(), kh.getGioiTinh(), kh.getSoDienThoai()};
            tableModel.addRow(row);
        }
        table.setModel(tableModel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == miDangXuat) {
            DangXuat();
        } else if (o == miThoat) {
            Thoat();
        } else if (o == btnThem) {
            ThemKH();
        } else if (o == btnXoa) {
            Xoa();
        } else if (o == btnSua) {
            Sua();
        } else if (o == btnXoaTrang) {
            XoaTrang();
        } else if (o == btnTimKiem) {
            TiemKiem();
        } else if (o == miTrangChu) {
            dispose();
            new frmTrangChu();
        }
    }

    public void DangXuat() {
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
    }

    public void Thoat() {
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn thoát?",
                "Xác nhận thoát",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public void ThemKH() {
        String maKH = txtMaKH.getText().trim();
        String tenKH = txtTenKH.getText().trim();
        String ngaySinh = txtDate.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String gioiTinh = rbNam.isSelected() ? "Nam" : rbNu.isSelected() ? "Nữ" : rbKhac.isSelected() ? "Khác" : "";
        String soDT = txtSoDT.getText().trim();
        if (maKH.isEmpty() || tenKH.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty() || gioiTinh.isEmpty() || soDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!soDT.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là 10 chữ số!");
            return;
        }
        KhachHang kh = new KhachHang(maKH, tenKH, LocalDate.parse(ngaySinh, DateTimeFormatter.ofPattern("dd/MM/yyyy")), diaChi, gioiTinh, soDT);
        if (kh_dao.isExits(maKH)) {
            JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!");
            return;
        }
        try {
            kh_dao.createKH(kh);
            String[] rowdata = {kh.getMaKhachHang(), kh.getTenKhachHang(), kh.getNgaySinh().toString(), kh.getDiaChi(), kh.getGioiTinh(), kh.getSoDienThoai()};
            tableModel.addRow(rowdata);
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không thể thêm khách hàng!");
            e.printStackTrace();
        }
        XoaTrang();
    }

    public void Xoa() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maKH = (String) tableModel.getValueAt(row, 0);
                if (kh_dao.delete(maKH)) {
                    tableModel.removeRow(row);
                    JOptionPane.showMessageDialog(this, "Đã xóa khách hàng!");
                    XoaTrang();
                    return;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
        }
    }

    public void Sua() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String maKH = txtMaKH.getText().trim();
            String tenKH = txtTenKH.getText().trim();
            String ngaySinh = txtDate.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String gioiTinh = rbNam.isSelected() ? "Nam" : rbNu.isSelected() ? "Nữ" : rbKhac.isSelected() ? "Khác" : "";
            String soDT = txtSoDT.getText().trim();

            if (maKH.isEmpty() || tenKH.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty() || gioiTinh.isEmpty() || soDT.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            if (!soDT.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải là 10 chữ số!");
                return;
            }

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (i != selectedRow && tableModel.getValueAt(i, 0).equals(maKH)) {
                    JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!");
                    return;
                }
            }

            tableModel.setValueAt(maKH, selectedRow, 0);
            tableModel.setValueAt(tenKH, selectedRow, 1);
            tableModel.setValueAt(ngaySinh, selectedRow, 2);
            tableModel.setValueAt(diaChi, selectedRow, 3);
            tableModel.setValueAt(gioiTinh, selectedRow, 4);
            tableModel.setValueAt(soDT, selectedRow, 5);
            XoaTrang();
            JOptionPane.showMessageDialog(this, "Đã sửa khách hàng!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!");
        }
    }

    public void XoaTrang() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtDate.setText("");
        txtDiaChi.setText("");
        genderGroup.clearSelection();
        txtSoDT.setText("");
        txtTimKiem.setText("");
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        sorter.setRowFilter(null);
        txtMaKH.requestFocus();
    }

    public void TiemKiem() {
        String text = txtTimKiem.getText().trim().toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        if (!text.isEmpty()) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            if (sorter.getViewRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào!");
                return;
            }
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
            txtMaKH.setText(tableModel.getValueAt(row, 0).toString());
            txtTenKH.setText(tableModel.getValueAt(row, 1).toString());
            txtDate.setText(tableModel.getValueAt(row, 2).toString());
            txtDiaChi.setText(tableModel.getValueAt(row, 3).toString());
            String gioiTinh = tableModel.getValueAt(row, 4).toString();
            if (gioiTinh.equals("Nam")) rbNam.setSelected(true);
            else if (gioiTinh.equals("Nữ")) rbNu.setSelected(true);
            else rbKhac.setSelected(true);
            txtSoDT.setText(tableModel.getValueAt(row, 5).toString());
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
        new frmKhachHang();
    }
}