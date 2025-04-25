package gui;

import connectSQL.ConnectSQL;
import dao.PhongChieu_Dao;
import entity.PhongChieu;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class frmPhongChieu extends JFrame implements ActionListener, MouseListener {
    private JMenuItem miKhuyenMai;
    private PhongChieu_Dao pc_dao;
    private ConnectSQL connectSQL;
    private JMenuItem miTrangChu;
    private JButton btnTimKiem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnXoaTrang;
    private JButton btnChonPhong;
    private JLabel lblMaPhong;
    private JTextField txtMaPhong;
    private JLabel lblTenPhong;
    private JTextField txtTenPhong;
    private JLabel lblSoGhe;
    private JTextField txtSoGhe;
    private JTextField txtTimKiem;
    private JLabel lblTiemKiem;
    private JButton btnThem;
    private JLabel title;
    private JLabel lblQuanLy;
    private JMenuItem miDangXuat;
    private JMenuItem miThoat;
    private DefaultTableModel tableModel;
    private JTable table;

    public frmPhongChieu() {
        pc_dao = new PhongChieu_Dao();
        try {
            ConnectSQL.getInstance().connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        setTitle("Phòng Chiếu");
        setSize(1600, 830);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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
            if(item.equals("Khuyến mại")){
                miKhuyenMai = menuItem;
                menuItem.addActionListener(this);
            }
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

        JPanel titlePanel = new JPanel(new BorderLayout());
        title = new JLabel("Danh sách phòng chiếu", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(0x4B5AA1));
        titlePanel.setPreferredSize(new Dimension(1600, 80));
        titlePanel.add(title, BorderLayout.CENTER);
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Phần nhập liệu
        JPanel pLeft = new JPanel(new GridBagLayout());
        pLeft.setBorder(BorderFactory.createTitledBorder("Nhập thông tin phòng chiếu"));
        pLeft.setPreferredSize(new Dimension(1120, 180));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        lblMaPhong = new JLabel("Mã phòng:");
        txtMaPhong = new JTextField(50);
        txtMaPhong.setEditable(false);
        
        lblTenPhong = new JLabel("Tên phòng:");
        txtTenPhong = new JTextField(50);
        
        lblSoGhe = new JLabel("Số ghế:");
        txtSoGhe = new JTextField(50);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblMaPhong, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtMaPhong, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblTenPhong, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtTenPhong, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        pLeft.add(lblSoGhe, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pLeft.add(txtSoGhe, gbc);

        lblMaPhong.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTenPhong.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSoGhe.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel pRight = new JPanel(new BorderLayout());
        pRight.setPreferredSize(new Dimension(480, 180));

        JPanel pButtons = new JPanel();
        pButtons.setLayout(new BoxLayout(pButtons, BoxLayout.Y_AXIS));
        pButtons.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnSua = new JButton("Sửa");
        btnXoaTrang = new JButton("Xóa trắng");
        btnChonPhong = new JButton("Chọn phòng");

        btnThem.setFont(new Font("Arial", Font.PLAIN, 16));
        btnXoa.setFont(new Font("Arial", Font.PLAIN, 16));
        btnSua.setFont(new Font("Arial", Font.PLAIN, 16));
        btnXoaTrang.setFont(new Font("Arial", Font.PLAIN, 16));
        btnChonPhong.setFont(new Font("Arial", Font.PLAIN, 16));

        btnThem.setPreferredSize(new Dimension(120, 40));
        btnXoa.setPreferredSize(new Dimension(120, 40));
        btnSua.setPreferredSize(new Dimension(120, 40));
        btnXoaTrang.setPreferredSize(new Dimension(120, 40));
        btnChonPhong.setPreferredSize(new Dimension(120, 40));

        btnThem.setBackground(new Color(34, 139, 34));
        btnThem.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(220, 20, 60));
        btnXoa.setForeground(Color.WHITE);
        btnSua.setBackground(new Color(255, 140, 0));
        btnSua.setForeground(Color.WHITE);
        btnXoaTrang.setBackground(new Color(128, 128, 128));
        btnXoaTrang.setForeground(Color.WHITE);
        btnChonPhong.setBackground(new Color(65, 105, 225)); 
        btnChonPhong.setForeground(Color.WHITE);

        row1.add(btnThem);
        row1.add(btnXoa);
        row2.add(btnSua);
        row2.add(btnXoaTrang);
        row3.add(btnChonPhong);
        pButtons.add(row1);
        pButtons.add(row2);
        pButtons.add(row3);
        pRight.add(pButtons, BorderLayout.CENTER);

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
        String[] headers = {"Mã Phòng", "Tên Phòng", "Số Ghế"};
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
        btnChonPhong.addActionListener(this);
        btnTimKiem.addActionListener(this);
        table.addMouseListener(this);
        DocDuLieuDatabaseVaoTable();
        setVisible(true);
    }

    private JMenuItem createMenuItem(String name) {
        return new JMenuItem(name);
    }

    public void DocDuLieuDatabaseVaoTable() {
        tableModel.setRowCount(0); 
        
        PhongChieu_Dao dao = new PhongChieu_Dao();
        List<PhongChieu> dsPhong = dao.getAllPhongChieu();
        
        for (PhongChieu pc : dsPhong) {
            String[] row = {
                String.valueOf(pc.getMaPhong()), 
                pc.getTenPhong(), 
                String.valueOf(pc.getSoGhe())
            };
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
            ThemPhongChieu();
        } else if (o == btnXoa) {
            XoaPhongChieu();
        } else if (o == btnSua) {
            SuaPhongChieu();
        } else if (o == btnXoaTrang) {
            XoaTrang();
        }else if (o == btnChonPhong) {
            ChonPhong();
        } else if (o == btnTimKiem) {
            TimKiem();
        } else if (o == miTrangChu) {
            dispose();
            new frmTrangChu();
        } else if(o == miKhuyenMai){
            dispose();
            new frmKhuyenMai();
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

    public void ThemPhongChieu() {
        String tenPhong = txtTenPhong.getText().trim();
        String soGheText = txtSoGhe.getText().trim();
        
        if (tenPhong.isEmpty() || soGheText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin phòng chiếu!");
            return;
        }
        
        int soGhe;
        try {
            soGhe = Integer.parseInt(soGheText);
            if (soGhe <= 0) {
                JOptionPane.showMessageDialog(this, "Số ghế phải là số nguyên dương!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số ghế phải là số nguyên!");
            return;
        }
        
        PhongChieu phongChieu = new PhongChieu(tenPhong, soGhe);
        
        if (pc_dao.themPhongChieu(phongChieu)) {
            JOptionPane.showMessageDialog(this, "Thêm phòng chiếu thành công!");
            XoaTrang();
            DocDuLieuDatabaseVaoTable(); 
        } else {
            JOptionPane.showMessageDialog(this, "Thêm phòng chiếu thất bại!");
        }
    }

    public void XoaPhongChieu() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int maPhong = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa phòng chiếu này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (pc_dao.xoaPhongChieu(maPhong)) {
                    JOptionPane.showMessageDialog(this, "Xóa phòng chiếu thành công!");
                    tableModel.removeRow(row);
                    XoaTrang();
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "Không thể xóa phòng chiếu! Có thể phòng đang có lịch chiếu liên quan.",
                            "Lỗi xóa",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng chiếu cần xóa!");
        }
    }

    public void SuaPhongChieu() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int maPhong = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            String tenPhong = txtTenPhong.getText().trim();
            String soGheText = txtSoGhe.getText().trim();
            
            if (tenPhong.isEmpty() || soGheText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin phòng chiếu!");
                return;
            }
            
            int soGhe;
            try {
                soGhe = Integer.parseInt(soGheText);
                if (soGhe <= 0) {
                    JOptionPane.showMessageDialog(this, "Số ghế phải là số nguyên dương!");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số ghế phải là số nguyên!");
                return;
            }
            
            PhongChieu phongChieu = new PhongChieu(maPhong, tenPhong, soGhe);
            
            if (pc_dao.capNhatPhongChieu(phongChieu)) {
                JOptionPane.showMessageDialog(this, "Cập nhật phòng chiếu thành công!");
                tableModel.setValueAt(String.valueOf(maPhong), row, 0);
                tableModel.setValueAt(tenPhong, row, 1);
                tableModel.setValueAt(String.valueOf(soGhe), row, 2);
                XoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật phòng chiếu thất bại!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng chiếu cần sửa!");
        }
    }

    public void XoaTrang() {
        txtMaPhong.setText("");
        txtTenPhong.setText("");
        txtSoGhe.setText("");
        txtTimKiem.setText("");
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        sorter.setRowFilter(null);
        txtTenPhong.requestFocus();
    }

    public void ChonPhong() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int maPhong;
            if (table.getRowSorter() != null) {
                int modelRow = table.convertRowIndexToModel(row);
                maPhong = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());
            } else {
                maPhong = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            }
            
            dispose(); 
            new frmGhe(maPhong); 
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng chiếu!");
        }
    }

    public void TimKiem() {
        String text = txtTimKiem.getText().trim().toLowerCase();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!");
            return;
        }
        
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        
        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter("(?i)" + text, 0, 1);
        sorter.setRowFilter(filter);
        
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
            JOptionPane.showMessageDialog(this, "Đã tìm thấy " + table.getRowCount() + " kết quả!");
            
            int modelRow = table.convertRowIndexToModel(0);
            txtMaPhong.setText(tableModel.getValueAt(modelRow, 0).toString());
            txtTenPhong.setText(tableModel.getValueAt(modelRow, 1).toString());
            txtSoGhe.setText(tableModel.getValueAt(modelRow, 2).toString());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào!");
            sorter.setRowFilter(null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String maPhong = tableModel.getValueAt(row, 0).toString();
            String tenPhong = tableModel.getValueAt(row, 1).toString();
            String soGhe = tableModel.getValueAt(row, 2).toString();
            
            txtMaPhong.setText(maPhong);
            txtTenPhong.setText(tenPhong);
            txtSoGhe.setText(soGhe);
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
        new frmPhongChieu();
    }
}
