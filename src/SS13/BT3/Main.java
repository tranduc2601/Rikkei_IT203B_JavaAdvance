package SS13.BT3;

public class Main {
    public static void main(String[] args) {
        FixedModule module = new FixedModule();

        System.out.println("RIKKEI HOSPITAL - XUAT VIEN VA THANH TOAN");
        System.out.println("---");

        System.out.println("TRANG THAI BAN DAU:");
        module.printPatients();
        module.printBeds();

        System.out.println("\nDEMO 1: Xuat vien hop le - BenhNhanID=1, VienPhi=3000000");
        module.xuatVienVaThanhToan(1, 3000000);
        module.printPatients();
        module.printBeds();

        System.out.println("\nDEMO 2: Bay 1 - So du khong du - BenhNhanID=2, VienPhi=1500000");
        module.xuatVienVaThanhToan(2, 1500000);
        module.printPatients();
        module.printBeds();

        System.out.println("\nDEMO 3: Bay 2 - Benh nhan khong ton tai - BenhNhanID=999, VienPhi=1000000");
        module.xuatVienVaThanhToan(999, 1000000);
        module.printPatients();
        module.printBeds();

        System.out.println("\nKET LUAN:");
        System.out.println("- 3 buoc lien hoan duoc gom vao 1 transaction: tru tien, giai phong giuong, cap nhat trang thai.");
        System.out.println("- Bay 1: so du < vien phi -> nem exception, rollback.");
        System.out.println("- Bay 2: rowsAffected=0 -> nem exception, rollback.");
    }
}

