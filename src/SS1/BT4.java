package SS1;
import java.io.IOException;

public class BT4 {

    // Trạm cuối: Hàm A (main) - Nơi bắt buộc phải xử lý lỗi
    public static void main(String[] args) {
        System.out.println("Bắt đầu chương trình tại main()...");

        try {
            // Hàm A gọi Hàm B
            processUserData();
        } catch (IOException e) {
            // Lưới an toàn cuối cùng tóm gọn lỗi từ Hàm C ném lên
            System.out.println("Lỗi đã bị bắt tại main()!");
            System.out.println("Chi tiết lỗi: " + e.getMessage());
        }

        System.out.println("Chương trình kết thúc an toàn, không bị crash.");
    }

    // Trạm giữa: Hàm B - Chuyển tiếp trách nhiệm
    public static void processUserData() throws IOException {
        System.out.println("Đang xử lý dữ liệu tại processUserData()...");
        // Hàm B gọi Hàm C. Vì C có throws, B cũng phải throws để đẩy tiếp.
        saveToFile();
    }

    // Trạm đáy: Hàm C - Nơi phát sinh lỗi thực tế
    public static void saveToFile() throws IOException {
        System.out.println("Đang cố gắng ghi file tại saveToFile()...");

        // Cố tình tạo ra một lỗi Checked Exception (phải có throws ở chữ ký hàm)
        throw new IOException("Không tìm thấy ổ đĩa hoặc file bị khóa!");
    }
}