package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frmTrangChu extends JFrame implements ActionListener {
    private JMenuItem miKhachHang;
    private JLabel lblQuanLy;
    private JMenuItem miDangXuat;
    private JMenuItem miThoat;

    public frmTrangChu() {
        setTitle("Trang Chủ");
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

        menuHeThong.setBounds(100, 0, 200, 50);

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
            }
            menuHeThong.add(menuItem);
        }

        String[] danhMucItems = {"Khách hàng", "Nhân viên", "Khuyến mại", "Phim"};
        for (String item : danhMucItems) {
            JMenuItem menuItem = createMenuItem(item);
            if(item.equals("Khách hàng")){
                miKhachHang = menuItem;
                menuItem.addActionListener(this);
            }
            menuItem.setFont(new Font("Arial", Font.PLAIN, 18));
            menuItem.addActionListener(this);
            menuDanhMuc.add(menuItem);
        }

        String[] xuLyItems = {"Vé", "Phòng chiếu", "Đồ ăn", "Đặt vé"};
        for (String item : xuLyItems) {
            JMenuItem menuItem = createMenuItem(item);
            menuItem.setFont(new Font("Arial", Font.PLAIN, 18));
            menuItem.addActionListener(this);
            menuXuLy.add(menuItem);
        }

        JMenuItem doanThuItem = createMenuItem("Doanh thu");
        doanThuItem.setFont(new Font("Arial", Font.PLAIN, 18));
        doanThuItem.addActionListener(this);
        menuThongKe.add(doanThuItem);

        menuBar.add(menuHeThong);
        menuBar.add(menuDanhMuc);
        menuBar.add(menuXuLy);
        menuBar.add(menuThongKe);

        lblQuanLy = new JLabel("QUẢN LÝ: VĂN TRỌNG");
        lblQuanLy.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(lblQuanLy, BorderLayout.EAST);
        rightPanel.setOpaque(false);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(menuBar, BorderLayout.WEST);
        menuPanel.add(rightPanel, BorderLayout.EAST);

        setJMenuBar(null);
        getContentPane().add(menuPanel, BorderLayout.NORTH);

        JPanel pCen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("src/img/anhNen.png");
                g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        pCen.setLayout(new BorderLayout());

        add(pCen, BorderLayout.CENTER);

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
        } else if(o == miKhachHang){
            dispose();
            new frmKhachHang();
        }
    }

    public static void main(String[] args) {
        new frmTrangChu();
    }
}