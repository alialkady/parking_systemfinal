import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
abstract class operatorMethods{
    public abstract String generateEntryID(String plateNumber);
    public abstract int assignedSlot(String carPlateNumber);
    public abstract int freeSpot(String id);
    public abstract double calculateParkingDurationHours(String id);
    public abstract double calculateParkingFee(String id);
    public abstract String entryTicket(String plateNumber);


        }

/**
 * Operator
 */
public class Operator extends operatorMethods {

    private String entryID;
    private LocalDateTime entryDateTime;

    private  String carPlateNumber;
    private double parkingFeePerHour = 2.5;


    public String generateEntryID(String plateNumber) {

            entryID = String.valueOf((int) (Math.random() * 100001));
            this.carPlateNumber = plateNumber;


            return database_handle.insertCustomerData(entryID, carPlateNumber);

    }

    public int assignedSlot(String carPlateNumber){

        if(database_handle.cancelSlot(entryID)==1){
            return 0;
        }
        this.carPlateNumber = carPlateNumber;
        if(database_handle.fullSpots()==1){
            return 0;
        }
      int slot =  database_handle.assignSlot(entryID);
      database_handle.assignSlotToCustomer(slot,entryID);

      // file.writeFile("slot",slot);
        return slot;
    }


    public  int freeSpot(String id){
        database_handle.freeSpot(id);
        return 1;
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
        return duration.getSeconds();
    }

    public double calculateParkingFee(String id) {
        this.entryID=id;
        double hours = calculateParkingDurationHours(entryID);
        double fees = hours* parkingFeePerHour;
        database_handle.setFees(entryID,fees);
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
         /*
         int counter = 0;
         if(database_handle.fullSpots()==1){
             counter++;
             if(counter>1){
                 return "no freeSpots";
             }
         }

          */
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

public static String displaySpots(){
       return database_handle.retrieveData("spots");
        /*
        database_handle database= new database_handle();
        void availableSlot = String.valueOf(database.retrieveData("spots"));
        System.out.println("Availble Spots: " +availableSlot);

         */
    }
    public static List<Integer> freeSpots(){
        return database_handle.getFreeSpots();
        /*
        database_handle database= new database_handle();
        void availableSlot = String.valueOf(database.retrieveData("spots"));
        System.out.println("Availble Spots: " +availableSlot);

         */
    }




}
