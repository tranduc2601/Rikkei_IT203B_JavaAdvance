-- Rikkei Hospital BT4 - Dashboard Y ta (N+1 Query Problem)
-- Pass MySQL root: tmd2601.

CREATE DATABASE IF NOT EXISTS Hospital_DB
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE Hospital_DB;

DROP TABLE IF EXISTS DichVuSuDung;
DROP TABLE IF EXISTS BenhNhan;

CREATE TABLE BenhNhan (
    maBenhNhan   INT          PRIMARY KEY AUTO_INCREMENT,
    hoTen        VARCHAR(100) NOT NULL,
    tuoi         INT          NOT NULL,
    gioiTinh     VARCHAR(10)  NOT NULL,
    phong        VARCHAR(20)  NOT NULL,
    ngayNhapVien DATE         NOT NULL
);

CREATE TABLE DichVuSuDung (
    maDichVu   INT          PRIMARY KEY AUTO_INCREMENT,
    maBenhNhan INT          NOT NULL,
    tenDichVu  VARCHAR(100) NOT NULL,
    loai       VARCHAR(50)  NOT NULL,
    gioSuDung  TIME         NOT NULL,
    CONSTRAINT fk_dv_bn FOREIGN KEY (maBenhNhan)
        REFERENCES BenhNhan(maBenhNhan)
        ON DELETE CASCADE
);

-- Sinh 500 benh nhan:
--   i % 5 != 0  -> co dich vu  (400 benh nhan)
--   i % 5  = 0  -> CHUA co dich vu (100 benh nhan) - de demo Bay 2
DROP PROCEDURE IF EXISTS gen_dashboard_data;
DELIMITER //
CREATE PROCEDURE gen_dashboard_data()
BEGIN
    DECLARE i       INT DEFAULT 1;
    DECLARE lastId  INT;
    WHILE i <= 500 DO
        INSERT INTO BenhNhan (hoTen, tuoi, gioiTinh, phong, ngayNhapVien)
        VALUES (
            CONCAT(ELT(1 + (i % 5), 'Nguyen Van', 'Tran Thi', 'Le Van', 'Pham Thi', 'Hoang Duc'),
                   ' BenhNhan ', LPAD(i, 3, '0')),
            18 + (i % 60),
            IF(i % 2 = 0, 'Nam', 'Nu'),
            CONCAT('Phong_', 100 + (i % 20)),
            CURDATE()
        );
        SET lastId = LAST_INSERT_ID();

        IF i % 5 != 0 THEN
            INSERT INTO DichVuSuDung (maBenhNhan, tenDichVu, loai, gioSuDung)
            VALUES (lastId, CONCAT('Paracetamol 500mg #', i), 'Thuoc', '08:00:00');

            IF i % 3 != 0 THEN
                INSERT INTO DichVuSuDung (maBenhNhan, tenDichVu, loai, gioSuDung)
                VALUES (lastId, CONCAT('Truyen dich NaCl #', i), 'Dich vu', '14:00:00');
            END IF;
        END IF;

        SET i = i + 1;
    END WHILE;
END //
DELIMITER ;

CALL gen_dashboard_data();
DROP PROCEDURE IF EXISTS gen_dashboard_data;

SELECT COUNT(*) AS tong_benh_nhan FROM BenhNhan;
SELECT COUNT(*) AS benh_nhan_chua_co_dich_vu
FROM   BenhNhan bn
WHERE  NOT EXISTS (
    SELECT 1 FROM DichVuSuDung dv WHERE dv.maBenhNhan = bn.maBenhNhan
);

