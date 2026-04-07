package SS13.BT4;

public class DichVu {
    private int    maDichVu;
    private String tenDichVu;
    private String loai;
    private String gioSuDung;

    public DichVu(int maDichVu, String tenDichVu, String loai, String gioSuDung) {
        this.maDichVu  = maDichVu;
        this.tenDichVu = tenDichVu;
        this.loai      = loai;
        this.gioSuDung = gioSuDung;
    }

    public int    getMaDichVu()  { return maDichVu; }
    public String getTenDichVu() { return tenDichVu; }
    public String getLoai()      { return loai; }
    public String getGioSuDung() { return gioSuDung; }

    @Override
    public String toString() {
        return tenDichVu + " (" + loai + " " + gioSuDung + ")";
    }
}

