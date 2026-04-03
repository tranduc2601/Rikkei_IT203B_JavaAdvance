package SS12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BT4 {

    static class TestResult {
        private String data;

        public TestResult(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }

    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static void insertTestResults(List<TestResult> list) {
        String sql = "INSERT INTO Results(data) VALUES(?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (TestResult tr : list) {
                pstmt.setString(1, tr.getData());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<TestResult> testResults = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            testResults.add(new TestResult("Blood_Sample_" + i));
        }

        long startTime = System.currentTimeMillis();

        insertTestResults(testResults);

        long endTime = System.currentTimeMillis();

        System.out.println("Tổng thời gian thực thi: " + (endTime - startTime) + " ms");
    }
}