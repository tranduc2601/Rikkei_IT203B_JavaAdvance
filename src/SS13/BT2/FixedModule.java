package SS13.BT2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FixedModule {

    private static final String URL  = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "tmd2601.";

    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
        Connection        conn    = null;
        PreparedStatement ps1     = null;
        PreparedStatement ps2     = null;
        PreparedStatement psCheck = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            conn.setAutoCommit(false);

            String sqlCheckBalance = "SELECT balance FROM Patient_Wallet WHERE patient_id = ?";
            psCheck = conn.prepareStatement(sqlCheckBalance);
            psCheck.setInt(1, patientId);
            ResultSet rs = psCheck.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Benh nhan ID=" + patientId + " khong ton tai.");
            }
            double balance = rs.getDouble("balance");
            if (balance < amount) {
                throw new SQLException("So du khong du. So du: " + balance + ", Can thanh toan: " + amount);
            }
            rs.close();

            String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            ps1 = conn.prepareStatement(sqlDeductWallet);
            ps1.setDouble(1, amount);
            ps1.setInt(2, patientId);
            ps1.executeUpdate();

            String sqlUpdateInvoice = "UPDATE Invoices SET status = 'PAID' WHERE invoice_id = ?";
            ps2 = conn.prepareStatement(sqlUpdateInvoice);
            ps2.setInt(1, invoiceId);
            ps2.executeUpdate();

            conn.commit();
            System.out.println("Thanh toan hoan tat! PatientID=" + patientId + " InvoiceID=" + invoiceId + " So tien=" + amount);

        } catch (SQLException e) {
            System.out.println("Loi he thong: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Da rollback - vi tien khong bi thay doi.");
                } catch (SQLException ex) {
                    System.out.println("Loi khi rollback: " + ex.getMessage());
                }
            }
        } finally {
            try {
                if (psCheck != null) psCheck.close();
                if (ps1     != null) ps1.close();
                if (ps2     != null) ps2.close();
                if (conn    != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Loi dong ket noi: " + ex.getMessage());
            }
        }
    }

    public void printWallet() {
        String sql = "SELECT patient_id, patient_name, balance FROM Patient_Wallet ORDER BY patient_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("VI TIEN BENH NHAN:");
            while (rs.next()) {
                System.out.println("  ID=" + rs.getInt("patient_id") + " | " + rs.getString("patient_name") + " | " + rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            System.out.println("Loi doc vi tien: " + e.getMessage());
        }
    }

    public void printInvoices() {
        String sql = "SELECT invoice_id, patient_id, amount, status FROM Invoices ORDER BY invoice_id";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("HOA DON VIEN PHI:");
            while (rs.next()) {
                System.out.println("  InvoiceID=" + rs.getInt("invoice_id") + " | PatientID=" + rs.getInt("patient_id") + " | Amount=" + rs.getDouble("amount") + " | Status=" + rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Loi doc hoa don: " + e.getMessage());
        }
    }
}

