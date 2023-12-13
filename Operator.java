import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.Duration;
import java.util.UUID;



/**
 * Operator
 */
public class Operator {

    private String entryID;
    private LocalDateTime entryDateTime;
    private String availableSlot;
    private String carPlateNumber;
    private double parkingFeePerHour = 2.5; // Assuming a fixed fee per hour


    public String generateEntryID(String plateNumber) {
       UUID uuid = UUID.randomUUID();
        entryID = String.valueOf((int) (Math.random() * 100001));
        this.carPlateNumber = plateNumber;

        return database_handle.insertCustomerData(entryID, carPlateNumber);
    }

    public int assignedSlot(String carPlateNumber){
        this.carPlateNumber = carPlateNumber;
      int slot =  database_handle.assignSlot(entryID);
       database_handle.assignSlotToCustomer(slot,entryID);
        return slot;

    }
    public String freeSpot(){
        return database_handle.spotFree(assignedSlot(this.carPlateNumber));
    }

    /*public String printEntryTicket(String platenumber) {
        this.carPlateNumber= platenumber;
        System.out.println("==== Entry Ticket ====");
        System.out.println("Entry ID: " + entryID);
        System.out.println("Vehicle Number: " + carPlateNumber);
        System.out.println("Entry Time: " + entryDateTime);
        System.out.println("Slot Number: "+ availableSlot);
        System.out.println("=====================");
    }*/

    public double calculateParkingDurationHours(String id) {
        this.entryID=id;
        entryDateTime = database_handle.getDate(id).toLocalDateTime();
        LocalDateTime exitTime = LocalDateTime.now();
        Duration duration = Duration.between(entryDateTime, exitTime);
        database_handle.setExitDate(entryID, Timestamp.valueOf(exitTime));
        return duration.getSeconds() / 3600.0;
    }

    public double calculateParkingFee(String id) {
        this.entryID=id;
        double hours = calculateParkingDurationHours(entryID);
        double fees = hours* parkingFeePerHour;
        System.out.println(database_handle.setFees(entryID,fees));
        return fees;

    }


/*
    public void printExitTicket(String providedEntryID) {
        
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
        }
       // database_handle.printEntryTicket(providedEntryID);
    }
*/

     public String entryTicket(String plateNumber){
        this.carPlateNumber = plateNumber;
        return database_handle.setEntryTicket(entryID);
    } 
   public static String printExitTicket(String providedEntryID){

        return database_handle.getCustomerData(providedEntryID);
        /*if (providedEntryID==entryID) {
            calculateParkingDurationHours(providedEntryID);
            double parkingFee = (double)calculateParkingFee(providedEntryID);
            String output = "{\n\"Entry ID\": \""+entryID+"\",\n\"Car Plate Number\": \""+carPlateNumber+"\",\n\"Duration Hours\": \"" + calculateParkingDurationHours(entryID)+ "\",\n\"Parking Fee\": \"$" + parkingFee + "\"}";
            //database_handle.updateStatus(entryID,"Exited");
            //database_handle.setPaymentStatus(entryID,"Unpaid");
            return output;
            } else {
                return "Invalid Entry ID.";
                }
                //return database_handle.printExitTicket(providedEntryID);*/
                }

public static String displayFreeSpots(){
       return database_handle.retrieveData("spots");
        /*
        database_handle database= new database_handle();
        void availableSlot = String.valueOf(database.retrieveData("spots"));
        System.out.println("Availble Spots: " +availableSlot);

         */
    }




}
