import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;




public class Customer {

    // JDBC URL, username, and password of SQL Server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/customers?useSSL=false";
    private static final String USER = "ali";
    private static final String PASSWORD = "123";

    public static class CustomerData {
        int entryid;
        String plateNum;
        Timestamp transactionDue;
        int slotNum;
    
        public CustomerData() {
            this.entryid = 0;
            this.plateNum = "AA";
            this.slotNum = 0;
            this.transactionDue = Timestamp.valueOf(LocalDateTime.now());
        }
    }
    
    public static void main(String[] args) {
        Customer c1 = new Customer();
        CustomerData CXD = new CustomerData();
        
        System.out.println("Hello world!");
        
        retrieveData();
        // c1.GetTicketData(0, 0)
        c1.printEntryTicket(CXD);
        
        System.out.println(generateEntryid());
    }

    public static int generateEntryid()
    {
        Random rand = new Random();
        int randomNum = rand.nextInt(10000, 99999);
        return(randomNum);
    }

    private static void retrieveData() //testing function
    {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD))
        {
            String selectQuery = "SELECT * FROM ticket";

            try (Statement statement = connection.createStatement())
            {
                ResultSet resultSet = statement.executeQuery(selectQuery);

                while (resultSet.next())
                {
                    int id = resultSet.getInt("entryid");

                    String plateno = resultSet.getString("plateno");

                    Timestamp Transdue = resultSet.getTimestamp("transdue");

                    System.out.println("ID: " + id + ", plate: " + plateno + " , time: " + Transdue);
                }
            }
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
    }

    public static int insertTicket(String plate_number, int slotNum)
    {
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD))
        {
            String insertQuery = "INSERT INTO ticket (entry_id, plate_number, transaction_date, slot) VALUES (?, ?,?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery))
            {
                preparedStatement.setInt(1,generateEntryid());
                preparedStatement.setString(2,plate_number);
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.setInt(4,slotNum);
                preparedStatement.executeUpdate();
                return (0); //success insertion
            }
        }
        catch (SQLException e)
        {
            return (1); //error in insertion
        }

    }

    public CustomerData GetTicketData(int EntryID, int slotnumber)
    {
        CustomerData CXD = new CustomerData();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD))
        { 
            String printingQuery = "SELECT * FROM customer WHERE entry_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(printingQuery))
            {
                preparedStatement.setInt(1, EntryID);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if (resultSet.next())
                { // resultSet is empty, this throws an exception
                    int id = resultSet.getInt("entryid");
                    String plateno = resultSet.getString("plateno");
                    Timestamp Transdue = resultSet.getTimestamp("transdue");
                    
                    CXD.entryid = id;
                    CXD.plateNum = plateno;
                    CXD.transactionDue = Transdue;
                    CXD.slotNum = slotnumber;

                    return CXD;
                } 
                else 
                {
                    CXD.entryid = -1; //data not found matching this id
                    return (CXD);
                }
            }
            catch (SQLException e)
            {
                CXD.entryid = -2; //error in execution
                return (CXD);
            }
        }
        catch (SQLException e) 
        {
            CXD.entryid = -3;
            return (CXD); //sql error
        }
    }
    public void printEntryTicket(CustomerData CXD)
    {
    System.out.println("==== Entry Ticket ====");
    System.out.println("Welcome Dear Customer");
    System.out.println("Entry ID: " + CXD.entryid);
    System.out.println("Vehicle Number: " + CXD.plateNum);
    System.out.println("Entry Time: " + CXD.transactionDue);
    System.out.println("Slot Number: "+ CXD.slotNum);
    System.out.println("=====================");
    }
}
