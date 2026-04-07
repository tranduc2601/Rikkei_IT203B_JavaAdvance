package SS14;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankTransfer {

    private static final String URL = "jdbc:mysql://localhost:3306/BankDB";
    private static final String USER = "root";
    private static final String PASS = "tmd2601.";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            return;
        }

        String senderId = "ACC01";
        String receiverId = "ACC02";
        double transferAmount = 1500.0;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            conn.setAutoCommit(false);

            try {
                String checkSql = "SELECT Balance FROM Accounts WHERE AccountId = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, senderId);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new Exception("Tài khoản người gửi không tồn tại.");
                        }
                        double currentBalance = rs.getDouble("Balance");
                        if (currentBalance < transferAmount) {
                            throw new Exception("Số dư không đủ để thực hiện giao dịch.");
                        }
                    }
                }

                String callSql = "{call sp_UpdateBalance(?, ?)}";
                try (CallableStatement cstmt = conn.prepareCall(callSql)) {
                    cstmt.setString(1, senderId);
                    cstmt.setDouble(2, -transferAmount);
                    cstmt.execute();

                    cstmt.setString(1, receiverId);
                    cstmt.setDouble(2, transferAmount);
                    cstmt.execute();
                }

                conn.commit();

                String displaySql = "SELECT AccountId, FullName, Balance FROM Accounts WHERE AccountId IN (?, ?)";
                try (PreparedStatement displayStmt = conn.prepareStatement(displaySql)) {
                    displayStmt.setString(1, senderId);
                    displayStmt.setString(2, receiverId);

                    try (ResultSet rs = displayStmt.executeQuery()) {
                        System.out.printf("%-15s | %-25s | %-15s\n", "AccountId", "FullName", "Balance");
                        System.out.println("---------------------------------------------------------------");
                        while (rs.next()) {
                            System.out.printf("%-15s | %-25s | %,.2f\n",
                                    rs.getString("AccountId"),
                                    rs.getString("FullName"),
                                    rs.getDouble("Balance"));
                        }
                    }
                }

            } catch (Exception e) {
                conn.rollback();
                System.err.println("Giao dịch thất bại. Đã hoàn tác (rollback) toàn bộ thay đổi.");
                System.err.println("Lý do: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}