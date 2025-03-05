import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Configuration implements Serializable {
    private int maximumTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public Configuration(int maximumTicketCapacity, int totalTickets, int ticketReleaseRate, int customerRetrievalRate) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaximumTicketCapacity() { return maximumTicketCapacity; }
    public int getTotalTickets() { return totalTickets; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }

    @Override
    public String toString() {
        return "Configuration{" +
                "maximumTicketCapacity=" + maximumTicketCapacity +
                ", totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                '}';
    }

    public void saveToFile(String filename, Configuration config) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(this.toString());
            System.out.println("Configuration saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    public static Configuration loadFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        reader.close();

        if (line == null || !line.startsWith("Configuration{") || !line.endsWith("}")) {
            throw new IllegalArgumentException("Invalid configuration file format.");
        }

        Pattern pattern = Pattern.compile("(\\w+)=(\\d+)");
        Matcher matcher = pattern.matcher(line);

        int maximumTicketCapacity = 0;
        int totalTickets = 0;
        int ticketReleaseRate = 0;
        int customerRetrievalRate = 0;

        while (matcher.find()) {
            String key = matcher.group(1);
            int value = Integer.parseInt(matcher.group(2));

            switch (key) {
                case "maximumTicketCapacity" -> maximumTicketCapacity = value;
                case "totalTickets" -> totalTickets = value;
                case "ticketReleaseRate" -> ticketReleaseRate = value;
                case "customerRetrievalRate" -> customerRetrievalRate = value;
                default -> throw new IllegalArgumentException("Unexpected key in configuration: " + key);
            }
        }

        return new Configuration(maximumTicketCapacity, totalTickets, ticketReleaseRate, customerRetrievalRate);
    }
}
