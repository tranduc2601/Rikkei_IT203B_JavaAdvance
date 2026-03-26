package SS6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BT1 {
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
        int totalGenerated;

        public TicketPool(String roomName, int count) {
            this.roomName = roomName;
            addTickets(count);
        }

        public synchronized void addTickets(int count) {
            for (int i = 0; i < count; i++) {
                totalGenerated++;
                tickets.add(new Ticket(roomName + "-" + String.format("%03d", totalGenerated)));
            }
        }

        public synchronized Ticket sellTicket() {
            for (Ticket t : tickets) {
                if (!t.isSold) {
                    t.isSold = true;
                    return t;
                }
            }
            return null;
        }

        public synchronized int getAvailableCount() {
            return (int) tickets.stream().filter(t -> !t.isSold).count();
        }
    }

    static class TicketSupplier implements Runnable {
        TicketPool roomA;
        TicketPool roomB;
        int supplyCount;
        int interval;
        int rounds;

        public TicketSupplier(TicketPool roomA, TicketPool roomB, int supplyCount, int interval, int rounds) {
            this.roomA = roomA;
            this.roomB = roomB;
            this.supplyCount = supplyCount;
            this.interval = interval;
            this.rounds = rounds;
        }

        @Override
        public void run() {
            for (int i = 0; i < rounds; i++) {
                try {
                    Thread.sleep(interval);
                    roomA.addTickets(supplyCount);
                    roomB.addTickets(supplyCount);
                    System.out.println("\n[Nhà cung cấp]: Đã thêm " + supplyCount + " vé vào phòng A và B\n");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class BookingCounter implements Runnable {
        String counterName;
        TicketPool roomA;
        TicketPool roomB;
        int soldCount = 0;
        SupplierStatus status;

        public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB, SupplierStatus status) {
            this.counterName = counterName;
            this.roomA = roomA;
            this.roomB = roomB;
            this.status = status;
        }

        @Override
        public void run() {
            Random rand = new Random();
            while (!status.isSupplierDone || roomA.getAvailableCount() > 0 || roomB.getAvailableCount() > 0) {
                TicketPool targetPool = rand.nextBoolean() ? roomA : roomB;
                Ticket ticket = targetPool.sellTicket();

                if (ticket != null) {
                    soldCount++;
                    System.out.println(counterName + " đã bán vé " + ticket.ticketId);
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class SupplierStatus {
        boolean isSupplierDone = false;
    }

    public static void main(String[] args) {
        TicketPool poolA = new TicketPool("A", 10);
        TicketPool poolB = new TicketPool("B", 10);
        SupplierStatus status = new SupplierStatus();

        TicketSupplier supplier = new TicketSupplier(poolA, poolB, 3, 3000, 2);
        BookingCounter counter1 = new BookingCounter("Quầy 1", poolA, poolB, status);
        BookingCounter counter2 = new BookingCounter("Quầy 2", poolA, poolB, status);

        Thread ts = new Thread(supplier);
        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        ts.start();
        t1.start();
        t2.start();

        try {
            ts.join();
            status.isSupplierDone = true;
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Kết thúc chương trình ---");
        System.out.println("Quầy 1 bán được: " + counter1.soldCount + " vé");
        System.out.println("Quầy 2 bán được: " + counter2.soldCount + " vé");
        System.out.println("Vé còn lại phòng A: " + poolA.getAvailableCount());
        System.out.println("Vé còn lại phòng B: " + poolB.getAvailableCount());
    }
}