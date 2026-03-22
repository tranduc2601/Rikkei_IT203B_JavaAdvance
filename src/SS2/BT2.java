package SS2;
@FunctionalInterface
interface PasswordValidator {
    boolean isValid(String password);
}

public class BT2 {
    public static void main(String[] args) {

        // Cấp 1: Lambda cơ bản (vẫn còn rườm rà)
        // PasswordValidator validator = (String password) -> { return password.length() >= 8; };

        // Cấp 2: Bỏ kiểu dữ liệu String (Type Inference)

        // Cấp 3: Bỏ ngoặc tròn vì chỉ có 1 tham số

        // Cấp 4 (ĐÁP ÁN CUỐI CÙNG): Bỏ ngoặc nhọn và chữ 'return' vì chỉ có 1 dòng lệnh.
        PasswordValidator validator = p -> p.length() >= 8;

        System.out.println("Mật khẩu '12345678' hợp lệ? " + validator.isValid("12345678")); // Kết quả: true
        System.out.println("Mật khẩu '1234' hợp lệ? " + validator.isValid("1234"));         // Kết quả: false
    }
}