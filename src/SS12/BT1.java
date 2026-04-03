package SS12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BT1 {

    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static boolean loginDoctor(String doctorCode, String password) {
        String sql = "SELECT * FROM Doctors WHERE code = ? AND pass = ?";

        System.out.println("Đang xử lý đăng nhập cho bác sĩ: " + doctorCode);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Biên dịch trước câu lệnh tại đây

            pstmt.setString(1, doctorCode);
            pstmt.setString(2, password); // Nếu pass là "' OR '1'='1", nó chỉ là 1 chuỗi ký tự bình thường

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("=> ĐĂNG NHẬP THÀNH CÔNG! Chào mừng Bác sĩ " + rs.getString("name"));
                    return true;
                } else {
                    System.out.println("=> ĐĂNG NHẬP THẤT BẠI: Sai mã bác sĩ hoặc mật khẩu!");
                    return false;
                }
            }

        } catch (SQLException e) {
            System.err.println("=> LỖI HỆ THỐNG: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- TEST LUỒNG HACKER TẤN CÔNG ---");
        String hackerCode = "DOC001";
        String hackerPass = "' OR '1'='1";

        loginDoctor(hackerCode, hackerPass);
    }
}