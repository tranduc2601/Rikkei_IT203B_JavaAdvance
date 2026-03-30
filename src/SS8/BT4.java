package SS8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BT4 {

    interface Observer {
        void update(int temperature);
    }

    interface Subject {
        void attach(Observer o);
        void detach(Observer o);
        void notifyObservers();
    }

    static class TemperatureSensor implements Subject {
        private List<Observer> observers = new ArrayList<>();
        private int currentTemperature;

        public void setTemperature(int temp) {
            this.currentTemperature = temp;
            System.out.println("\n[Cảm biến]: Nhiệt độ phòng hiện tại = " + temp + "°C");
            notifyObservers(); // Chủ động báo tin cho mọi người đăng ký
        }

        @Override public void attach(Observer o) {
            observers.add(o);
        }
        @Override public void detach(Observer o) {
            observers.remove(o);
        }
        @Override public void notifyObservers() {
            for (Observer o : observers) {
                o.update(currentTemperature);
            }
        }
    }

    static class Fan implements Observer {
        @Override
        public void update(int temperature) {
            System.out.print("  -> Quạt: ");
            if (temperature < 20) {
                System.out.println("Nhiệt độ thấp, tự động TẮT.");
            } else if (temperature <= 25) {
                System.out.println("Chạy tốc độ TRUNG BÌNH.");
            } else {
                System.out.println("Nhiệt độ cao, chạy tốc độ MẠNH.");
            }
        }
    }

    static class Humidifier implements Observer {
        @Override
        public void update(int temperature) {
            System.out.println("  -> Máy tạo ẩm: Tự động điều chỉnh độ ẩm cho mức nhiệt " + temperature + "°C.");
        }
    }

    public static void main(String[] args) {
        TemperatureSensor sensor = new TemperatureSensor();
        Fan smartFan = new Fan();
        Humidifier smartHumidifier = new Humidifier();

        sensor.attach(smartFan);
        System.out.println("Quạt: Đã đăng ký nhận thông báo nhiệt độ.");
        sensor.attach(smartHumidifier);
        System.out.println("Máy tạo ẩm: Đã đăng ký nhận thông báo nhiệt độ.");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nNhập nhiệt độ phòng mới (nhập -1 để thoát): ");
            int temp = scanner.nextInt();
            if (temp == -1) break;

            sensor.setTemperature(temp);
        }
        scanner.close();
    }
}