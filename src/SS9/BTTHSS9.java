package SS9;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Product {
    protected String id;
    protected String name;
    protected double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public abstract void displayInfo();
}

class PhysicalProduct extends Product {
    private double weight;

    public PhysicalProduct(String id, String name, double price, double weight) {
        super(id, name, price);
        this.weight = weight;
    }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    @Override
    public void displayInfo() {
        System.out.printf("[Physical] ID: %s | Tên: %s | Giá: %.2f | Trọng lượng: %.2f kg\n", id, name, price, weight);
    }
}

class DigitalProduct extends Product {
    private double sizeInMB;

    public DigitalProduct(String id, String name, double price, double sizeInMB) {
        super(id, name, price);
        this.sizeInMB = sizeInMB;
    }

    public double getSizeInMB() { return sizeInMB; }
    public void setSizeInMB(double sizeInMB) { this.sizeInMB = sizeInMB; }

    @Override
    public void displayInfo() {
        System.out.printf("[Digital] ID: %s | Tên: %s | Giá: %.2f | Dung lượng: %.2f MB\n", id, name, price, sizeInMB);
    }
}

class ProductDatabase {
    private static ProductDatabase instance;

    private List<Product> products;

    private ProductDatabase() {
        products = new ArrayList<>();
    }

    public static synchronized ProductDatabase getInstance() {
        if (instance == null) {
            instance = new ProductDatabase();
        }
        return instance;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product findById(String id) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean deleteProduct(String id) {
        Product p = findById(id);
        if (p != null) {
            products.remove(p);
            return true;
        }
        return false;
    }
}

class ProductFactory {
    public static Product createProduct(int type, String id, String name, double price, double extraProperty) {
        if (type == 1) {
            return new PhysicalProduct(id, name, price, extraProperty);
        } else if (type == 2) {
            return new DigitalProduct(id, name, price, extraProperty);
        }
        return null;
    }
}

public class BTTHSS9 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductDatabase database = ProductDatabase.getInstance();

        while (true) {
            System.out.println("\n--- QUẢN LÝ SẢN PHẨM ---");
            System.out.println("1. Thêm mới sản phẩm");
            System.out.println("2. Xem danh sách sản phẩm");
            System.out.println("3. Cập nhật thông tin sản phẩm");
            System.out.println("4. Xoá sản phẩm");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Chọn loại sản phẩm (1. Vật lý | 2. Kỹ thuật số): ");
                    int type = Integer.parseInt(scanner.nextLine());
                    if (type != 1 && type != 2) {
                        System.out.println("Loại không hợp lệ!");
                        break;
                    }

                    System.out.print("Nhập ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập Tên: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập Giá: ");
                    double price = Double.parseDouble(scanner.nextLine());

                    double extra = 0;
                    if (type == 1) {
                        System.out.print("Nhập Trọng lượng (weight): ");
                        extra = Double.parseDouble(scanner.nextLine());
                    } else {
                        System.out.print("Nhập Dung lượng (size in MB): ");
                        extra = Double.parseDouble(scanner.nextLine());
                    }

                    Product newProduct = ProductFactory.createProduct(type, id, name, price, extra);
                    database.addProduct(newProduct);
                    System.out.println("=> Đã thêm sản phẩm thành công!");
                    break;

                case 2:
                    System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
                    List<Product> list = database.getAllProducts();
                    if (list.isEmpty()) {
                        System.out.println("Kho hàng trống.");
                    } else {
                        for (Product p : list) {
                            p.displayInfo();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Nhập ID sản phẩm cần cập nhật: ");
                    String updateId = scanner.nextLine();
                    Product pToUpdate = database.findById(updateId);

                    if (pToUpdate == null) {
                        System.out.println("=> Không tìm thấy sản phẩm với ID này!");
                    } else {
                        System.out.print("Nhập Tên mới (bỏ qua nếu không đổi): ");
                        String newName = scanner.nextLine();
                        if (!newName.trim().isEmpty()) pToUpdate.setName(newName);

                        System.out.print("Nhập Giá mới (nhập -1 nếu không đổi): ");
                        double newPrice = Double.parseDouble(scanner.nextLine());
                        if (newPrice != -1) pToUpdate.setPrice(newPrice);

                        if (pToUpdate instanceof PhysicalProduct) {
                            PhysicalProduct physProduct = (PhysicalProduct) pToUpdate;
                            System.out.print("Nhập Trọng lượng mới (nhập -1 nếu không đổi): ");
                            double newWeight = Double.parseDouble(scanner.nextLine());
                            if (newWeight != -1) physProduct.setWeight(newWeight);
                        } else if (pToUpdate instanceof DigitalProduct) {
                            DigitalProduct digiProduct = (DigitalProduct) pToUpdate;
                            System.out.print("Nhập Dung lượng mới (nhập -1 nếu không đổi): ");
                            double newSize = Double.parseDouble(scanner.nextLine());
                            if (newSize != -1) digiProduct.setSizeInMB(newSize);
                        }
                        System.out.println("=> Đã cập nhật thành công!");
                    }
                    break;

                case 4:
                    System.out.print("Nhập ID sản phẩm cần xóa: ");
                    String deleteId = scanner.nextLine();
                    boolean isDeleted = database.deleteProduct(deleteId);
                    if (isDeleted) {
                        System.out.println("=> Đã xóa sản phẩm thành công!");
                    } else {
                        System.out.println("=> Xóa thất bại. Không tìm thấy ID!");
                    }
                    break;

                case 5:
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn từ 1-5!");
            }
        }
    }
}