package SS12;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class BT3 {

    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static void checkSurgeryFee(int surgeryId) {
        String sql = "{call GET_SURGERY_FEE(?, ?)}";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setInt(1, surgeryId);

            cstmt.registerOutParameter(2, Types.DECIMAL);

            cstmt.execute();

            double cost = cstmt.getDouble(2);

            System.out.printf("=> Tra cứu thành công! Chi phí cho ca phẫu thuật [%d] là: %,.2f VNĐ\n", surgeryId, cost);

        } catch (SQLException e) {
            System.err.println("=> LỖI: Không thể tra cứu chi phí - " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("--- HỆ THỐNG TRA CỨU CHI PHÍ PHẪU THUẬT ---");
        checkSurgeryFee(505);
    }
}