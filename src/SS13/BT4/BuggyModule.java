package SS13.BT4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuggyModule {
    private static final String URL  = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "tmd2601.";

    private int lastQueryCount = 0;

    public List<BenhNhanDTO> getDashboard() {
        lastQueryCount = 0;
        List<BenhNhanDTO> result = new ArrayList<>();
        String sql1 = "SELECT maBenhNhan, hoTen, tuoi, gioiTinh, phong, ngayNhapVien " +
                      "FROM BenhNhan ORDER BY maBenhNhan";
        String sql2 = "SELECT maDichVu, tenDichVu, loai, gioSuDung " +
                      "FROM DichVuSuDung WHERE maBenhNhan = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps1 = conn.prepareStatement(sql1);
             ResultSet rs1 = ps1.executeQuery()) {
            lastQueryCount++;
            while (rs1.next()) {
                BenhNhanDTO dto = new BenhNhanDTO(
                    rs1.getInt("maBenhNhan"),
                    rs1.getString("hoTen"),
                    rs1.getInt("tuoi"),
                    rs1.getString("gioiTinh"),
                    rs1.getString("phong"),
                    rs1.getString("ngayNhapVien")
                );
                // N+1 BUG: ban 1 query rieng le vao DB cho moi benh nhan
                try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                    ps2.setInt(1, dto.getMaBenhNhan());
                    try (ResultSet rs2 = ps2.executeQuery()) {
                        lastQueryCount++;
                        while (rs2.next()) {
                            dto.addDichVu(new DichVu(
                                rs2.getInt("maDichVu"),
                                rs2.getString("tenDichVu"),
                                rs2.getString("loai"),
                                rs2.getString("gioSuDung")
                            ));
                        }
                    }
                }
                result.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return result;
    }

    public int getLastQueryCount() { return lastQueryCount; }
}

