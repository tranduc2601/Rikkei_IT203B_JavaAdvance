package SS2;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// 1. Tạo class User cơ bản làm dữ liệu mẫu
class User {
    String username;
    String role;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }
}

public class BT1 {
    public static void main(String[] args) {

        // ==========================================
        // Yêu cầu 4: Supplier - Khởi tạo User mặc định
        // ==========================================
        // Logic: Không cần tham số (), trả về (->) một User mới.
        Supplier<User> userFactory = () -> new User("guest_user", "USER");

        // Gọi hàm get() để thực sự lấy ra User
        User newUser = userFactory.get();

        // ==========================================
        // Yêu cầu 1: Predicate - Kiểm tra Admin
        // ==========================================
        // Logic: Nhận vào 'u', kiểm tra role có phải ADMIN không, trả về true/false
        Predicate<User> isAdmin = u -> u.role.equals("ADMIN");

        // Gọi hàm test() để kiểm tra
        System.out.println("User này có phải Admin không? " + isAdmin.test(newUser)); // Kết quả: false

        // ==========================================
        // Yêu cầu 2: Function - Chuyển User thành String username
        // ==========================================
        // Logic: Nhận vào 'u', trích xuất và trả về mỗi username
        Function<User, String> extractUsername = u -> u.username;

        // Gọi hàm apply() để thực hiện chuyển đổi
        String nameOnly = extractUsername.apply(newUser);
        System.out.println("Username trích xuất được: " + nameOnly); // Kết quả: guest_user

        // ==========================================
        // Yêu cầu 3: Consumer - In thông tin User
        // ==========================================
        // Logic: Nhận vào 'u', thực hiện lệnh in, không cần return
        Consumer<User> printUserInfo = u -> System.out.println("Thông tin: " + u.username + " - Vai trò: " + u.role);

        // Gọi hàm accept() để thực thi hành động in
        printUserInfo.accept(newUser);
    }
}