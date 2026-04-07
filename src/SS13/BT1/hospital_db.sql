-- ============================================================
-- Rikkei Hospital - Module Cấp Phát Thuốc Nội Trú
-- Chạy script này trước khi chạy chương trình Java
-- ============================================================

CREATE DATABASE IF NOT EXISTS Hospital_DB
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE Hospital_DB;

-- Bảng kho thuốc
CREATE TABLE IF NOT EXISTS Medicine_Inventory (
    medicine_id   INT          PRIMARY KEY AUTO_INCREMENT,
    medicine_name VARCHAR(100) NOT NULL,
    quantity      INT          NOT NULL DEFAULT 0,
    unit          VARCHAR(20)  NOT NULL DEFAULT 'viên',
    CONSTRAINT chk_quantity CHECK (quantity >= 0)
);

-- Bảng lịch sử cấp phát thuốc
CREATE TABLE IF NOT EXISTS Prescription_History (
    history_id      INT      PRIMARY KEY AUTO_INCREMENT,
    patient_id      INT      NOT NULL,
    medicine_id     INT      NOT NULL,
    dispensed_date  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_medicine
        FOREIGN KEY (medicine_id)
        REFERENCES Medicine_Inventory(medicine_id)
        ON DELETE RESTRICT
);

-- ============================================================
-- Dữ liệu mẫu
-- ============================================================
INSERT INTO Medicine_Inventory (medicine_name, quantity, unit) VALUES
    ('Paracetamol 500mg',  100, 'viên'),
    ('Amoxicillin 250mg',   50, 'viên'),
    ('Ibuprofen 400mg',     75, 'viên'),
    ('Metformin 500mg',     30, 'viên'),
    ('Aspirin 81mg',        20, 'viên');

-- ============================================================
-- Truy vấn kiểm tra
-- ============================================================
SELECT * FROM Medicine_Inventory;

