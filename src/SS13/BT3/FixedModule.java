package SS13.BT3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FixedModule {

    private static final String URL  = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "tmd2601.";

    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection        conn    = null;
        PreparedStatement psCheck = null;
        PreparedStatement ps1     = null;
        PreparedStatement ps2     = null;
        PreparedStatement ps3     = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            conn.setAutoCommit(false);

            // --- BAY 1: Kiem tra so du tam ung truoc khi tru tien ---
            String sqlCheck = "SELECT deposit FROM Patients WHERE patient_id = ?";
            psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, maBenhNhan);
            ResultSet rs = psCheck.executeQuery();

            // --- BAY 2: Neu khong co hang nao tra ve -> benh nhan khong ton tai ---
            if (!rs.next()) {
                throw new Exception("Benh nhan ID=" + maBenhNhan + " khong ton tai.");
            }
            double soDu = rs.getDouble("deposit");
            rs.close();

            // --- BAY 1: So du khong du -> chan lai, nem ngoai le, rollback ---
            if (soDu < tienVienPhi) {
                throw new Exception("So du khong du. So du: " + soDu + " | Vien phi: " + tienVienPhi);
            }

            // Buoc 1: Tru tien vien phi vao so du tam ung
            String sqlTruTien = "UPDATE Patients SET deposit = deposit - ? WHERE patient_id = ?";
            ps1 = conn.prepareStatement(sqlTruTien);
            ps1.setDouble(1, tienVienPhi);
            ps1.setInt(2, maBenhNhan);
            int r1 = ps1.executeUpdate();
            // --- BAY 2: Rows affected = 0 -> khong co benh nhan tuong ung ---
            if (r1 == 0) throw new Exception("Buoc 1 that bai: khong tim thay benh nhan ID=" + maBenhNhan);

            // Buoc 2: Giai phong giuong benh -> chuyen trang thai sang 'Trong'
            String sqlGiuong = "UPDATE Beds SET status = 'Trong', patient_id = NULL WHERE patient_id = ?";
            ps2 = conn.prepareStatement(sqlGiuong);
            ps2.setInt(1, maBenhNhan);
            int r2 = ps2.executeUpdate();
            // --- BAY 2: Rows affected = 0 -> khong co giuong lien ket voi benh nhan nay ---
            if (r2 == 0) throw new Exception("Buoc 2 that bai: khong tim thay giuong lien ket voi benh nhan ID=" + maBenhNhan);

            // Buoc 3: Cap nhat trang thai benh nhan thanh 'Da xuat vien'
            String sqlTrangThai = "UPDATE Patients SET status = 'Da xuat vien' WHERE patient_id = ?";
            ps3 = conn.prepareStatement(sqlTrangThai);
            ps3.setInt(1, maBenhNhan);
            int r3 = ps3.executeUpdate();
            // --- BAY 2: Rows affected = 0 -> khong cap nhat duoc trang thai ---
            if (r3 == 0) throw new Exception("Buoc 3 that bai: khong cap nhat duoc trang thai benh nhan ID=" + maBenhNhan);

            conn.commit();
            System.out.println("Xuat vien thanh cong! BenhNhanID=" + maBenhNhan + " | Vien phi=" + tienVienPhi + " | Con lai=" + (soDu - tienVienPhi));

        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("ROLLBACK - Du lieu khong thay doi.");
                } catch (SQLException ex) {
                    System.out.println("Loi rollback: " + ex.getMessage());
                }
            }
        } finally {
            try {
                if (psCheck != null) psCheck.close();
                if (ps1     != null) ps1.close();
                if (ps2     != null) ps2.close();
                if (ps3     != null) ps3.close();
                if (conn    != null) { conn.setAutoCommit(true); conn.close(); }
            } catch (SQLException ex) {
                System.out.println("Loi dong ket noi: " + ex.getMessage());
            }
        }
    }

    public void printPatients() {
        String sql = "SELECT patient_id, patient_name, deposit, status FROM Patients ORDER BY patient_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("BENH NHAN:");
            while (rs.next()) {
                System.out.printf("  ID=%-3d | %-15s | Deposit=%-10.0f | %s%n",
                    rs.getInt("patient_id"),
                    rs.getString("patient_name"),
                    rs.getDouble("deposit"),
                    rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Loi doc benh nhan: " + e.getMessage());
        }
    }

    public void printBeds() {
        String sql = "SELECT bed_id, patient_id, status FROM Beds ORDER BY bed_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("GIUONG BENH:");
            while (rs.next()) {
                System.out.printf("  BedID=%-3d | PatientID=%-6s | %s%n",
                    rs.getInt("bed_id"),
                    rs.getObject("patient_id") != null ? rs.getObject("patient_id").toString() : "NULL",
                    rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Loi doc giuong: " + e.getMessage());
        }
    }
}

