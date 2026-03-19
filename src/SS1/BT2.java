package SS1;
import java.util.Scanner;

public class BT2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Nhập tổng số người dùng: ");
            int totalUsers = Integer.parseInt(scanner.nextLine());

            System.out.print("Nhập số lượng nhóm muốn chia: ");
            int numberOfGroups = Integer.parseInt(scanner.nextLine());

            int usersPerGroup = totalUsers / numberOfGroups;

            System.out.println("Mỗi nhóm có " + usersPerGroup + " người.");

        } catch (ArithmeticException e) {
            System.out.println("Cảnh báo: Không thể chia cho 0!");
        }

        System.out.println("Chương trình vẫn tiếp tục chạy các luồng lệnh phía sau...");

        scanner.close();
    }
}