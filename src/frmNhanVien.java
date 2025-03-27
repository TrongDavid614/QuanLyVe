
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class frmNhanVien extends JFrame implements ActionListener {
    private  DanhSachNhanVien dsnv ;
    private JLabel lblTitle;
    private JLabel lblMaNV, lblHo, lblTen, lblTuoi, lblPhai, lblTienLuong;
    private JTextField txtMaNV, txtHo, txtTen, txtTuoi, txtTienLuong;
    private JRadioButton radNam, radNu;
    private DefaultTableModel tableModal;
    private JTable table;
    private JLabel lblTim;
    private JTextField txtTim;
    private JButton btnTim;
    private JButton btnThem;
    private JButton btnXoaTrang;
    private JButton btnXoa;
    private JButton btnLuu;
    private static final String FILENAME = "data.txt";

    public frmNhanVien() {
        dsnv = new DanhSachNhanVien();
        setTitle("Quản lý nhân viên");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        BuildUI();
        LoadDatabase();
        setVisible(true);
    }

    public void BuildUI() {
        setLayout(new BorderLayout());
        JPanel pNorth = new JPanel();
        pNorth.add(lblTitle = new JLabel("THÔNG TIN NHÂN VIÊN"));
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 30));
        lblTitle.setForeground(Color.BLUE);
        add(pNorth, BorderLayout.NORTH);

        JPanel pCen = new JPanel();
        pCen.setLayout(null);
        add(pCen, BorderLayout.CENTER);

        pCen.add(lblMaNV = new JLabel("Mã nhân viên : "));
        pCen.add(txtMaNV = new JTextField(20));
        pCen.add(lblHo = new JLabel("Họ : "));
        pCen.add(txtHo = new JTextField(20));
        pCen.add(lblTen = new JLabel("Tên nhân viên : "));
        pCen.add(txtTen = new JTextField(20));
        pCen.add(lblTuoi = new JLabel("Tuổi : "));
        pCen.add(txtTuoi = new JTextField(20));
        pCen.add(lblPhai = new JLabel("Phái : "));

        ButtonGroup bg = new ButtonGroup();
        pCen.add(radNam = new JRadioButton("Nam"));
        pCen.add(radNu = new JRadioButton("Nữ"));
        bg.add(radNam);
        bg.add(radNu);

        pCen.add(lblTienLuong = new JLabel("Tiền lương"));
        pCen.add(txtTienLuong = new JTextField(20));

        int w1 = 0, w2 = 100, h1 = 20;
        lblMaNV.setBounds(w1, 20, w2, h1);
        txtMaNV.setBounds(100, 20, 800, h1);
        lblHo.setBounds(w1, 50, w2, h1);
        txtHo.setBounds(100, 50, 300, h1);
        lblTen.setBounds(400, 50, 300, h1);
        txtTen.setBounds(500, 50, 500, h1);
        lblTuoi.setBounds(w1, 80, w2, h1);
        txtTuoi.setBounds(100, 80, 600, h1);
        lblPhai.setBounds(700, 80, w2, h1);
        radNam.setBounds(750, 80, 50, h1);
        radNu.setBounds(840, 80, 50, h1);
        lblTienLuong.setBounds(w1, 110, w2, h1);
        txtTienLuong.setBounds(100, 110, 900, h1);


        String[] headers = "Mã NV;Họ;Tên;Phái;Tuổi;Tiền lương".split(";");
        tableModal = new DefaultTableModel(headers, 0);
        table = new JTable(tableModal);
        table.setRowHeight(20);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(0, 140, 900, 300);
        pCen.add(scroll);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setBorder(BorderFactory.createTitledBorder(""));
        add(split , BorderLayout.SOUTH);
        JPanel pLeft = new JPanel();
        JPanel pRight = new JPanel();
        split.add(pLeft);
        split.add(pRight);

        pLeft.add(lblTim = new JLabel("Nhập mã số cần tìm : "));
        pLeft.add(txtTim = new JTextField());
        txtTim.setPreferredSize(new Dimension(150 , 20));
        pLeft.add(btnTim = new JButton("Tìm"));

        pRight.add(btnThem = new JButton("Thêm"));
        pRight.add(btnXoaTrang = new JButton("Xóa Trắng"));
        pRight.add(btnXoa = new JButton("Xóa"));
        pRight.add(btnLuu = new JButton("Lưu"));

        btnThem.addActionListener(this);
        btnLuu.addActionListener(this);
        btnXoa.addActionListener(this);
        btnTim.addActionListener(this);
        btnXoaTrang.addActionListener(this);

    }

    public static void main(String[] args) {
        new frmNhanVien();
    }

    private void LoadDatabase(){
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            br.readLine();
            while (br.ready()){
                String line = br.readLine();
                if(line!= null || !line.trim().isEmpty()){
                    String[] a = line.split(";");
                    tableModal.addRow(a);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o.equals(btnThem)) them();
        else if(o.equals(btnXoa)) xoa();
        else if(o.equals(btnXoaTrang)) xoaRong();
        else if(o.equals(btnLuu)) luu();

    }

    public void them(){
        String maNVStr = txtMaNV.getText().trim();
        String ho = txtHo.getText().trim();
        String ten = txtTen.getText().trim();
        String tuoiStr = txtTuoi.getText().trim();
        String tienLuongStr = txtTienLuong.getText().trim();

        if (maNVStr.isEmpty() || ho.isEmpty() || ten.isEmpty() || tuoiStr.isEmpty() || tienLuongStr.isEmpty() ||(!radNam.isSelected() && !radNu.isSelected())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            int maNV = Integer.parseInt(maNVStr);
            int tuoi = Integer.parseInt(tuoiStr);
            boolean phai = radNam.isSelected();
            double tienLuong = Double.parseDouble(tienLuongStr);
            NhanVien nv = new NhanVien(maNV , ho , ten , phai , tuoi , tienLuong);
            if (dsnv.themNhanVien(nv)) {
                String[] rowData = {
                        String.valueOf(maNV), ho, ten, phai ? "Nam" : "Nữ", String.valueOf(tuoi), String.valueOf(tienLuong)
                };
                tableModal.addRow(rowData);
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                xoaRong();
            }else{
                JOptionPane.showMessageDialog(this , "Thêm nhân viên thất bại!");
            }


        }catch (Exception e){
            JOptionPane.showMessageDialog(this , "Thêm nhân viên không thành công!");
        }
    }
    public void xoa() {
        int row = table.getSelectedRow(); // Lấy dòng được chọn
        if (row != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?");
            if (confirm == JOptionPane.YES_OPTION) { // Nếu chọn "Có"
                tableModal.removeRow(row); // Xóa nhân viên khỏi bảng
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hãy chọn một dòng để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void xoaRong(){
        txtMaNV.setText("");
        txtTuoi.setText("");
        txtHo.setText("");
        txtTen.setText("");
        txtTienLuong.setText("");
        radNu.setSelected(false);
        radNam.setSelected(false);
    }
    public void luu(){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter (fw);
            bw.write("Mã NV;Họ;Tên;Phái;Tuổi;Tiền lương");
            bw.newLine();
            for (int i=0 ; i<tableModal.getRowCount() ; i++){
                StringBuffer sb = new StringBuffer();
                sb.append(tableModal.getValueAt(i , 0)).append(";")
                        .append(tableModal.getValueAt(i , 1)).append(";")
                        .append(tableModal.getValueAt(i , 2)).append(";")
                        .append(tableModal.getValueAt(i , 3)).append(";")
                        .append(tableModal.getValueAt(i , 4)).append(";")
                        .append(tableModal.getValueAt(i , 5));
                bw.write(sb.toString());
                bw.newLine();


            }
            bw.close();
            fw.close();
            JOptionPane.showMessageDialog(this , "Lưu nhân viên thành công");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this , "Lưu thất bại");
        }
    }

}
