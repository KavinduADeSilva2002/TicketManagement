import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Configuration config;

        // Load or create configuration
        System.out.println("Do you want to load an existing configuration? (yes/no)");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("yes")) {
            System.out.println("Enter configuration file name:");
            String filename = scanner.nextLine();
            config = Configuration.loadFromFile(filename);
            if (config == null) {
                System.out.println("Loading failed. Creating a new configuration.");
                config = createNewConfiguration(scanner);
            }
        } else {
            config = createNewConfiguration(scanner);
        }

        // Initialize ticket pool with configuration
        TicketPool ticketPool = new TicketPool(config.getTotalTickets());

        // Create and start vendor threads
        Vendor vendor1 = new Vendor(ticketPool, config.getTicketReleaseRate(), "Vendor1");
        Vendor vendor2 = new Vendor(ticketPool, config.getTicketReleaseRate(), "Vendor2");
        Thread vendorThread1 = new Thread(vendor1);
        Thread vendorThread2 = new Thread(vendor2);

        // Create and start customer threads
        Customer customer1 = new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer1");
        Customer customer2 = new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer2");
        Thread customerThread1 = new Thread(customer1);
        Thread customerThread2 = new Thread(customer2);

        vendorThread1.start();
        vendorThread2.start();
        customerThread1.start();
        customerThread2.start();
    }

    private static Configuration createNewConfiguration(Scanner scanner) {
        int maximumTicketCapacity = 0;
        int totalTickets = 0;
        int ticketReleaseRate = 0;
        int customerRetrievalRate = 0;

        // Validate maximum ticket capacity
        while (true) {
            System.out.println("Enter maximum ticket capacity (positive integer):");
            if (scanner.hasNextInt()) {
                maximumTicketCapacity = scanner.nextInt();
                if (maximumTicketCapacity > 0) break;
            }
            System.out.println("Invalid input. Please enter a positive integer.");
            scanner.nextLine();
        }

        // Validate total tickets
        while (true) {
            System.out.println("Enter total tickets (positive integer, <= maximum capacity):");
            if (scanner.hasNextInt()) {
                totalTickets = scanner.nextInt();
                if (totalTickets > 0 && totalTickets <= maximumTicketCapacity) break;
            }
            System.out.println("Invalid input. Total tickets must be a positive integer and <= maximum capacity.");
            scanner.nextLine();
        }

        // Validate ticket release rate
        while (true) {
            System.out.println("Enter vendor ticket release rate (tickets/sec, positive integer):");
            if (scanner.hasNextInt()) {
                ticketReleaseRate = scanner.nextInt();
                if (ticketReleaseRate > 0) break;
            }
            System.out.println("Invalid input. Please enter a positive integer.");
            scanner.nextLine();
        }

        // Validate customer retrieval rate
        while (true) {
            System.out.println("Enter customer ticket retrieval rate (tickets/sec, positive integer):");
            if (scanner.hasNextInt()) {
                customerRetrievalRate = scanner.nextInt();
                if (customerRetrievalRate > 0) break;
            }
            System.out.println("Invalid input. Please enter a positive integer.");
            scanner.nextLine();
        }
        scanner.nextLine();

        Configuration config = new Configuration(maximumTicketCapacity, totalTickets, ticketReleaseRate, customerRetrievalRate);

        System.out.println("Do you want to save this configuration? (yes/no)");
        String saveChoice = scanner.nextLine().trim().toLowerCase();
        if (saveChoice.equals("yes")) {
            System.out.println("Enter filename to save configuration:");
            String filename = scanner.nextLine();
            config.saveToFile(filename, config);
        }
        return config;
    }
}
