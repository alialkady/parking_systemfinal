import java.net.SocketTimeoutException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.Duration;
public class database_handle {
    // JDBC URL, username, and password of SQL Server
    private static final String JDBC_URL = "jdbc:sqlserver://DESKTOP-FCEDQJG:1433;databaseName=parking_system;encrypt=false;trustServerCertificate=true";
    private static final String USER = "alialkady";
    private static final String PASSWORD = "Aa22540444";

    //assigne slot by GAzar
    public static void assignSlot(String plateNumber) {
        int availableSlot =0;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {

            String query = "SELECT spot FROM spots WHERE spot_free = 'free'";
            try (PreparedStatement statement = connection.prepareStatement(query)) {


                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    availableSlot = resultSet.getInt("spot");
                }
                System.out.print(availableSlot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



    // insert methods
    public static void insertOperatorData(String name,String pass,int shift) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "INSERT INTO operator (username, password,shift_time) VALUES (?, ?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,pass);
                preparedStatement.setInt(3,shift);
                preparedStatement.executeUpdate();

                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Data couldn't be inserted.");
        }
    }

    public static void insertCustomerData(String entry_id,String plate_number) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "INSERT INTO customers (entry_id, plate_number,transaction_date) VALUES (?, ?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1,entry_id);
                preparedStatement.setString(2,plate_number);
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.executeUpdate();

                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Data couldn't be inserted.");
        }
    }
    public static void insertSpot(int spot, String spot_free) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "INSERT INTO spots (spot, spot_free) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1,spot);
                preparedStatement.setString(2,spot_free);
                preparedStatement.executeUpdate();

                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Data can't be inserted");
        }
    }
    public static void insertPayment(int shiftPayment,double payment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "INSERT INTO payment (shift_order,shifts_payment) VALUES (?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1,shiftPayment);
                preparedStatement.setDouble(2,payment);
                preparedStatement.executeUpdate();

                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Data couldn't be inserted.");
        }
    }
//retrieve method
    public static void retrieveData(String table) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String selectQuery = "SELECT * FROM "+table;

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(selectQuery);

                while (resultSet.next()) {
                    if(table=="operator"){
                        String username = resultSet.getString("username");
                        String password= resultSet.getString("password");
                        int shift = resultSet.getInt("shift_time");
                        System.out.println("username: "+ username+'\n'+"password: "+password+'\n'+"shift: "+shift);
                    }
                    else if(table=="customers"){
                        int entry_id = resultSet.getInt("entry_id");
                        int plate_number = resultSet.getInt("plate_number");
                        //here's write transaction date but i have problem with the datatype
                        System.out.println("entry id: "+entry_id+'\n'+"plate number: "+plate_number+'\n'); // you have to print also the transaction date
                    }
                    else if(table=="spots"){
                        int spots =resultSet.getInt("spot");
                        String spot_free =resultSet.getString("spot_free");
                        System.out.println("spot number: "+spots+" is "+ spot_free);
                    }
                    else if(table =="payment"){
                        int shift = resultSet.getInt("shift_order");
                        double shift_payment = resultSet.getDouble("shift_payment");
                        System.out.println("shift "+shift+" shift_payment is "+shift_payment);
                    }


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateCustomerId(int entry_id,int new_id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String updateQuery = "UPDATE customers SET entry_id = ? WHERE entry_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, new_id);
                preparedStatement.setInt(2, entry_id);
                preparedStatement.executeUpdate();

                System.out.println("Data updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Data couldn't update successfully.");
        }
    }
    public static void updateOperatorUser(String username,String newUser) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String updateQuery = "UPDATE operator SET username = ? WHERE username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newUser);
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();

                System.out.println("Data updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Data couldn't be updated.");
        }
    }
    public static void updateOperatorPass(String password,String newPass) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String updateQuery = "UPDATE operator SET password = ? WHERE password = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newPass);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();

                System.out.println("Data updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Data couldn't be updated.");
        }
    }

    public static void updatePayment(int shift,double payment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String updateQuery = "UPDATE payment SET shifts_payment = ? WHERE shift_order = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setDouble(1, payment);
                preparedStatement.setInt(2, shift);
                preparedStatement.executeUpdate();

                System.out.println("Data updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Data couldn't be updated.");
        }
    }

    public static void deleteCustomerData(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String deleteQuery = "DELETE FROM customers WHERE entry_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                System.out.println("Data deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteOperatorData(String username) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String deleteQuery = "DELETE FROM operator WHERE username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.executeUpdate();

                System.out.println("Data deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Data couldn't be deleted.");
        }
    }
    
}

