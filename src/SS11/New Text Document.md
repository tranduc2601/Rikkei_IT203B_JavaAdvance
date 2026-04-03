# Kiến thức JDBC (Java Database Connectivity)

## 1. Giới thiệu tổng quan
### Đặt vấn đề
- Ứng dụng Java (hướng đối tượng) và DBMS (ngôn ngữ SQL) không cùng ngôn ngữ.
- Cần một "Adapter" tiêu chuẩn để Java kết nối với mọi loại Database.
### Khái niệm
- JDBC là bộ API tiêu chuẩn của Java dùng để kết nối và thực thi truy vấn tới CSDL.
### Các thành phần cốt lõi
- **DriverManager:** Quản lý danh sách Driver và thiết lập kết nối.
- **Connection:** Đối tượng đại diện cho một phiên kết nối vật lý đến Database.
- **Statement:** Đối tượng dùng để gửi các câu lệnh SQL tới máy chủ.
- **ResultSet:** Đối tượng lưu trữ bảng dữ liệu trả về (chuyên dùng cho SELECT).
### Lưu ý
- JDBC API nằm trong gói `java.sql` (có sẵn trong JDK).
- JDBC Driver là thư viện trung gian (file `.jar`) do từng hãng Database cung cấp.
- Luôn phải đóng kết nối (`close()`) để giải phóng tài nguyên.

## 2. Thiết lập môi trường và Kết nối
### Vấn đề thường gặp
- Lỗi `ClassNotFoundException` do Java không có sẵn Driver của hãng thứ ba.
### 3 Bước thiết lập chuẩn
- **Chuẩn bị DB:** Đảm bảo máy chủ CSDL đang chạy, ghi nhớ URL, User, Password.
- **Thêm Connector:** Đưa file thư viện Driver (VD: `mysql-connector-j.jar`) vào Classpath của dự án.
- **Mở kết nối:** Dùng lớp `DriverManager` để kết nối vào CSDL.
### Cấu hình và Bảo mật
- Cấu trúc URL: `jdbc:mysql://[host]:[port]/[database_name]` (Cổng mặc định MySQL là 3306).
- Phiên bản Driver phải tương thích với phiên bản Database Server.
- Không nên viết cứng (hardcode) mật khẩu trực tiếp vào code.

## 3. Thực hiện truy vấn (SELECT)
### Công cụ sử dụng
- **Statement:** Đóng vai trò "xe tải" chở câu lệnh SQL đi.
- **ResultSet:** Đóng vai trò "thùng chứa" hứng dữ liệu trả về.
### Quy trình 3 bước lấy dữ liệu
- **Tạo đối tượng:** `connection.createStatement()`
- **Chạy truy vấn:** `executeQuery(sql)` để gửi lệnh và nhận về `ResultSet`.
- **Duyệt dữ liệu:** Dùng vòng lặp `while(rs.next())` kết hợp các hàm `get...()` (VD: getString, getInt).
### Cơ chế của con trỏ (Cursor)
- Ban đầu con trỏ nằm ở vị trí trước dòng đầu tiên.
- Bắt buộc phải gọi `rs.next()` để dịch chuyển con trỏ xuống hàng có dữ liệu để đọc.
- Phương thức `executeQuery()` CHỈ dùng cho các lệnh lấy dữ liệu.

## 4. Thao tác Thêm, Sửa, Xóa (INSERT, UPDATE, DELETE)
### Vấn đề
- Các lệnh thay đổi dữ liệu không trả về bảng kết quả, nên không thể dùng `executeQuery()`.
### Giải pháp
- Dùng phương thức: `executeUpdate(sql)`
- Giá trị trả về: Một số nguyên (`int`) đại diện cho số lượng dòng (records) đã bị tác động.
### Cơ chế kiểm tra kết quả
- **> 0 :** Thao tác thành công, dữ liệu đã bị thay đổi.
- **= 0 :** Thao tác hợp lệ nhưng không có dòng nào bị thay đổi (VD: Cập nhật ID không tồn tại).
### Lưu ý sinh tử
- Các thao tác này làm thay đổi dữ liệu vĩnh viễn.
- Phải đặc biệt cẩn thận với điều kiện `WHERE` để tránh Xóa hoặc Sửa nhầm toàn bộ dữ liệu trong bảng.