
import java.io.Serializable;
import java.util.ArrayList;

public class DanhSachNhanVien implements Serializable {
    private ArrayList<NhanVien> list;

    public DanhSachNhanVien() {
        list = new ArrayList<NhanVien>();
    }

    public boolean themNhanVien(NhanVien nv) {
        for(NhanVien n : list) {
            if(n.getMaMV() == nv.getMaMV()) return false;
        }
        list.add(nv);
        return true;
    }

    public boolean xoaNhanVien(int maMV) {
        for (NhanVien nv : list) {
            if (nv.getMaMV() == maMV) {
                list.remove(nv);
                return true;
            }
        }
        return false;
    }

    public NhanVien timKiem(int maMV) {
        for (NhanVien nv : list) {
            if (nv.getMaMV() == maMV) {
                return nv;
            }
        }
        return null;
    }

    public ArrayList<NhanVien> getList() {
        return list;
    }

    public NhanVien getNhanVien(int i) {
        if(i>=0 && i<list.size()) return list.get(i);
        return null;
    }
    public int tong() {
        return list.size();
    }
}