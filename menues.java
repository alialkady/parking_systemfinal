import java.util.Scanner;
public class menues{

    public static void homeMenue(){
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("===== Parking System Menu =====");
            System.out.println("1. Customer");
            System.out.println("2. Operator");
            System.out.println("3. Admin");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    customerMenu();
                    break;
                case 2:
                        OperatorMenu();
                    break;
                case 3:
                    adminMenu();
                    break;
                case 4:
                    System.out.println("Exiting the Parking System. Goodbye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
    }
    public static void customerMenu() {
        Scanner scanner = new Scanner(System.in);
        //Customer customer = new Customer("");

        System.out.println("Customer Menu");
        System.out.println("1. Print Entry Ticket");
        System.out.println("2. Print Exit Ticket");
        System.out.println("3. Back");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        scanner.nextLine(); // Consume newline
        Operator operator=new Operator();
        switch (choice) {
            case 1:
                System.out.print("Enter vehicle plate number: ");
                String plateNumber = scanner.nextLine();
                System.out.println(operator.generateEntryID(plateNumber));
               System.out.println(operator.assignedSlot(plateNumber));
                System.out.println(operator.entryTicket(plateNumber));

                //operator.recordEntryTime();
                //operator.assignedSlot(plateNumber);
               // System.out.println(database_handle.getCustomerData(plateNumber));

                //customer.setVehicleNumber(plateNumber);
                //customer.park(1);
                break;
            case 2:
                System.out.print("Enter entry ID: ");
                String providedEntryID = scanner.nextLine();
                operator.calculateParkingDurationHours(providedEntryID);
                operator.calculateParkingFee(providedEntryID);
                System.out.println(operator.printExitTicket(providedEntryID));
                //database_handle.spotFree(operator.assignedSlot(plateNumber));
                //customer.printExitTicket(providedEntryID);
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid option. Please choose again.");
                break;
        }
    }
    public static void OperatorMenu() {
        Operator entryOperator = new Operator();
        Scanner scanner = new Scanner(System.in);
        boolean backToMain = false;

        // Simulated login credentials
        final String OPERATOR_USERNAME = "operator";
        final String OPERATOR_PASSWORD = "password";

        while (!backToMain) {
            System.out.print("Enter Operator Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Operator Password: ");
            String password = scanner.nextLine();

            if (username.equals(OPERATOR_USERNAME) && password.equals(OPERATOR_PASSWORD)) {
                System.out.println("Login successful!");
                operatorActions(entryOperator);
                backToMain = true;
            } else {
                System.out.println("Invalid credentials. Please try again or type 'back' to return.");
                if (username.equalsIgnoreCase("back")) {
                    backToMain = true;
                }
            }
        }
    }

    public static void operatorActions(Operator entryOperator) {
        Scanner scanner = new Scanner(System.in);
        boolean backToLogin = false;

        while (!backToLogin) {
            System.out.println("===== Entry Station Operator Menu =====");
            System.out.println("1. Display Free Slots");
            System.out.println("2. Display Plate Numbers in Every Slot");
            System.out.println("3. Back to Login");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println(Operator.displayFreeSpots());
                    break;
                case 2:
                    System.out.println(Operator.displayFreeSpots());
                    break;
                case 3:
                    backToLogin = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
    }

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean backToMain = false;

        System.out.println("Admin Menu");
        System.out.println("1. Log in");
        System.out.println("2. Sign UP");
        System.out.println("3. Back");
        System.out.print("Choose an option: ");
        int choice1 = scanner.nextInt();

        scanner.nextLine(); // Consume newline
        //Admin adminLogin;
        Admin adminSignup;
        switch (choice1) {
            case 1:
                //adminLogin = new Admin();
                if(file.adminCheck()){
                    adminActions();
                }
                else System.out.println("Access Denied");
                break;
            case 2:
                Scanner adminScanner = new Scanner(System.in);
                System.out.print("Enter Username: ");
                String username = adminScanner.nextLine();
                System.out.print("Enter Password: ");
                String pass = adminScanner.nextLine();
                adminSignup = new Admin(username, pass);
                adminMenu();
                break;
            case 3:
                backToMain = true;
                break;
            default:
                System.out.println("Invalid option. Please choose again.");
                break;
        }


    }

    public static void adminActions(){
        Scanner scanner = new Scanner(System.in);
        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("===== Admin Menu =====");
            System.out.println("1. Add Slots");
            System.out.println("2. View Total Slots");
            System.out.println("3. Reset username and password");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice2 = scanner.nextInt();
            Admin admin=new Admin();
            switch (choice2) {
                case 1:
                    System.out.print("Enter the number of new slots to add: ");
                    int newSlots = scanner.nextInt();
                    admin.addSpots(newSlots);
                    break;
                case 2:
                    admin.viewTotalSpots();
                    break;
                case 3:
                    /*Scanner adminScanner = new Scanner(System.in);
                    System.out.print("Enter new Username: ");
                    String username = adminScanner.nextLine();
                    System.out.print("Enter new Password: ");
                    String pass = adminScanner.nextLine();*/
                    admin.setUsernameAndPass();
                    adminMenu();
                case 4:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
    }
}
