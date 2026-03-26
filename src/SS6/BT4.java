package SS6;

import java.util.ArrayList;
import java.util.List;

public class BT4 {
    static class Ticket {
        String ticketId;
        boolean isSold;

        public Ticket(String ticketId) {
            this.ticketId = ticketId;
            this.isSold = false;
        }
    }

    static class TicketPool {
        String roomName;
        List<Ticket> tickets = new ArrayList<>();
        int totalGenerated = 0;
        boolean isSupplierActive = true;

        public TicketPool(String roomName) {
            this.roomName = roomName;
        }

        public synchronized void addTickets(int count) {
            for (int i = 0; i < count; i++) {
                totalGenerated++;
                tickets.add(new Ticket(roomName + "-" + String.format("%03d", totalGenerated)));
            }
            System.out.println("[Nhà cung cấp]: Đã thêm " + count + " vé vào phòng " + roomName);
            notifyAll(); // Đánh thức các quầy đang chờ vé
        }

        public synchronized Ticket sellTicket(String counterName) throws InterruptedException {
            while (getAvailableCount() == 0) {
                if (!isSupplierActive) return null; // Nếu hết vé và nhà cung cấp đã nghỉ -> Dừng quầy

                System.out.println(counterName + ": Hết vé phòng " + roomName + ", đang chờ...");
                wait(); // Hết vé -> Giao lại ổ khóa và đi ngủ
            }

            for (Ticket t : tickets) {
                if (!t.isSold) {
                    t.isSold = true;
                    return t;
                }
            }
            return null;
        }

        public synchronized void stopSupplier() {
            isSupplierActive = false;
            notifyAll(); // Đánh thức các quầy đang chờ để chúng tự động kết thúc
        }

        public synchronized int getAvailableCount() {
            return (int) tickets.stream().filter(t -> !t.isSold).count();
        }
    }

    static class TicketSupplier implements Runnable {
        TicketPool roomA;
        TicketPool roomB;

        public TicketSupplier(TicketPool roomA, TicketPool roomB) {
            this.roomA = roomA;
            this.roomB = roomB;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000); // Đợi 2 giây cho quầy bán hết vé ban đầu
                roomA.addTickets(3);
                roomB.addTickets(3);

                Thread.sleep(2000); // Đợi 2 giây
                roomA.addTickets(2);

                Thread.sleep(1000);
                roomA.stopSupplier();
                roomB.stopSupplier();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class BookingCounter implements Runnable {
        String counterName;
        TicketPool targetPool;

        public BookingCounter(String counterName, TicketPool targetPool) {
            this.counterName = counterName;
            this.targetPool = targetPool;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Ticket ticket = targetPool.sellTicket(counterName);
                    if (ticket == null) break;

                    System.out.println(counterName + " bán vé " + ticket.ticketId);
                    Thread.sleep(300); // Tốc độ bán vé
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        TicketPool poolA = new TicketPool("A");
        TicketPool poolB = new TicketPool("B");

        // Khởi tạo vé ban đầu
        poolA.addTickets(3);
        poolB.addTickets(2);

        TicketSupplier supplier = new TicketSupplier(poolA, poolB);
        BookingCounter counter1 = new BookingCounter("Quầy 1", poolA);
        BookingCounter counter2 = new BookingCounter("Quầy 2", poolB);

        Thread ts = new Thread(supplier);
        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();
        ts.start();
    }
}