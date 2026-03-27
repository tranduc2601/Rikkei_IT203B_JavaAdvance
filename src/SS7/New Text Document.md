# OOAD & SOLID: Nền Tảng Kiến Trúc Phần Mềm

## 1. OOAD (Object-Oriented Analysis and Design)
### 1.1. Bản chất & Gốc rễ
- **Vấn đề giải quyết:** Code "hướng chức năng" (chạy từ trên xuống) hoặc nhồi nhét quá nhiều vào một file sẽ biến thành "Spaghetti code" khi dự án lớn lên -> Khó đọc, sửa chỗ này hỏng chỗ kia.
- **Định nghĩa:** Phương pháp nhìn hệ thống như một thế giới thực, bao gồm các "Thực thể" (Objects) sống động có thuộc tính và hành vi riêng, giao tiếp với nhau để hoàn thành nghiệp vụ.
### 1.2. Cách hoạt động (2 Bước)
- **Phân tích (Analysis):** Đi tìm "Cái gì?" (What). Bóc tách yêu cầu bài toán để tìm ra các Danh từ (đối tượng) và Động từ (hành vi).
- **Thiết kế (Design):** Trả lời "Như thế nào?" (How). Bố trí xem đối tượng nào chứa dữ liệu gì, gọi đến ai để xử lý logic.

## 2. SOLID (5 Nguyên lý thiết kế vàng)
### 2.1. S - Single Responsibility Principle (SRP)
- **Cốt lõi:** Đơn nhiệm. Một lớp chỉ nên có **MỘT lý do duy nhất để thay đổi**.
- **Lý do cần:** Tránh "Hiệu ứng Domino". Nếu một lớp làm quá nhiều việc (tính toán + lưu trữ + in ấn), khi thay đổi logic in ấn có thể vô tình làm hỏng logic tính toán.
- **Cách làm:** Chia nhỏ lớp "khổng lồ" thành nhiều lớp nhỏ, mỗi lớp quản lý đúng 1 nghiệp vụ (Ví dụ: tách `Employee` thành `SalaryCalculator`, `EmployeeRepository`).

### 2.2. O - Open/Closed Principle (OCP)
- **Cốt lõi:** Đóng/Mở. **Mở** để mở rộng tính năng mới, nhưng **Đóng** để sửa đổi mã nguồn cũ.
- **Lý do cần:** Mã nguồn cũ đang chạy ổn định (trên Production) nếu bị mở ra thêm/bớt các dòng `if-else` sẽ rất dễ sinh ra bug ẩn.
- **Cách làm:** Sử dụng tính Trừu tượng (Abstract/Interface) và Đa hình. Khi có yêu cầu mới, tạo một class mới kế thừa từ Interface cũ (Ví dụ: dùng *Strategy Pattern* để tính lương thay vì dùng `if-else` kiểm tra loại nhân viên).

### 2.3. L - Liskov Substitution Principle (LSP)
- **Cốt lõi:** Lớp con phải **thay thế hoàn toàn** được lớp cha mà không làm thay đổi tính đúng đắn của chương trình (không làm sập hệ thống).
- **Lý do cần:** Ngăn chặn việc lạm dụng "Kế thừa" (Inheritance) sai mục đích, dẫn đến việc lớp con ném ra các lỗi (Exception) vô lý mà hệ thống dùng lớp cha không lường trước được.
- **Cách làm:** Chỉ kế thừa khi lớp con có *đầy đủ hành vi* của lớp cha. (Ví dụ: `Chim Cánh Cụt` không được kế thừa từ lớp `Chim` nếu lớp `Chim` có hàm `bay()`. Hãy tách hàm `bay()` ra một interface riêng).

### 2.4. I - Interface Segregation Principle (ISP)
- **Cốt lõi:** Phân tách Giao diện. Nhiều Interface nhỏ, chuyên biệt sẽ tốt hơn một "Fat Interface" (Giao diện mập mạp, ôm đồm).
- **Lý do cần:** Không được ép buộc một lớp phải triển khai (implement) những phương thức mà bản thân nó không bao giờ sử dụng tới.
- **Cách làm:** Xé nhỏ Interface. (Ví dụ: Thay vì `IWorker` có cả `work()`, `eat()`, `sleep()`, hãy tách thành `IWorkable`, `IEatable`. Robot thì chỉ cần implement `IWorkable`).

### 2.5. D - Dependency Inversion Principle (DIP)
- **Cốt lõi:** Đảo ngược phụ thuộc. Module cấp cao (logic nghiệp vụ) không được phụ thuộc trực tiếp vào Module cấp thấp (công cụ như Database, API). Cả hai phải phụ thuộc vào **Abstraction (Interface)**.
- **Lý do cần:** Cắt đứt sự phụ thuộc cứng (Tight Coupling). Giúp hệ thống dễ dàng thay đổi các thành phần hạ tầng (đổi từ MySQL sang MongoDB) mà không phải viết lại logic nghiệp vụ cốt lõi.
- **Cách làm:** Sử dụng kỹ thuật *Dependency Injection (DI)*. Truyền (Inject) các đối tượng hạ tầng vào lớp nghiệp vụ thông qua Constructor hoặc Setter, sử dụng kiểu dữ liệu là Interface.