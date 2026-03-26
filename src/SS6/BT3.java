package SS6;

import java.util.ArrayList;
import java.util.List;

public class BT3 {
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

        public TicketPool(String roomName, int count) {
            this.roomName = roomName;
            for (int i = 1; i <= count; i++) {
                tickets.add(new Ticket(roomName + "-" + String.format("%03d", i)));
            }
        }

        public Ticket getUnsoldTicket() {
            for (Ticket t : tickets) {
                if (!t.isSold) return t;
            }
            return null;
        }
    }

    static class BookingCounter implements Runnable {
        String counterName;
        TicketPool roomA;
        TicketPool roomB;

        public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
            this.counterName = counterName;
            this.roomA = roomA;
            this.roomB = roomB;
        }

        public boolean sellCombo() {
            // CÁCH PHÒNG CHỐNG DEADLOCK: Sắp xếp thứ tự khóa (Lock Ordering)
            // Luôn khóa pool có tên nhỏ hơn (A) trước, lớn hơn (B) sau.
            TicketPool firstLock = roomA.roomName.compareTo(roomB.roomName) < 0 ? roomA : roomB;
            TicketPool secondLock = firstLock == roomA ? roomB : roomA;

            synchronized (firstLock) {
                // Giả lập độ trễ để dễ xảy ra deadlock nếu không sắp xếp thứ tự khóa
                try { Thread.sleep(50); } catch (InterruptedException e) {}

                synchronized (secondLock) {
                    Ticket tA = roomA.getUnsoldTicket();
                    Ticket tB = roomB.getUnsoldTicket();

                    if (tA != null && tB != null) {
                        tA.isSold = true;
                        tB.isSold = true;
                        System.out.println(counterName + " bán combo thành công: " + tA.ticketId + " & " + tB.ticketId);
                        return true;
                    } else {
                        System.out.println(counterName + ": Hết vé một trong hai phòng, bán combo thất bại.");
                        return false;
                    }
                }
            }
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                if (!sellCombo()) break;
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== CHẠY CHƯƠNG TRÌNH VỚI CƠ CHẾ CHỐNG DEADLOCK ===");
        TicketPool poolA = new TicketPool("A", 10);
        TicketPool poolB = new TicketPool("B", 10);

        // Chú ý: Dù truyền ngược thứ tự tham số, thuật toán Lock Ordering vẫn sẽ tự động sắp xếp lại khóa A trước B
        BookingCounter counter1 = new BookingCounter("Quầy 1", poolA, poolB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", poolB, poolA);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();
    }
}