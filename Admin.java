import java.util.*;
abstract class AdminMethods{
    public abstract void setUsername(String user);
    public abstract void setPassword(String password);
    public abstract String getUsername();
    public abstract String getPassword();
    public abstract String addSpots(int spotId);
    public abstract String viewTotalSpots();
    public abstract String addCustomer(String entry_id,String plate_number);
    public abstract String addOperator(String name, String pass,int shift);
    public abstract String updateUser(String id,String newID);
    public abstract String updateOperatorName(String oldName,String newName);
    public abstract String updateOperatorPass(String oldPass,String newPass);
    public abstract String updatePayment(int shift, double payment);
    public abstract String deleteCustomer(String entry_id);
    public abstract String deleteOperator(String name);
    public abstract String parkedCar();
    public abstract String carReports();


}
public class Admin extends AdminMethods {

    private String Username;
    private String Password;
    //Constructor for logging into the system
    public Admin(){
        // creating two strings to hold the entered login info
        String user ="";
        String pass ="";
        //Scanning the login info
        Scanner adminCheckScan = new Scanner(System.in);
        System.out.print("Username: ");
        user += adminCheckScan.nextLine();
        System.out.print("Password: ");
        pass += adminCheckScan.nextLine();
        //passing login info to the login check method
        file.adminCheck("Admin", user, pass);
    }

    // Constructor for add or signing up a new admin to the system
    public Admin(String user, String pass){
        Username = user;
        Password = pass;
        // Add new admin's info to the admins file
        file.appendFile("Admin", Username);
        file.appendFile("Admin", Password);
    }

    // Admin username setter
    public void setUsername(String user) {
        Username = user;
        Scanner userInputScanner = new Scanner(System.in);
        //Scan login info to edit them
        System.out.print("Enter the old username: ");
        String oldUsername = userInputScanner.nextLine();
        System.out.print("Enter the old password: ");
        String oldPassword = userInputScanner.nextLine();
        //passing login info to the edit file method
        file.editFile("Admin", oldUsername, oldPassword);
    }

    // Admin password setter
    public void setPassword(String password) {
        Password = password;
        Scanner userInputScanner = new Scanner(System.in);
        //Scan login info to edit them
        System.out.print("Enter the old username: ");
        String oldUsername = userInputScanner.nextLine();
        System.out.print("Enter the old password: ");
        String oldPassword = userInputScanner.nextLine();
        //passing login info to the edit file method
        file.editFile("Admin", oldUsername, oldPassword);
    }

    // Admin username getter
    public String getUsername() {
        return Username;
    }

    // Admin password getter
    public String getPassword() {
        return Password;
    }

    //Admin add spots to the parking area
    public String addSpots(int spotId){

        return database_handle.insertSpot(spotId, "free");
    }

    //Admin view total spots in the parking area
    public String viewTotalSpots(){
      return database_handle.retrieveData("spots");

    }

    // Admin add customer
    public String addCustomer(String entry_id,String plate_number){
        return database_handle.insertCustomerData(entry_id,plate_number);

    }
    //admin add operator
    public String addOperator(String name, String pass,int shift){
        return database_handle.insertOperatorData(name,pass,shift);

    }
    //admin update user_id
    public String updateUser(String  id,String newID){
       return database_handle.updateCustomerId(id,newID);

    }
    //admin update operator_name
    public String updateOperatorName(String oldName,String newName){
        return database_handle.updateOperatorUser(oldName,newName);

    }
    //admin update operator_password
    public String updateOperatorPass(String oldPass,String newPass){
       return database_handle.updateOperatorPass(oldPass,newPass);

    }
    //admin update shiftPayment
    public String updatePayment(int shift, double payment){
       return database_handle.updatePayment(shift,payment);

    }
    //admin delete customerData
    public String deleteCustomer(String entry_id){
        return database_handle.deleteCustomerData(entry_id);

    }
    //admin delete OperatorData
    public String deleteOperator(String name){
       return database_handle.deleteOperatorData(name);

    }
    public String parkedCar(){
        return database_handle.retrieveData("customers");
    }

    public String carReports(){
        return database_handle.retrieveData("payment");
    }

}
