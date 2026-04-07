package SS13.BT2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BuggyModule {

    private static final String URL  = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
        Connection        conn = null;
        PreparedStatement ps1  = null;
        PreparedStatement ps2  = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            conn.setAutoCommit(false);

            String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            ps1 = conn.prepareStatement(sqlDeductWallet);
            ps1.setDouble(1, amount);
            ps1.setInt(2, patientId);
            ps1.executeUpdate();

            String sqlUpdateInvoice = "UPDATE Invoicess SET status = 'PAID' WHERE invoice_id = ?";
            ps2 = conn.prepareStatement(sqlUpdateInvoice);
            ps2.setInt(1, invoiceId);
            ps2.executeUpdate();

            conn.commit();
            System.out.println("Thanh toan hoan tat!");

        } catch (SQLException e) {
            System.out.println("Loi he thong: Khong the hoan tat thanh toan. Chi tiet: " + e.getMessage());
        } finally {
            try {
                if (ps1  != null) ps1.close();
                if (ps2  != null) ps2.close();
                if (conn != null) conn.close();
            } catch (SQLException ignored) {}
        }
    }
}

