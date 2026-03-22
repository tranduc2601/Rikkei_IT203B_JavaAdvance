package SS2;
// Đánh dấu đây là một Functional Interface hợp lệ (chỉ có 1 hàm abstract)
@FunctionalInterface
interface Authenticatable {

    // 1. Phương thức trừu tượng (Bắt buộc phải Override hoặc dùng Lambda)
    String getPassword();

    // 2. Phương thức mặc định (Logic có sẵn, đối tượng nào cũng xài được)
    default boolean isAuthenticated() {
        // Lấy password từ hàm abstract (lúc chạy mới biết password là gì)
        String pwd = getPassword();
        // Kiểm tra xem password có hợp lệ không (khác null và không bị rỗng)
        return pwd != null && !pwd.trim().isEmpty();
    }

    // 3. Phương thức tĩnh (Công cụ dùng chung, gọi qua tên Interface)
    static String encrypt(String rawPassword) {
        // Mô phỏng thuật toán mã hóa (VD: MD5, BCrypt)
        return "chuoi_da_ma_hoa_cua_" + rawPassword;
    }
}

public class BT3 {
    public static void main(String[] args) {
        // ==========================================
        // TEST 1: Thử tính năng Utility (Static Method)
        // ==========================================
        // Không cần khởi tạo đối tượng, gọi thẳng từ tên Interface Authenticatable
        String encrypted = Authenticatable.encrypt("admin123");
        System.out.println("Mật khẩu sau khi mã hóa: " + encrypted);

        // ==========================================
        // TEST 2: Vận dụng Functional Interface bằng Lambda
        // ==========================================
        // Vì Authenticatable là Functional Interface, ta dùng Lambda để định nghĩa NHANH hàm getPassword()
        // Ở đây, ta giả sử đối tượng này luôn trả về mật khẩu là rỗng ""
        Authenticatable guestUser = () -> "";

        // Giả sử một người dùng khác có mật khẩu đàng hoàng
        Authenticatable adminUser = () -> "super_secret_password";

        // ==========================================
        // TEST 3: Thử tính năng Kế thừa hành vi (Default Method)
        // ==========================================
        // Lambda chỉ định nghĩa getPassword(), nhưng đối tượng sinh ra lại có sẵn luôn hàm isAuthenticated()
        System.out.println("Guest có được xác thực không? " + guestUser.isAuthenticated()); // false
        System.out.println("Admin có được xác thực không? " + adminUser.isAuthenticated()); // true
    }
}