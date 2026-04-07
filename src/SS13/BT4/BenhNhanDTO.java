package SS13.BT4;

import java.util.ArrayList;
import java.util.List;

public class BenhNhanDTO {
    private int          maBenhNhan;
    private String       hoTen;
    private int          tuoi;
    private String       gioiTinh;
    private String       phong;
    private String       ngayNhapVien;
    private List<DichVu> dsDichVu;

    public BenhNhanDTO(int maBenhNhan, String hoTen, int tuoi,
                       String gioiTinh, String phong, String ngayNhapVien) {
        this.maBenhNhan   = maBenhNhan;
        this.hoTen        = hoTen;
        this.tuoi         = tuoi;
        this.gioiTinh     = gioiTinh;
        this.phong        = phong;
        this.ngayNhapVien = ngayNhapVien;
        this.dsDichVu     = new ArrayList<>();
    }

    public void addDichVu(DichVu dv) { dsDichVu.add(dv); }

    public int          getMaBenhNhan()   { return maBenhNhan; }
    public String       getHoTen()        { return hoTen; }
    public int          getTuoi()         { return tuoi; }
    public String       getGioiTinh()     { return gioiTinh; }
    public String       getPhong()        { return phong; }
    public String       getNgayNhapVien() { return ngayNhapVien; }
    public List<DichVu> getDsDichVu()     { return dsDichVu; }
}

