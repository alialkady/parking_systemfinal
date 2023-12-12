import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
public class Customer {

    // JDBC URL, username, and password of SQL Server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CUSTOMER?useSSL=false";
    private static final String USER = "ali";
    private static final String PASSWORD = "123";

    public static void main(String[] args) 
    {
        Customer c1 = new Customer();
        
        System.out.println("Hello world!");
        
        retrieveData();
        
        c1.printEntryTicket(59606, 3454);
        
        System.out.println(generateEntryid());
    }

    public static int generateEntryid()
    {
        Random rand = new Random();
        int randomNum = rand.nextInt(10000, 99999);
        //database_handle.insertCustomerData(randomNum,"aht");
        return(randomNum);
    }

    private static void retrieveData()
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

    public static void insertTicket(String plate_number)
    {
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD))
        {
            String insertQuery = "INSERT INTO ticket (ENTRYID, PLATENO, TRANSDUE) VALUES (?, ?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery))
            {
                preparedStatement.setInt(1,generateEntryid());
                preparedStatement.setString(2,plate_number);
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.executeUpdate();

                System.out.println("Ticket inserted successfully.");
            }
        }
        catch (SQLException e)
        {
            System.err.println(e);
            System.out.println("Ticket couldn't be inserted.");
        }

    }

    public void printEntryTicket(int EntryID, int slotnumber)
    {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD))
        {
            String printingQuery = "SELECT * FROM ticket WHERE ENTRYID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(printingQuery))
            {
                preparedStatement.setInt(1, EntryID);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if (resultSet.next())
                { // resultSet is empty, this throws an exception
                    int id = resultSet.getInt("entryid");
                    String plateno = resultSet.getString("plateno");

                    Timestamp Transdue = resultSet.getTimestamp("transdue");
                    System.out.println("==== Entry Ticket ====");
                    System.err.println("Welcome Dear Customer");
                    System.out.println("Entry ID: " + id);
                    System.out.println("Vehicle Number: " + plateno);
                    System.out.println("Entry Time: " + Transdue);
                    System.out.println("Slot Number: "+ slotnumber);
                    System.out.println("=====================");
                } 
                else 
                {
                        System.out.println("No result found");
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);
            }
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}
