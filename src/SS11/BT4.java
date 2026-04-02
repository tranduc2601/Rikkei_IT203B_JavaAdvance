package SS11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BT4 {

    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "123456";

    // Hàm làm sạch dữ liệu đầu vào (Sanitize)
    public static String sanitizeInput(String input) {
        if (input == null) return "";

        // Loại bỏ dấu nháy đơn (phá vỡ cấu trúc chuỗi SQL)
        // Loại bỏ dấu chấm phẩy (kết thúc lệnh hiện tại để chạy lệnh độc hại tiếp theo)
        // Loại bỏ dấu gạch ngang (biến phần code phía sau thành comment)
        return input.replace("'", "")
                .replace(";", "")
                .replace("--", "");
    }

    public static void searchPatient(String patientName) {
        // 1. BẮT BUỘC làm sạch đầu vào trước khi sử dụng
        String safePatientName = sanitizeInput(patientName);

        // 2. Nối chuỗi an toàn (do các ký tự độc hại đã bị tước bỏ)
        String sql = "SELECT * FROM Patients WHERE name = '" + safePatientName + "'";
        System.out.println("Câu lệnh SQL thực thi: " + sql);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("=> Hồ sơ bệnh nhân: " + rs.getString("name"));
            }

            if (!found) {
                System.out.println("Hệ thống không tìm thấy bệnh nhân nào khớp với tên vừa nhập.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("--- TEST TRƯỜNG HỢP BỊ TẤN CÔNG ---");
        // Chuỗi tấn công từ hacker
        String hackerInput = "A' OR '1'='1";

        // Nhờ bộ lọc, chuỗi truy vấn thực sự sẽ mất đi các dấu nháy đơn.
        // Lúc này CSDL sẽ đi tìm một người có cái tên dài thòng là "A OR 1=1", dĩ nhiên là không có ai tên như vậy cả.
        searchPatient(hackerInput);
    }
}