package SS11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BT3 {

    // Khai báo hằng số cấu hình Database
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static void inDanhMucThuoc() {
        // Câu lệnh truy vấn lấy tên thuốc và số lượng
        String sql = "SELECT medicine_name, stock_quantity FROM Medicines";

        System.out.println("=== DANH MỤC THUỐC TRONG KHO ===");

        // Sử dụng try-with-resources để TỰ ĐỘNG ĐÓNG kết nối (Connection, PreparedStatement, ResultSet)
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean hasData = false; // Biến cờ để kiểm tra xem kho có trống không

            // THAY ĐỔI CỐT LÕI: Dùng vòng lặp WHILE thay vì lệnh IF
            while (rs.next()) {
                hasData = true; // Đánh dấu là có ít nhất 1 loại thuốc

                // Lấy dữ liệu từng cột dựa theo tên cột trong CSDL
                String name = rs.getString("medicine_name");
                int quantity = rs.getInt("stock_quantity");

                System.out.printf("- Tên thuốc: %-20s | Số lượng tồn kho: %d\n", name, quantity);
            }

            // Xử lý trường hợp bảng Medicines không có dữ liệu nào
            if (!hasData) {
                System.out.println("Cảnh báo: Kho thuốc hiện đang trống, không có dữ liệu!");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy xuất danh mục thuốc: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Chạy thử chương trình
        inDanhMucThuoc();
    }
}