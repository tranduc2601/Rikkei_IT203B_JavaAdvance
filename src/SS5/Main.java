package SS5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductManager manager = new ProductManager();

        int choice = -1;
        while (choice != 5) {
            System.out.println("\n=== PRODUCT MANAGEMENT SYSTEM ===");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Hiển thị danh sách sản phẩm");
            System.out.println("3. Cập nhật số lượng theo ID");
            System.out.println("4. Xóa sản phẩm đã hết hàng");
            System.out.println("5. Thoát chương trình");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                    {
                        System.out.print("- Nhập ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("- Nhập Tên sản phẩm: ");
                        String name = scanner.nextLine();
                        System.out.print("- Nhập Giá: ");
                        double price = Double.parseDouble(scanner.nextLine());
                        System.out.print("- Nhập Số lượng: ");
                        int qty = Integer.parseInt(scanner.nextLine());
                        System.out.print("- Nhập Danh mục: ");
                        String category = scanner.nextLine();

                        Product newProduct = new Product(id, name, price, qty, category);
                        manager.addProduct(newProduct);
                        break;
                    }
                    case 2:
                    {
                        manager.displayProducts();
                        break;
                    }
                    case 3:
                    {
                        System.out.print("- Nhập ID sản phẩm cần cập nhật: ");
                        int updateId = Integer.parseInt(scanner.nextLine());
                        System.out.print("- Nhập Số lượng mới: ");
                        int newQty = Integer.parseInt(scanner.nextLine());
                        manager.updateQuantity(updateId, newQty);
                        break;
                    }
                    case 4:
                    {
                        manager.removeOutOfStock();
                        break;
                    }
                    case 5:
                    {
                        System.out.println("=> Lựa chọn không hợp lệ, vui lòng nhập từ 1 đến 5.");
                    }
                }
            }catch (NumberFormatException e) {
                System.out.println("=> LỖI NHẬP LIỆU: Vui lòng nhập đúng định dạng số!");
            }catch (InvalidProductException e) {
                System.out.println("Lỗi nghiệp vụ: " + e.getMessage() + "");
            }catch(Exception e) {
                System.out.println("Lỗi hệ thống: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
