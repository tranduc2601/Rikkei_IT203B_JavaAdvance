package SS2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

// Đổi tên thành UserBT4 để tránh trùng lặp với SS2_BT1
class UserBT4 {
    private String username;

    // Constructor không tham số
    public UserBT4() {
        this.username = "Default_User";
    }

    // Constructor có tham số
    public UserBT4(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

public class BT4 {
    public static void main(String[] args) {

        // ==========================================
        // 1. Tham chiếu Constructor: () -> new UserBT4()
        // ==========================================
        Supplier<UserBT4> userFactory = UserBT4::new;

        List<UserBT4> users = new ArrayList<>();
        users.add(new UserBT4("admin"));
        users.add(new UserBT4("manager"));
        users.add(userFactory.get());

        // ==========================================
        // 2. Tham chiếu Instance Method của kiểu cụ thể: (user) -> user.getUsername()
        // ==========================================
        Function<UserBT4, String> getNameMapper = UserBT4::getUsername;

        System.out.println("--- Danh sách Username ---");

        // ==========================================
        // Áp dụng Stream API và Method Reference
        // ==========================================
        users.stream()
                .map(UserBT4::getUsername) // Tham chiếu instance method
                .forEach(System.out::println); // Tham chiếu instance method của object cụ thể
    }
}