package SS13;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BT1 {

    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public void capPhatThuoc(int medicineId, int patientId) {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASS);

            conn.setAutoCommit(false);

            String sqlUpdateInventory = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
            ps1 = conn.prepareStatement(sqlUpdateInventory);
            ps1.setInt(1, medicineId);
            ps1.executeUpdate();

            String sqlInsertHistory = "INSERT INTO Prescription_History (patient_id, medicine_id, date) VALUES (?, ?, GETDATE())";
            ps2 = conn.prepareStatement(sqlInsertHistory);
            ps2.setInt(1, patientId);
            ps2.setInt(2, medicineId);
            ps2.executeUpdate();

            conn.commit();
            System.out.println("Cấp phát thuốc thành công!");

        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra: " + e.getMessage());

            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã hoàn tác (rollback) dữ liệu thành công.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (ps1 != null) ps1.close();
                if (ps2 != null) ps2.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}