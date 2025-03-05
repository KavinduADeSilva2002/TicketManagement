class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private final String customerName;

    public Customer(TicketPool ticketPool, int retrievalRate, String customerName) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.customerName = customerName;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                String ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    System.out.println(customerName + " purchased " + ticket);
                } else {
                    System.out.println(customerName + " found no tickets available.");
                }
                Thread.sleep(1000 / retrievalRate);
            }
        } catch (InterruptedException e) {
            System.err.println(customerName + " interrupted: " + e.getMessage());
        }
    }
}
