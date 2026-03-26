package SS6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BT2 {
    static class Ticket {
        String ticketId;
        String roomName;
        boolean isSold;

        public Ticket(String ticketId, String roomName) {
            this.ticketId = ticketId;
            this.roomName = roomName;
            this.isSold = false;
        }
    }

    static class TicketPool {
        String roomName;
        List<Ticket> tickets = new ArrayList<>();

        public TicketPool(String roomName, int count) {
            this.roomName = roomName;
            for (int i = 1; i <= count; i++) {
                tickets.add(new Ticket(roomName + "-" + String.format("%03d", i), roomName));
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

        public int getAvailableCount() {
            return (int) tickets.stream().filter(t -> !t.isSold).count();
        }
    }

    static class BookingCounter implements Runnable {
        String counterName;
        TicketPool roomA;
        TicketPool roomB;
        int soldCount = 0;

        public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
            this.counterName = counterName;
            this.roomA = roomA;
            this.roomB = roomB;
        }

        @Override
        public void run() {
            Random rand = new Random();
            while (roomA.getAvailableCount() > 0 || roomB.getAvailableCount() > 0) {
                TicketPool targetPool = rand.nextBoolean() ? roomA : roomB;
                Ticket ticket = targetPool.sellTicket();

                if (ticket == null) {
                    targetPool = (targetPool == roomA) ? roomB : roomA;
                    ticket = targetPool.sellTicket();
                }

                if (ticket != null) {
                    soldCount++;
                    System.out.println(counterName + " đã bán vé " + ticket.ticketId);
                }

                try {
                    Thread.sleep(50); // Giả lập thời gian xử lý bán vé
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        TicketPool poolA = new TicketPool("A", 10);
        TicketPool poolB = new TicketPool("B", 10);

        BookingCounter counter1 = new BookingCounter("Quầy 1", poolA, poolB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", poolA, poolB);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();

        try {
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