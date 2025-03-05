import java.util.*;

class TicketPool {
    private final List<String> tickets;

    public TicketPool(int initialCapacity) {
        this.tickets = Collections.synchronizedList(new ArrayList<>(initialCapacity));
    }

    public synchronized void addTickets(String ticket) {
        tickets.add(ticket);
        System.out.println("Ticket added: " + ticket);
    }

    public synchronized String removeTicket() {
        if (tickets.isEmpty()) {
            return null;
        }
        return tickets.remove(0); // Changed from removeFirst() to remove(0) for ArrayList compatibility
    }
}
