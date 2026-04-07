package SS13.BT4;

import java.sql.*;
import java.util.*;

public class FixedModule {
    private static final String URL  = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "tmd2601.";

    public List<BenhNhanDTO> getDashboard() {
        Map<Integer, BenhNhanDTO> map = new LinkedHashMap<>();
        String sql =
            "SELECT bn.maBenhNhan, bn.hoTen, bn.tuoi, bn.gioiTinh, bn.phong, bn.ngayNhapVien, " +
            "       dv.maDichVu,  dv.tenDichVu, dv.loai, dv.gioSuDung " +
            "FROM   BenhNhan bn " +
            "LEFT JOIN DichVuSuDung dv ON bn.maBenhNhan = dv.maBenhNhan " +
            "ORDER BY bn.maBenhNhan, dv.maDichVu";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int mabn = rs.getInt("maBenhNhan");
                if (!map.containsKey(mabn)) {
                    map.put(mabn, new BenhNhanDTO(
                        mabn,
                        rs.getString("hoTen"),
                        rs.getInt("tuoi"),
                        rs.getString("gioiTinh"),
                        rs.getString("phong"),
                        rs.getString("ngayNhapVien")
                    ));
                }
                // BAY 2: LEFT JOIN tra maDichVu = NULL neu benh nhan chua co dich vu nao.
                // Dung getObject() de kiem tra NULL truoc khi cast, tranh NullPointerException.
                if (rs.getObject("maDichVu") != null) {
                    map.get(mabn).addDichVu(new DichVu(
                        rs.getInt("maDichVu"),
                        rs.getString("tenDichVu"),
                        rs.getString("loai"),
                        rs.getString("gioSuDung")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return new ArrayList<>(map.values());
    }
}

