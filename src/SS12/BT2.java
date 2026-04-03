package SS12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BT2 {

    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static void updatePatientVitals(String patientId, double temperature, int heartRate) {
        String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, temperature);
            pstmt.setInt(2, heartRate);
            pstmt.setString(3, patientId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cập nhật chỉ số sinh tồn thành công cho bệnh nhân: " + patientId);
            } else {
                System.err.println("Cập nhật thất bại: Không tìm thấy mã bệnh nhân " + patientId);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi hệ thống khi cập nhật: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        updatePatientVitals("BN_002", 37.5, 85);
    }
}