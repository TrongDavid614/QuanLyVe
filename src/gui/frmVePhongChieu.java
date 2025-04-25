package gui;

import dao.PhongChieu_Dao;
import entity.PhongChieu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class frmVePhongChieu extends JDialog {
    private PhongChieu_Dao phongChieuDAO;
    private String maPhim;
    private String maKhachHang;
    private String selectedMaPhong; // Lưu mã phòng được chọn

    // Constructor nhận vào frame cha, mã phim và mã khách hàng
    public frmVePhongChieu(JFrame parent, String maPhim, String maKhachHang) {
        super(parent, "Chọn Phòng Chiếu", true);
        this.phongChieuDAO = new PhongChieu_Dao();
        this.maPhim = maPhim;
        this.maKhachHang = maKhachHang;
        this.selectedMaPhong = null; // Khởi tạo mã phòng được chọn
        initUI();
    }

    // Phương thức khởi tạo giao diện
    private void initUI() {
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Phần trên: Tiêu đề
        JLabel lblTitle = new JLabel("Phòng Chiếu", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Phần giữa: Danh sách phòng chiếu
        JPanel panelPhongChieu = new JPanel();
        List<PhongChieu> danhSachPhongChieu = phongChieuDAO.getAllPhongChieu();
        int numRooms = danhSachPhongChieu != null ? danhSachPhongChieu.size() : 0;
        int rows = (int) Math.ceil(numRooms / 3.0);
        panelPhongChieu.setLayout(new GridLayout(rows, 3, 10, 10));
        panelPhongChieu.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelPhongChieu);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        if (danhSachPhongChieu == null || danhSachPhongChieu.isEmpty()) {
            JLabel lblEmpty = new JLabel("Không có phòng chiếu nào.", SwingConstants.CENTER);
            lblEmpty.setFont(new Font("Arial", Font.PLAIN, 16));
            panelPhongChieu.setLayout(new FlowLayout(FlowLayout.CENTER));
            panelPhongChieu.add(lblEmpty);
        } else {
            for (PhongChieu pc : danhSachPhongChieu) {
                panelPhongChieu.add(createPhongChieuPanel(pc));
            }
            while (panelPhongChieu.getComponentCount() < rows * 3) {
                panelPhongChieu.add(new JPanel());
            }
        }

        // Phần dưới: Nút Chọn ghế và Hủy
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnChonGhe = new JButton("Chọn ghế");
        JButton btnHuy = new JButton("Hủy");

        btnChonGhe.setFont(new Font("Arial", Font.PLAIN, 16));
        btnHuy.setFont(new Font("Arial", Font.PLAIN, 16));
        btnChonGhe.setBackground(new Color(34, 139, 34));
        btnChonGhe.setForeground(Color.WHITE);
        btnHuy.setBackground(new Color(220, 20, 60));
        btnHuy.setForeground(Color.WHITE);

        // Sự kiện nút Chọn ghế
        btnChonGhe.addActionListener(e -> {
            if (selectedMaPhong == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phòng chiếu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mở frmGhe để chọn ghế
            frmGhe frmGhe = new frmGhe((JFrame) SwingUtilities.getWindowAncestor(this), selectedMaPhong);
            frmGhe.setVisible(true);

            // Lấy danh sách ghế đã chọn sau khi frmGhe đóng
            List<String> gheDaChon = frmGhe.getGheDaChon();
            if (!gheDaChon.isEmpty()) {
                System.out.println("Ghế đã chọn: " + gheDaChon);
                JOptionPane.showMessageDialog(this, "Đã chọn ghế: " + gheDaChon,
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Đóng form sau khi chọn ghế thành công
            }
        });

        // Sự kiện nút Hủy
        btnHuy.addActionListener(e -> dispose());

        panelButtons.add(btnChonGhe);
        panelButtons.add(btnHuy);
        add(panelButtons, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    // Phương thức tạo panel cho mỗi phòng chiếu
    private JPanel createPhongChieuPanel(PhongChieu pc) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(250, 250));
        panel.setMaximumSize(new Dimension(250, 250));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ảnh phòng chiếu
        ImageIcon icon = new ImageIcon("src/img/phongchieu.jpg");
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel lblHinhAnh = new JLabel(new ImageIcon(scaledImage));
        lblHinhAnh.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHinhAnh.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Thông tin phòng chiếu
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMaPhong = new JLabel("Mã phòng: " + pc.getMaPhong());
        JLabel lblTenPhong = new JLabel("Tên phòng: " + pc.getTenPhong());
        JLabel lblSoGhe = new JLabel("Số ghế: " + pc.getSoGhe());

        lblMaPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTenPhong.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSoGhe.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMaPhong.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTenPhong.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSoGhe.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(lblMaPhong);
        infoPanel.add(lblTenPhong);
        infoPanel.add(lblSoGhe);

        panel.add(lblHinhAnh);
        panel.add(infoPanel);

        // Sự kiện click để chọn phòng
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedMaPhong = pc.getMaPhong(); // Lưu mã phòng được chọn
                JOptionPane.showMessageDialog(frmVePhongChieu.this, "Đã chọn phòng: " + pc.getTenPhong(),
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frmVePhongChieu(null, "PH001", "KH001").setVisible(true));
    }
}