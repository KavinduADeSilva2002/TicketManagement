class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final String vendorName;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, String vendorName) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorName = vendorName;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                String ticket = vendorName + "-Ticket-" + i;
                ticketPool.addTickets(ticket);
                Thread.sleep(1000 / ticketReleaseRate);
            }
        } catch (InterruptedException e) {
            System.err.println(vendorName + " interrupted: " + e.getMessage());
        }
    }
}
