# JDBC Nâng Cao: PreparedStatement và CallableStatement

## 1. PreparedStatement (Chống SQL Injection)
### Vấn đề của Statement
- Gửi lệnh SQL dưới dạng văn bản thuần túy.
- Dễ bị tấn công **SQL Injection** khi dùng cách cộng chuỗi (Ví dụ: `' OR '1'='1`).
- Database phải biên dịch lại lệnh mỗi lần chạy.
### Đặc điểm của PreparedStatement
- **Biên dịch trước (Pre-compiled):** Database chuẩn bị khung lệnh từ trước.
- **Tham số hóa:** Dùng dấu `?` làm trình giữ chỗ (placeholder), tách biệt hoàn toàn lệnh và dữ liệu.
- Kế thừa từ `Statement`.
### Quy trình 4 bước
1. Viết SQL có chứa dấu `?`.
2. Tạo đối tượng: `conn.prepareStatement(sql)`.
3. Truyền dữ liệu: Dùng `set...()` (VD: `setString`, `setInt`).
4. Thực thi: `executeQuery()` hoặc `executeUpdate()` (Không truyền tham số `sql`).
### Lưu ý khi truyền tham số
- Số thứ tự (Index) bắt đầu từ **1**.
- Kiểu dữ liệu trong hàm `set` phải khớp với cột trong DB.
- Có thể xóa các tham số đã gán bằng `clearParameters()`.

## 2. CallableStatement (Gọi Stored Procedure)
### Đặt vấn đề
- Logic nghiệp vụ phức tạp nếu viết bằng Java sẽ chạy chậm và dài dòng.
- Cần một công cụ để gọi trực tiếp các hàm/thủ tục (Stored Procedure) đã viết sẵn trong Database.
### Đặc điểm cốt lõi
- Chuyên dùng để thực thi **Stored Procedures**.
- Cú pháp chuẩn: `{call ten_thu_tuc(?, ?)}`.
- Hỗ trợ 3 loại tham số:
    - **IN:** Đầu vào (Giống PreparedStatement).
    - **OUT:** Đầu ra (Nhận kết quả từ DB trả về).
    - **INOUT:** Kết hợp cả 2.
### Quy trình xử lý tham số OUT
- **Bắt buộc có 2 bước:**
    1. **Đăng ký (Register):** Dùng `cstmt.registerOutParameter(index, sqlType)`.
    2. **Nhận (Retrieve):** Sau khi chạy lệnh `execute()`, dùng `cstmt.get...()` để lấy giá trị.
- **Về kiểu dữ liệu SQL:**
    - Phải dùng các hằng số thuộc lớp `java.sql.Types` (VD: `Types.INTEGER`, `Types.DOUBLE`) khi đăng ký tham số.
- **Luồng chuẩn:** Đăng ký (Register) -> Thực thi (Execute) -> Nhận kết quả (Get).