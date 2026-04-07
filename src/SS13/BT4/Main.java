package SS13.BT4;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        BuggyModule buggy = new BuggyModule();
        FixedModule fixed = new FixedModule();

        System.out.println("RIKKEI HOSPITAL - DASHBOARD Y TA");
        System.out.println("---");

        long t0 = System.currentTimeMillis();
        List<BenhNhanDTO> buggyResult = buggy.getDashboard();
        long buggyTime = System.currentTimeMillis() - t0;
        System.out.println("BUGGY (N+1): " + buggy.getLastQueryCount() + " queries, "
                + buggyTime + "ms, " + buggyResult.size() + " benh nhan");

        t0 = System.currentTimeMillis();
        List<BenhNhanDTO> fixedResult = fixed.getDashboard();
        long fixedTime = System.currentTimeMillis() - t0;
        System.out.println("FIXED (JOIN): 1 query, " + fixedTime + "ms, "
                + fixedResult.size() + " benh nhan");

        System.out.println("---");
        System.out.println("DASHBOARD (5 dau):");
        int count = 0;
        BenhNhanDTO noService = null;
        for (BenhNhanDTO dto : fixedResult) {
            if (count < 5) { printDTO(dto); count++; }
            if (noService == null && dto.getDsDichVu().isEmpty()) noService = dto;
        }

        if (noService != null) {
            System.out.println("...");
            System.out.println("BAY 2 - Benh nhan chua co dich vu (van hien thi, khong NPE):");
            printDTO(noService);
        }
    }

    private static void printDTO(BenhNhanDTO dto) {
        System.out.printf("[%d] %s (%s, %dt) - %s - %s%n",
                dto.getMaBenhNhan(), dto.getHoTen(), dto.getGioiTinh(),
                dto.getTuoi(), dto.getPhong(), dto.getNgayNhapVien());
        if (dto.getDsDichVu().isEmpty()) {
            System.out.println("    chua co dich vu");
        } else {
            for (DichVu dv : dto.getDsDichVu()) {
                System.out.println("    " + dv);
            }
        }
    }
}

