package SS13.BT1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FixedModule {
    private static final String URL  = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "tmd2601.";

    public void capPhatThuoc(int medicineId, int patientId) {
        Connection        conn = null;
        PreparedStatement ps1  = null;
        PreparedStatement ps2  = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            conn.setAutoCommit(false);
            String sqlUpdateInventory =
                "UPDATE Medicine_Inventory SET quantity = quantity - 1 " +
                "WHERE medicine_id = ? AND quantity > 0";
            ps1 = conn.prepareStatement(sqlUpdateInventory);
            ps1.setInt(1, medicineId);
            int rowsUpdated = ps1.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException(
                    "Khong the cap phat: Thuoc ID=" + medicineId +
                    " khong ton tai hoac da het trong kho!");
            }
            String sqlInsertHistory =
                "INSERT INTO Prescription_History (patient_id, medicine_id, dispensed_date) " +
                "VALUES (?, ?, NOW())";
            ps2 = conn.prepareStatement(sqlInsertHistory);
            ps2.setInt(1, patientId);
            ps2.setInt(2, medicineId);
            ps2.executeUpdate();
            conn.commit();
            System.out.println("Cap phat thuoc thanh cong! (PatientID=" + patientId + ", MedicineID=" + medicineId + ")");
        } catch (Exception e) {
            System.out.println("Co loi xay ra: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Da hoan tac (ROLLBACK) - kho thuoc khong bi thay doi.");
                } catch (SQLException ex) {
                    System.out.println("Loi khi rollback: " + ex.getMessage());
                }
            }
        } finally {
            try {
                if (ps1  != null) ps1.close();
                if (ps2  != null) ps2.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void printInventory() {
        String sql = "SELECT medicine_id, medicine_name, quantity, unit " +
                     "FROM Medicine_Inventory ORDER BY medicine_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\nTRANG THAI KHO THUOC:");
            System.out.printf("%-5s %-25s %8s  %-6s%n",
                "ID", "Ten thuoc", "So luong", "Don vi");
            while (rs.next()) {
                System.out.printf("%-5d %-25s %8d  %-6s%n",
                    rs.getInt("medicine_id"),
                    rs.getString("medicine_name"),
                    rs.getInt("quantity"),
                    rs.getString("unit"));
            }
        } catch (SQLException e) {
            System.out.println("Loi doc kho: " + e.getMessage());
        }
    }

    public void printPrescriptionHistory() {
        String sql = "SELECT ph.history_id, ph.patient_id, " +
                     "mi.medicine_name, ph.dispensed_date " +
                     "FROM Prescription_History ph " +
                     "JOIN Medicine_Inventory mi ON ph.medicine_id = mi.medicine_id " +
                     "ORDER BY ph.history_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\nLICH SU CAP PHAT THUOC:");
            System.out.printf("%-5s %-12s %-25s %-20s%n",
                "ID", "BenhNhanID", "Ten thuoc", "Ngay cap");
            boolean hasRow = false;
            while (rs.next()) {
                hasRow = true;
                System.out.printf("%-5d %-12d %-25s %-20s%n",
                    rs.getInt("history_id"),
                    rs.getInt("patient_id"),
                    rs.getString("medicine_name"),
                    rs.getString("dispensed_date"));
            }
            if (!hasRow) System.out.println("  (Chua co ban ghi nao)");
        } catch (SQLException e) {
            System.out.println("Loi doc lich su: " + e.getMessage());
        }
    }
}
