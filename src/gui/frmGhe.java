package gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

import dao.Ghe_Dao;
import dao.PhongChieu_Dao;
import entity.Ghe;
import entity.PhongChieu;
import entity.Ghe.LoaiGhe;
import entity.Ghe.TrangThaiGhe;

public class frmGhe extends JFrame implements ActionListener, MouseListener {
    private JMenuItem miKhuyenMai;
    private JMenuItem miTrangChu;
    private JMenuItem miDangXuat;
    private JMenuItem miThoat;
    private JButton btnLuu;
    private JButton btnTroVe;
    private JLabel title;
    private JLabel lblQuanLy;
    private JPanel pnlGheContainer;
    private JPanel pnlGheGrid;
    private int maPhong;
    private PhongChieu phongChieu;
    private List<Ghe> danhSachGhe;
    private Ghe_Dao ghe_dao;
    private PhongChieu_Dao phongChieu_dao;
    private JLabel lblInfoPhong;
    private JLabel lblMoTa;
    private JButton[][] gheButtons;
    private int soHang;
    private int soCot;
    private List<Ghe> gheDaChon;

    public frmGhe(int maPhong) {
        this.maPhong = maPhong;
        this.gheDaChon = new ArrayList<>();
        this.ghe_dao = new Ghe_Dao();
        this.phongChieu_dao = new PhongChieu_Dao();
        
        this.phongChieu = phongChieu_dao.timPhongChieuTheoMa(maPhong);
        this.danhSachGhe = ghe_dao.getAllGhe();
        
        List<Ghe> ghePhongHienTai = new ArrayList<>();
        for (Ghe ghe : danhSachGhe) {
            if (ghe.getPhongChieu().getMaPhong() == maPhong) {
                ghePhongHienTai.add(ghe);
            }
        }
        this.danhSachGhe = ghePhongHienTai;
        
        tinhToanHangVaCot();
        
        setTitle("Quản lý ghế ngồi - Phòng " + phongChieu.getTenPhong());
        setSize(1600, 830);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        taoMenuBar();
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        title = new JLabel("Quản lý ghế - Phòng " + phongChieu.getTenPhong(), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(0x4B5AA1));
        titlePanel.setPreferredSize(new Dimension(1600, 80));
        titlePanel.add(title, BorderLayout.CENTER);
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phòng"));
        infoPanel.setPreferredSize(new Dimension(1600, 80));
        
        lblInfoPhong = new JLabel("Phòng: " + phongChieu.getTenPhong() + " - Số ghế: " + phongChieu.getSoGhe());
        lblInfoPhong.setFont(new Font("Arial", Font.BOLD, 16));
        lblInfoPhong.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        lblMoTa = new JLabel("Chú thích: □ Ghế thường | ◆ Ghế VIP | ✓ Ghế đang chọn | X Ghế đã đặt");
        lblMoTa.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMoTa.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        infoPanel.add(lblInfoPhong, BorderLayout.NORTH);
        infoPanel.add(lblMoTa, BorderLayout.CENTER);
        
        pnlGheContainer = new JPanel(new BorderLayout());
        pnlGheContainer.setBorder(BorderFactory.createTitledBorder("Sơ đồ ghế"));
        
        JPanel pnlScreen = new JPanel();
        pnlScreen.setPreferredSize(new Dimension(1600, 50));
        pnlScreen.setBackground(Color.DARK_GRAY);
        JLabel lblScreen = new JLabel("MÀN HÌNH", SwingConstants.CENTER);
        lblScreen.setForeground(Color.WHITE);
        lblScreen.setFont(new Font("Arial", Font.BOLD, 20));
        pnlScreen.add(lblScreen);
        
        pnlGheGrid = new JPanel();
        pnlGheGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        taoLuoiGhe();
        
        pnlGheContainer.add(pnlScreen, BorderLayout.NORTH);
        pnlGheContainer.add(new JScrollPane(pnlGheGrid), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setPreferredSize(new Dimension(1600, 60));
        
        btnLuu = new JButton("Lưu thay đổi");
        btnLuu.setFont(new Font("Arial", Font.PLAIN, 16));
        btnLuu.setBackground(new Color(34, 139, 34));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setPreferredSize(new Dimension(150, 40));
        
        btnTroVe = new JButton("Trở về");
        btnTroVe.setFont(new Font("Arial", Font.PLAIN, 16));
        btnTroVe.setBackground(new Color(128, 128, 128));
        btnTroVe.setForeground(Color.WHITE);
        btnTroVe.setPreferredSize(new Dimension(150, 40));
        
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnTroVe);
        
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(pnlGheContainer, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        btnLuu.addActionListener(this);
        btnTroVe.addActionListener(this);
        
        setVisible(true);
    }
    
    private void tinhToanHangVaCot() {
        // Mặc định số hàng là 8 (A-H) và số cột là phongChieu.getSoGhe() / 8 (hoặc tính theo danhSachGhe)
        if (danhSachGhe.isEmpty()) {
            this.soHang = 8; // Mặc định 8 hàng (A-H)
            this.soCot = phongChieu.getSoGhe() / soHang;
            if (phongChieu.getSoGhe() % soHang != 0) {
                this.soCot++; // Làm tròn lên nếu không chia hết
            }
        } else {
            // Tìm hàng cao nhất và cột lớn nhất từ tên ghế
            int maxHang = 0;
            int maxCot = 0;
            
            for (Ghe ghe : danhSachGhe) {
                String tenGhe = ghe.getTenGhe();
                if (tenGhe != null && tenGhe.length() >= 2) {
                    char hangChar = tenGhe.charAt(0);
                    int hangIndex = hangChar - 'A' + 1;
                    
                    try {
                        int cotIndex = Integer.parseInt(tenGhe.substring(1));
                        if (hangIndex > maxHang) maxHang = hangIndex;
                        if (cotIndex > maxCot) maxCot = cotIndex;
                    } catch (NumberFormatException e) {
                        // Bỏ qua nếu không phải số
                    }
                }
            }
            
            this.soHang = Math.max(maxHang, 1);
            this.soCot = Math.max(maxCot, 1);
        }
    }
    
    private void taoMenuBar() {
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
            JMenuItem menuItem = new JMenuItem(item);
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
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.setFont(new Font("Arial", Font.PLAIN, 18));
            menuItem.addActionListener(this);
            if(item.equals("Khuyến mại")){
                miKhuyenMai = menuItem;
            }
            menuDanhMuc.add(menuItem);
        }

        String[] xuLyItems = {"Vé", "Phòng chiếu", "Đồ ăn"};
        for (String item : xuLyItems) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.setFont(new Font("Arial", Font.PLAIN, 18));
            menuItem.addActionListener(this);
            menuXuLy.add(menuItem);
        }

