package SS8;

import java.util.Scanner;

public class BT2 {

    interface TemperatureSensor {
        double getTemperatureCelsius();
    }

    static class OldThermometer {
        public int getTemperatureFahrenheit() {
            return 67;
        }
    }

    static class ThermometerAdapter implements TemperatureSensor {
        private OldThermometer oldThermometer;

        public ThermometerAdapter(OldThermometer oldThermometer) {
            this.oldThermometer = oldThermometer;
        }

        @Override
        public double getTemperatureCelsius() {
            int fahrenheit = oldThermometer.getTemperatureFahrenheit();
            return (fahrenheit - 32) * 5.0 / 9.0;
        }
    }

    static class SmartHomeFacade {
        private TemperatureSensor tempSensor;
        public SmartHomeFacade(TemperatureSensor tempSensor) {
            this.tempSensor = tempSensor;
        }

        public void leaveHome() {
            System.out.println("--- CHẾ ĐỘ RỜI NHÀ ---");
            System.out.println("FACADE: Tắt đèn");
            System.out.println("FACADE: Tắt quạt");
            System.out.println("FACADE: Tắt điều hòa");
        }

        public void sleepMode() {
            System.out.println("--- CHẾ ĐỘ NGỦ ---");
            System.out.println("FACADE: Tắt đèn");
            System.out.println("FACADE: Điều hòa set 28°C");
            System.out.println("FACADE: Quạt chạy tốc độ thấp");
        }

        public void getCurrentTemperature() {
            double celsius = tempSensor.getTemperatureCelsius();
            System.out.printf("Nhiệt độ hiện tại: %.1f°C (chuyển đổi từ hệ thống cũ)\n", celsius);
        }
    }

    public static void main(String[] args) {
        OldThermometer oldSensor = new OldThermometer();
        TemperatureSensor adapter = new ThermometerAdapter(oldSensor);

        SmartHomeFacade facade = new SmartHomeFacade(adapter);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== BẢNG ĐIỀU KHIỂN NHÀ THÔNG MINH ===");
            System.out.println("1. Xem nhiệt độ hiện tại (Dùng Adapter)");
            System.out.println("2. Kích hoạt chế độ Rời nhà (Dùng Facade)");
            System.out.println("3. Kích hoạt chế độ Ngủ (Dùng Facade)");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            if (choice.equals("0")) break;

            switch (choice) {
                case "1": facade.getCurrentTemperature(); break;
                case "2": facade.leaveHome(); break;
                case "3": facade.sleepMode(); break;
                default: System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}