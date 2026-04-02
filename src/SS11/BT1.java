package SS11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BT1 {

    // Lớp DBContext quản lý kết nối cơ sở dữ liệu
    public static class DBContext {
        // 1. Dùng HẰNG SỐ (static final) để quản lý cấu hình tập trung
        private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
        private static final String USER = "root";
        private static final String PASS = "123456";

        // Phương thức mở kết nối
        public Connection getConnection() {
            try {
                return DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException e) {
                System.err.println("Lỗi không thể kết nối đến DB Bệnh viện: " + e.getMessage());
                return null;
            }
        }

        // 2. Ví dụ một phương thức truy vấn mẫu ĐẢM BẢO đóng kết nối bằng finally
        public void getPatientRecord(String patientId) {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = getConnection(); // Mở kết nối
                if (conn == null) return;

                String sql = "SELECT * FROM Patients WHERE id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, patientId);

                rs = stmt.executeQuery();

                // Xử lý dữ liệu...
                while (rs.next()) {
                    System.out.println("Hồ sơ bệnh nhân: " + rs.getString("name"));
                }

            } catch (SQLException e) {
                System.err.println("Lỗi trong quá trình truy vấn: " + e.getMessage());
            } finally {
                // 3. Khối FINALLY bắt buộc chạy dù có lỗi hay không
                // Đóng các đối tượng theo thứ tự ngược lại lúc tạo (ResultSet -> Statement -> Connection)
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close(); // QUAN TRỌNG NHẤT: Trả lại kết nối cho DB
                } catch (SQLException ex) {
                    System.err.println("Lỗi khi cố gắng giải phóng tài nguyên: " + ex.getMessage());
                }
            }
        }
    }

    // Hàm Main để chạy thử nghiệm
    public static void main(String[] args) {
        DBContext hospitalDB = new DBContext();
        // Gọi thử truy vấn, sau khi truy vấn xong kết nối sẽ được tự động dọn dẹp nhờ khối finally
        hospitalDB.getPatientRecord("BN_001");
    }
}