        JMenuItem doanhThuItem = new JMenuItem("Doanh thu");
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
    }
    
    private void taoLuoiGhe() {
        // Tạo grid layout cho ghế
        GridLayout gridLayout = new GridLayout(soHang + 1, soCot + 1, 10, 10); 
        pnlGheGrid.setLayout(gridLayout);
        
        gheButtons = new JButton[soHang][soCot];
        
        // Thêm ô góc trống
        pnlGheGrid.add(new JLabel(""));
        
        // Thêm số cột ở hàng đầu tiên
        for (int c = 0; c < soCot; c++) {
            JLabel lblCol = new JLabel(String.valueOf(c + 1), SwingConstants.CENTER);
            lblCol.setFont(new Font("Arial", Font.BOLD, 14));
            pnlGheGrid.add(lblCol);
        }
        
        // Tạo các hàng với chữ cái và ghế
        for (int r = 0; r < soHang; r++) {
            // Thêm chữ cái đầu hàng
            JLabel lblRow = new JLabel(String.valueOf((char)('A' + r)), SwingConstants.CENTER);
            lblRow.setFont(new Font("Arial", Font.BOLD, 14));
            pnlGheGrid.add(lblRow);
            
            // Thêm các nút ghế
            for (int c = 0; c < soCot; c++) {
                String tenGhe = String.valueOf((char)('A' + r)) + (c + 1);
                JButton btnGhe = new JButton(tenGhe);
                btnGhe.setPreferredSize(new Dimension(40, 40));
                btnGhe.setFont(new Font("Arial", Font.PLAIN, 12));
                
                // Tìm ghế trong danh sách
                Ghe ghe = timGheTheoTen(tenGhe);
                
                if (ghe != null) {
                    // Hiển thị màu sắc theo loại ghế và trạng thái
                    if (ghe.getTrangThai() == TrangThaiGhe.DA_DAT) {
                        btnGhe.setBackground(Color.GRAY);
                        btnGhe.setForeground(Color.WHITE);
                        btnGhe.setText("X");
                        btnGhe.setEnabled(false);
                    } else {
                        if (ghe.getLoaiGhe() == LoaiGhe.VIP) {
                            btnGhe.setBackground(new Color(255, 215, 0)); // Gold for VIP
                            btnGhe.setForeground(Color.BLACK);
                        } else {
                            btnGhe.setBackground(new Color(173, 216, 230)); // Light blue for normal
                            btnGhe.setForeground(Color.BLACK);
                        }
                        
                        // Thêm action listener cho nút ghế
                        btnGhe.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JButton btn = (JButton)e.getSource();
                                if (btn.getBackground().equals(new Color(50, 205, 50))) { // Đang chọn
                                    // Bỏ chọn ghế
                                    if (ghe.getLoaiGhe() == LoaiGhe.VIP) {
                                        btn.setBackground(new Color(255, 215, 0));
                                    } else {
                                        btn.setBackground(new Color(173, 216, 230));
                                    }
                                    gheDaChon.remove(ghe);
                                } else {
                                    // Chọn ghế
                                    btn.setBackground(new Color(50, 205, 50)); // Lime Green
                                    gheDaChon.add(ghe);
                                }
                            }
                        });
                    }
                } else {
                    // Nếu ghế chưa có trong DB, hiển thị màu mặc định
                    btnGhe.setBackground(new Color(173, 216, 230)); // Light blue for normal
                    
                    // Thêm action listener cho nút ghế mới
                    btnGhe.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton btn = (JButton)e.getSource();
                            showGheOptionDialog(btn, tenGhe);
                        }
                    });
                }
                
                gheButtons[r][c] = btnGhe;
                pnlGheGrid.add(btnGhe);
            }
        }
    }
    
    private Ghe timGheTheoTen(String tenGhe) {
        for (Ghe ghe : danhSachGhe) {
            if (ghe.getTenGhe() != null && ghe.getTenGhe().equals(tenGhe)) {
                return ghe;
            }
        }
        return null;
    }
    
    private void showGheOptionDialog(JButton btnGhe, String tenGhe) {
        JDialog dialog = new JDialog(this, "Thêm ghế mới", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel pnlContent = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTenGhe = new JLabel("Tên ghế:");
        JTextField txtTenGhe = new JTextField(tenGhe);
        txtTenGhe.setEditable(false);
        
        JLabel lblLoaiGhe = new JLabel("Loại ghế:");
        JComboBox<String> cboLoaiGhe = new JComboBox<>(new String[]{"THUONG", "VIP"});
        
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        JComboBox<String> cboTrangThai = new JComboBox<>(new String[]{"CON_TRONG", "DA_DAT"});
        
        pnlContent.add(lblTenGhe);
        pnlContent.add(txtTenGhe);
        pnlContent.add(lblLoaiGhe);
        pnlContent.add(cboLoaiGhe);
        pnlContent.add(lblTrangThai);
        pnlContent.add(cboTrangThai);
        
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnLuuGhe = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");
        
        btnLuuGhe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ghe gheNew = new Ghe();
                gheNew.setTenGhe(tenGhe);
                gheNew.setPhongChieu(phongChieu);
                gheNew.setLoaiGhe(cboLoaiGhe.getSelectedItem().toString().equals("VIP") ? LoaiGhe.VIP : LoaiGhe.THUONG);
                gheNew.setTrangThai(cboTrangThai.getSelectedItem().toString().equals("DA_DAT") ? TrangThaiGhe.DA_DAT : TrangThaiGhe.CON_TRONG);
                
                if (ghe_dao.themGhe(gheNew)) {
                    JOptionPane.showMessageDialog(dialog, "Thêm ghế thành công!");
                    
                    if (gheNew.getTrangThai() == TrangThaiGhe.DA_DAT) {
                        btnGhe.setBackground(Color.GRAY);
                        btnGhe.setForeground(Color.WHITE);
                        btnGhe.setText("X");
                        btnGhe.setEnabled(false);
                    } else if (gheNew.getLoaiGhe() == LoaiGhe.VIP) {
                        btnGhe.setBackground(new Color(255, 215, 0));
                    } else {
                        btnGhe.setBackground(new Color(173, 216, 230));
                    }

                    danhSachGhe.add(gheNew);
                    
                    ActionListener[] listeners = btnGhe.getActionListeners();
                    for (ActionListener listener : listeners) {
                        btnGhe.removeActionListener(listener);
                    }
                    
                    if (gheNew.getTrangThai() != TrangThaiGhe.DA_DAT) {
                        btnGhe.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JButton btn = (JButton)e.getSource();
                                if (btn.getBackground().equals(new Color(50, 205, 50))) { // Đang chọn
                                    // Bỏ chọn ghế
                                    if (gheNew.getLoaiGhe() == LoaiGhe.VIP) {
                                        btn.setBackground(new Color(255, 215, 0));
                                    } else {
                                        btn.setBackground(new Color(173, 216, 230));
                                    }
                                    gheDaChon.remove(gheNew);
                                } else {
                                    btn.setBackground(new Color(50, 205, 50));
                                    gheDaChon.add(gheNew);
                                }
                            }
                        });
                    }
                    
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm ghế thất bại!");
                }
            }
        });
        
        btnHuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        pnlButtons.add(btnLuuGhe);
        pnlButtons.add(btnHuy);
        
        dialog.add(pnlContent, BorderLayout.CENTER);
        dialog.add(pnlButtons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == miDangXuat) {
            dangXuat();
        } else if (o == miThoat) {
            thoat();
        } else if (o == miTrangChu) {
            dispose();
            new frmTrangChu();
        } else if (o == miKhuyenMai) {
            dispose();
            new frmKhuyenMai();
        } else if (o == btnLuu) {
            luuThayDoi();
        } else if (o == btnTroVe) {
            dispose();
            new frmPhongChieu();
        } else if (o instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) o;
            String text = menuItem.getText();
            switch (text) {
                case "Phòng chiếu":
                    dispose();
                    new frmPhongChieu();
                    break;
                case "Vé":
                    break;
                case "Đồ ăn":
                    break;
                case "Khách hàng":
                    break;
                case "Nhân viên":
                    break;
                case "Phim":
                    break;
                case "Doanh thu":
                    break;
            }
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
            try {
                new frmLogin();
                JOptionPane.showMessageDialog(null, "Đăng xuất thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: Không thể mở frmLogin!");
            }
        }
    }
    
    private void thoat() {
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn thoát?",
                "Xác nhận thoát",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
    
    private void luuThayDoi() {
        boolean success = true;
        
        for (Ghe ghe : gheDaChon) {
            if (ghe.getTrangThai() == TrangThaiGhe.CON_TRONG) {
                ghe.setTrangThai(TrangThaiGhe.DA_DAT);
            } else {
                ghe.setTrangThai(TrangThaiGhe.CON_TRONG);
            }
            
            if (!ghe_dao.capNhatGhe(ghe)) {
                success = false;
            }
        }
        
        if (success && !gheDaChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lưu thay đổi thành công!");
            gheDaChon.clear();
            pnlGheGrid.removeAll();
            taoLuoiGhe();
            pnlGheGrid.revalidate();
            pnlGheGrid.repaint();
        } else if (gheDaChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có thay đổi nào để lưu!");
        } else {
            JOptionPane.showMessageDialog(this, "Lưu thay đổi thất bại!");
        }
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

}
