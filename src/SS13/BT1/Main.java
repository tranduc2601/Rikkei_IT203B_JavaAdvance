package SS13.BT1;

public class Main {
    public static void main(String[] args) {
        FixedModule module = new FixedModule();

        System.out.println("RIKKEI HOSPITAL - MODULE CAP PHAT THUOC NOI TRU");
        System.out.println("-------------------------------");

        System.out.println("\nTRANG THAI BAN DAU");
        module.printInventory();
        module.printPrescriptionHistory();

        System.out.println("\nDEMO 1: Cap phat binh thuong");
        System.out.println("Bac si ke Paracetamol 500mg (ID=1) cho Benh nhan ID=101");
        module.capPhatThuoc(1, 101);
        module.printInventory();
        module.printPrescriptionHistory();

        System.out.println("\nDEMO 2: Cap phat thuoc khong ton tai (ID=999)");
        module.capPhatThuoc(999, 102);

        System.out.println("\nTRANG THAI CUOI");
        module.printInventory();
        module.printPrescriptionHistory();

        System.out.println("\nKET LUAN:");
        System.out.println("- Transaction dam bao atomicity: kho thuoc & lich su cap phat luon nhat quan.");
        System.out.println("- Khong dung transaction: thuoc bi tru nhung khong co ban ghi lich su -> that thoat & sai lech so sach.");
    }
}
