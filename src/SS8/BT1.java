package SS8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BT1 {

    static class HardwareConnection {
        private static HardwareConnection instance;
        private boolean isConnected = false;

        private HardwareConnection() {
        }

        public static HardwareConnection getInstance() {
            if (instance == null) {
                instance = new HardwareConnection();
            }
            return instance;
        }

        public void connect() {
            if (!isConnected) {
                System.out.println("HardwareConnection: Đã kết nối phần cứng.");
                isConnected = true;
            } else {
                System.out.println("HardwareConnection: Phần cứng ĐÃ ĐƯỢC kết nối từ trước, đang dùng lại instance cũ.");
            }
        }
    }

    // Product Interface
    interface Device {
        void turnOn();
        void turnOff();
    }

    static class Light implements Device {
        @Override public void turnOn() { System.out.println("Đèn: Bật sáng."); }
        @Override public void turnOff() { System.out.println("Đèn: Tắt."); }
    }
    static class Fan implements Device {
        @Override public void turnOn() { System.out.println("Quạt: Bắt đầu quay."); }
        @Override public void turnOff() { System.out.println("Quạt: Ngừng quay."); }
    }
    static class AirConditioner implements Device {
        @Override public void turnOn() { System.out.println("Điều hòa: Bật làm mát."); }
        @Override public void turnOff() { System.out.println("Điều hòa: Tắt."); }
    }

    abstract static class DeviceFactory {
        public abstract Device createDevice();
    }

    static class LightFactory extends DeviceFactory {
        @Override public Device createDevice() {
            System.out.println("LightFactory: Đã tạo đèn mới.");
            return new Light();
        }
    }
    static class FanFactory extends DeviceFactory {
        @Override public Device createDevice() {
            System.out.println("FanFactory: Đã tạo quạt mới.");
            return new Fan();
        }
    }
    static class AirConditionerFactory extends DeviceFactory {
        @Override public Device createDevice() {
            System.out.println("AirConditionerFactory: Đã tạo điều hòa mới.");
            return new AirConditioner();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Device> devices = new ArrayList<>();

        while (true) {
            System.out.println("\n--- SMART HOME MENU ---");
            System.out.println("1. Kết nối phần cứng (Singleton)");
            System.out.println("2. Tạo thiết bị mới (Factory Method)");
            System.out.println("3. Bật tất cả thiết bị hiện có");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            if (choice.equals("0")) break;

            switch (choice) {
                case "1":
                    HardwareConnection.getInstance().connect();
                    break;
                case "2":
                    System.out.print("Chọn loại (1. Đèn, 2. Quạt, 3. Điều hòa): ");
                    String type = scanner.nextLine();
                    DeviceFactory factory = null;
                    if (type.equals("1")) factory = new LightFactory();
                    else if (type.equals("2")) factory = new FanFactory();
                    else if (type.equals("3")) factory = new AirConditionerFactory();

                    if (factory != null) {
                        devices.add(factory.createDevice());
                    } else {
                        System.out.println("Lựa chọn không hợp lệ.");
                    }
                    break;
                case "3":
                    if (devices.isEmpty()) {
                        System.out.println("Chưa có thiết bị nào được tạo.");
                    } else {
                        for (Device d : devices) d.turnOn();
                    }
                    break;
                default:
                    System.out.println("Vui lòng chọn lại.");
            }
        }
    }
}