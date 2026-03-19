package SS1;
class User {
    private int age;

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Tuổi không thể âm! Bạn đã nhập: " + age);
        }

        this.age = age;
        System.out.println("Cập nhật tuổi thành công: " + this.age);
    }
}

public class BT3 {
    public static void main(String[] args) {
        User user = new User();

        // Trường hợp 1: Dữ liệu hợp lệ (Happy path)
        user.setAge(25);

        // Trường hợp 2: Vi phạm quy tắc (Sẽ gây ra lỗi)
        // user.setAge(-5);
    }
}