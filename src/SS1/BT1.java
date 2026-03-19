package SS1;
import java.util.Scanner;
import java.time.Year;

public class BT1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Vui lòng nhập năm sinh của bạn: ");
        String inputStr = scanner.nextLine();

        try {
            // Cố gắng chuyển chuỗi thành số
            int birthYear = Integer.parseInt(inputStr);
            int currentYear = Year.now().getValue(); // Lấy năm hiện tại
            int age = currentYear - birthYear;

            System.out.println("Tuổi của bạn là: " + age);

        } catch (NumberFormatException e) {
            // Nếu parse thất bại, nhảy ngay vào đây
            System.out.println("Lỗi: Bạn phải nhập một con số hợp lệ (VD: 1995), không được nhập chữ!");

        } finally {
            // Dọn dẹp tài nguyên dù có lỗi hay không
            scanner.close();
            System.out.println("Thực hiện dọn dẹp tài nguyên trong finally...");
        }
    }
}