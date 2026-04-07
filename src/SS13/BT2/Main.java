package SS13.BT2;

public class Main {
    public static void main(String[] args) {
        FixedModule module = new FixedModule();

        System.out.println("RIKKEI HOSPITAL - MODULE THANH TOAN VIEN PHI");
        System.out.println("---");

        System.out.println("TRANG THAI BAN DAU:");
        module.printWallet();
        module.printInvoices();

        System.out.println("DEMO 1: Thanh toan hop le - PatientID=1, InvoiceID=1, So tien=1200000");
        module.thanhToanVienPhi(1, 1, 1200000);
        module.printWallet();
        module.printInvoices();

        System.out.println("DEMO 2: So du khong du - PatientID=3, InvoiceID=3, So tien=2000000");
        module.thanhToanVienPhi(3, 3, 2000000);
        module.printWallet();
        module.printInvoices();

        System.out.println("KET LUAN:");
        System.out.println("- Thieu rollback: ket noi bi treo, gay lang phi tai nguyen va nguy co loi cac thao tac khac.");
        System.out.println("- Co rollback: khi loi xay ra, du lieu duoc hoan tac ve trang thai an toan truoc do.");
    }
}

