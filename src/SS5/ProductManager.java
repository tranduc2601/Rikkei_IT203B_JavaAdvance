package SS5;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManager {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) throws InvalidProductException {
        boolean isExist = products.stream()
                .anyMatch(p -> p.getId() == product.getId());

        if (isExist) {
            throw new InvalidProductException("ID sản phẩm đã tồn tại trong danh sách: " + product.getId()); // [cite: 29]
        }
        products.add(product);
        System.out.println("=> Thêm sản phẩm thành công!");
    }

    // Hiển thị danh sách dưới dạng bảng [cite: 32]
    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("=> Danh sách sản phẩm hiện đang trống.");
            return;
        }
        System.out.println("+-------+----------------------+--------------+----------+-----------------+");
        System.out.printf("| %-5s | %-20s | %-12s | %-8s | %-15s |\n", "ID", "Tên SP", "Giá", "Số lượng", "Danh mục");
        System.out.println("+-------+----------------------+--------------+----------+-----------------+");
        products.forEach(System.out::println);
        System.out.println("+-------+----------------------+--------------+----------+-----------------+");
    }

    // Cập nhật số lượng bắt buộc dùng Optional [cite: 33, 34]
    public void updateQuantity(int id, int newQuantity) throws InvalidProductException {
        Optional<Product> optionalProduct = products.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setQuantity(newQuantity);
            System.out.println("=> Cập nhật số lượng thành công!");
        } else {
            throw new InvalidProductException("Không tìm thấy sản phẩm có ID: " + id); // [cite: 29]
        }
    }

    // Xóa sản phẩm có quantity = 0 bằng Java 8 [cite: 35]
    public void removeOutOfStock() {
        boolean isRemoved = products.removeIf(p -> p.getQuantity() == 0);
        if (isRemoved) {
            System.out.println("=> Đã xóa thành công các sản phẩm hết hàng.");
        } else {
            System.out.println("=> Không có sản phẩm nào hết hàng (quantity = 0) để xóa.");
        }
    }
}