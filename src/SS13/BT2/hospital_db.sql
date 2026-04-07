CREATE DATABASE IF NOT EXISTS Hospital_DB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE Hospital_DB;

CREATE TABLE IF NOT EXISTS Patient_Wallet (
    patient_id   INT          PRIMARY KEY AUTO_INCREMENT,
    patient_name VARCHAR(100) NOT NULL,
    balance      DOUBLE       NOT NULL DEFAULT 0,
    CONSTRAINT chk_balance CHECK (balance >= 0)
);

CREATE TABLE IF NOT EXISTS Invoices (
    invoice_id INT         PRIMARY KEY AUTO_INCREMENT,
    patient_id INT         NOT NULL,
    amount     DOUBLE      NOT NULL,
    status     VARCHAR(20) NOT NULL DEFAULT 'UNPAID',
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES Patient_Wallet(patient_id)
);

INSERT INTO Patient_Wallet (patient_name, balance) VALUES
    ('Nguyen Van A', 5000000),
    ('Tran Thi B',   3000000),
    ('Le Van C',     1500000);

INSERT INTO Invoices (patient_id, amount, status) VALUES
    (1, 1200000, 'UNPAID'),
    (2,  800000, 'UNPAID'),
    (3, 2000000, 'UNPAID');

SELECT * FROM Patient_Wallet;
SELECT * FROM Invoices;

