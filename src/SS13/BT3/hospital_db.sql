CREATE DATABASE IF NOT EXISTS Hospital_DB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE Hospital_DB;

DROP TABLE IF EXISTS Beds;
DROP TABLE IF EXISTS Patients;

CREATE TABLE Patients (
    patient_id   INT          PRIMARY KEY AUTO_INCREMENT,
    patient_name VARCHAR(100) NOT NULL,
    deposit      DOUBLE       NOT NULL DEFAULT 0,
    status       VARCHAR(30)  NOT NULL DEFAULT 'Dang dieu tri'
);

CREATE TABLE Beds (
    bed_id     INT         PRIMARY KEY AUTO_INCREMENT,
    patient_id INT,
    status     VARCHAR(20) NOT NULL DEFAULT 'Trong',
    CONSTRAINT fk_bed_patient FOREIGN KEY (patient_id) REFERENCES Patients(patient_id)
);

INSERT INTO Patients (patient_name, deposit, status) VALUES
    ('Nguyen Van A', 5000000, 'Dang dieu tri'),
    ('Tran Thi B',    500000, 'Dang dieu tri'),
    ('Le Van C',     3000000, 'Dang dieu tri');

INSERT INTO Beds (patient_id, status) VALUES
    (1, 'Co nguoi'),
    (2, 'Co nguoi'),
    (3, 'Co nguoi');

SELECT * FROM Patients;
SELECT * FROM Beds;

