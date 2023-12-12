import java.time.LocalDateTime;
import java.util.Date;
import java.time.Duration;


/**
 * Operator
 */
public class Operator {

    private String entryID;
    private LocalDateTime entryDateTime;
    private String availableSlot;
    private String carPlateNumber;
    private double parkingFeePerHour = 2.5; // Assuming a fixed fee per hour


    public void generateEntryID(String plateNumber) {
        // ID generation logic as before
        //entryID = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (int) (Math.random() * 1000);
        //entryID = (int)Math.random() * 100000;
        this.carPlateNumber=plateNumber;
        entryID = carPlateNumber;
        //entryDateTime = LocalDateTime.now();
        database_handle.insertCustomerData(entryID,carPlateNumber);

    }

    public void recordEntryTime() {
        entryDateTime = LocalDateTime.now();
    }

    public String assignedSlot(String carPlateNumber){
        this.carPlateNumber = carPlateNumber;
      int slot =  database_handle.assignSlot(carPlateNumber);
      return database_handle.assignSlotToCustomer(slot,entryID);

    }

    public void printEntryTicket(String platenumber) {
        this.carPlateNumber= platenumber;
        System.out.println("==== Entry Ticket ====");
        System.out.println("Entry ID: " + entryID);
        System.out.println("Vehicle Number: " + carPlateNumber);
        System.out.println("Entry Time: " + entryDateTime);
        System.out.println("Slot Number: "+ availableSlot);
        System.out.println("=====================");
    }

    public double calculateParkingDurationHours() {
        LocalDateTime exitTime = LocalDateTime.now();
        Duration duration = Duration.between(entryDateTime, exitTime);
        return duration.getSeconds() / 3600.0;
    }

    public double calculateParkingFee() {
        double hours = calculateParkingDurationHours();
        return hours * parkingFeePerHour;
    }

    public void printExitTicket(String providedEntryID) {
        /*
        if (providedEntryID==entryID) {
            calculateParkingDurationHours();
            double parkingFee = calculateParkingFee();

            // Simulate printing an exit ticket with duration and fee details
            System.out.println("==== Exit Ticket ====");
            System.out.println("Entry ID: " + entryID);
            System.out.println("Vehicle Number: " + carPlateNumber);
            System.out.println("Parked Time: " + calculateParkingDurationHours() + " hours");
            System.out.println("Parking Fee: $" + parkingFee);
            System.out.println("=====================");
        } else {
            System.out.println("Invalid Entry ID. Please provide the correct entry ID.");
        }*/
       // database_handle.printEntryTicket(providedEntryID);
    }


/*     public String entryTicket(String plateNumber){
        this.carPlateNumber = plateNumber;
        return "Entry ID: "+entryID+", Entry DateTime: "+entryDateTime+"\nAvailable Slot: "+availableSlot+"\nPLate number: "+carPlateNumber;
    } */

    public static void displayFreeSpots(){
        database_handle.retrieveData("spots");
        /*
        database_handle database= new database_handle();
        void availableSlot = String.valueOf(database.retrieveData("spots"));
        System.out.println("Availble Spots: " +availableSlot);

         */
    }




}