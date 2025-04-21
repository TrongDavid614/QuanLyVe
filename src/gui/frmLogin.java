package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class frmLogin extends JFrame implements MouseListener{
    private final JLabel lblTitle;
    private final JLabel lblTaiKhoan;
    private final JLabel lblMatKhau;
    private final JTextField txtTaiKhoan;
    private final JPasswordField txtPass;
    private final JButton btnDangNhap;
    private final JButton btnThoat;
    private final JLabel iconEye;

    private boolean isPasswordVisible = false;

    public frmLogin() {
        setTitle("Đăng nhập");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JSplitPane jp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        jp.setEnabled(false);
        add(jp, BorderLayout.CENTER);

        ImageIcon img = new ImageIcon("src/img/dangNhap.jpg");
        JPanel pLeft = new ImagePanel(img);
        pLeft.setPreferredSize(new Dimension(400, 600));

        JPanel pRight = new JPanel(null);
        pRight.setBackground(Color.CYAN);

        jp.setLeftComponent(pLeft);
        jp.setRightComponent(pRight);
        jp.setDividerLocation(400);

        lblTitle = new JLabel("THÔNG TIN ĐĂNG NHẬP");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 30));
        lblTitle.setBounds(60, 100, 400, 40);
        pRight.add(lblTitle);

        lblTaiKhoan = new JLabel("Tài khoản:");
        lblTaiKhoan.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblTaiKhoan.setBounds(80, 170, 150, 25);
        pRight.add(lblTaiKhoan);

        ImageIcon imgTaiKhoan = new ImageIcon("src/img/taiKhoan.png");
        Image scaledTaiKhoan = imgTaiKhoan.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel iconTaiKhoan = new JLabel(new ImageIcon(scaledTaiKhoan));
        iconTaiKhoan.setBounds(40, 200, 30, 30);
        pRight.add(iconTaiKhoan);

        txtTaiKhoan = new JTextField();
        txtTaiKhoan.setBounds(80, 200, 300, 30);
        pRight.add(txtTaiKhoan);

        lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblMatKhau.setBounds(80, 240, 150, 25);
        pRight.add(lblMatKhau);

        ImageIcon imgPass = new ImageIcon("src/img/password.png");
        Image scaledPass = imgPass.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel iconPass = new JLabel(new ImageIcon(scaledPass));
        iconPass.setBounds(40, 270, 30, 30);
        pRight.add(iconPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(80, 270, 300, 30);
        pRight.add(txtPass);

        ImageIcon eyeClosedRaw = new ImageIcon("src/img/anMatKhau.png");
        ImageIcon eyeOpenRaw = new ImageIcon("src/img/nhinMatKhau.png");

        Image eyeClosedScaled = eyeClosedRaw.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        Image eyeOpenScaled = eyeOpenRaw.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);

        ImageIcon eyeClosedIcon = new ImageIcon(eyeClosedScaled);
        ImageIcon eyeOpenIcon = new ImageIcon(eyeOpenScaled);

        iconEye = new JLabel(eyeClosedIcon);
        iconEye.setBounds(380, 270, 33, 33);
        iconEye.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iconEye.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iconEye.setBounds(380, 270, 33, 33);
        pRight.add(iconEye);

        iconEye.addMouseListener(this);

        JPanel tacVu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setBackground(new Color(46, 204, 113));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);

        btnThoat = new JButton("Thoát");
        btnThoat.setBackground(new Color(231, 76, 60));
        btnThoat.setForeground(Color.WHITE);
        btnThoat.setFocusPainted(false);

        tacVu.add(btnDangNhap);
        tacVu.add(btnThoat);
        tacVu.setBorder(BorderFactory.createTitledBorder("Chọn tác vụ"));
        tacVu.setBounds(30, 450, 400, 80);
        pRight.add(tacVu);

        setVisible(true);
    }

    class ImagePanel extends JPanel {
        private final Image img;

        public ImagePanel(ImageIcon icon) {
            this.img = icon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == iconEye) {
            isPasswordVisible = !isPasswordVisible;
            txtPass.setEchoChar(isPasswordVisible ? (char) 0 : '●');
            ImageIcon eyeIcon = isPasswordVisible
                    ? new ImageIcon(new ImageIcon("src/img/nhinMatKhau.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH))
                    : new ImageIcon(new ImageIcon("src/img/anMatKhau.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
            iconEye.setIcon(eyeIcon);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
