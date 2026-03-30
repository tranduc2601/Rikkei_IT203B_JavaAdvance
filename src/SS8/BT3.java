package SS8;

import java.util.Stack;

public class BT3 {

    static class Light {
        public void turnOn() { System.out.println("Đèn: Bật"); }
        public void turnOff() { System.out.println("Đèn: Tắt"); }
    }
    static class AirConditioner {
        private int temperature = 25;
        public void setTemperature(int t) {
            this.temperature = t;
            System.out.println("Điều hòa: Nhiệt độ = " + t + "°C");
        }
        public int getTemperature() { return temperature; }
    }

    // --- 2. COMMAND INTERFACE & CONCRETE COMMANDS ---
    interface Command {
        void execute();
        void undo();
    }

    static class LightOnCommand implements Command {
        private Light light;
        public LightOnCommand(Light light) { this.light = light; }
        @Override public void execute() { light.turnOn(); }
        @Override public void undo() {
            System.out.print("Undo -> ");
            light.turnOff();
        }
    }

    static class LightOffCommand implements Command {
        private Light light;
        public LightOffCommand(Light light) { this.light = light; }
        @Override public void execute() { light.turnOff(); }
        @Override public void undo() {
            System.out.print("Undo -> ");
            light.turnOn();
        }
    }

    static class ACSetTempCommand implements Command {
        private AirConditioner ac;
        private int prevTemp;
        private int newTemp;

        public ACSetTempCommand(AirConditioner ac, int newTemp) {
            this.ac = ac;
            this.newTemp = newTemp;
        }
        @Override public void execute() {
            prevTemp = ac.getTemperature();
            ac.setTemperature(newTemp);
        }
        @Override public void undo() {
            System.out.print("Undo -> ");
            ac.setTemperature(prevTemp);
        }
    }

    static class RemoteControl {
        private Command[] slots = new Command[5]; // Giả sử remote có 5 nút
        private Stack<Command> history = new Stack<>();

        public void setCommand(int slot, Command command) {
            slots[slot] = command;
            System.out.println("Đã gán lệnh cho nút " + slot);
        }

        public void pressButton(int slot) {
            if (slots[slot] != null) {
                slots[slot].execute();
                history.push(slots[slot]);
            } else {
                System.out.println("Nút " + slot + " chưa được gán lệnh.");
            }
        }

        public void pressUndo() {
            if (!history.isEmpty()) {
                Command lastCommand = history.pop();
                lastCommand.undo();
            } else {
                System.out.println("Không có lịch sử để Undo.");
            }
        }
    }

    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();
        Light livingRoomLight = new Light();
        AirConditioner bedroomAC = new AirConditioner();

        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);
        Command acSet26 = new ACSetTempCommand(bedroomAC, 26);

        remote.setCommand(1, lightOn);
        remote.setCommand(2, lightOff);
        remote.setCommand(3, acSet26);

        System.out.println("\n--- BẤM NÚT TRÊN REMOTE ---");
        System.out.println("Nhấn nút 1:"); remote.pressButton(1);
        System.out.println("Nhấn nút 2:"); remote.pressButton(2);

        System.out.println("\n--- THỬ TÍNH NĂNG UNDO ---");
        remote.pressUndo();
        remote.pressUndo();

        System.out.println("\n--- ĐIỀU HÒA ---");
        System.out.println("Nhấn nút 3:"); remote.pressButton(3);
        remote.pressUndo();
    }
}