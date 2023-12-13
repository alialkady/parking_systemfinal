import com.sun.source.tree.BreakTree;

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
    public static int assignSlot(String plateNumber) {
        int availableSlot = 0;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {

            String query = "SELECT spot FROM spots WHERE spot_free = 'free'";
            try (PreparedStatement statement = connection.prepareStatement(query)) {


                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    availableSlot = resultSet.getInt("spot");
                }
                return availableSlot;
            }
        } catch (SQLException e) {
            return 0;
        }


    }

    //assign slot to customer
    public static String assignSlotToCustomer(int slot, String id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "UPDATE customers set slot = ? where entry_id =?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, slot);
                preparedStatement.setString(2, id);

                preparedStatement.executeUpdate();

                return "slot is assigned";
            }
        } catch (SQLException e) {
            return "slot didn't assign";
        }
    }

    // insert methods
    public static String insertOperatorData(String name, String pass, int shift) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "INSERT INTO operator (username, password,shift_time) VALUES (?, ?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, pass);
                preparedStatement.setInt(3, shift);
                preparedStatement.executeUpdate();

                return "Data inserted successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't be inserted";
        }
    }

    public static String insertCustomerData(String entry_id, String plate_number) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "INSERT INTO customers (entry_id, plate_number,transaction_date) VALUES (?, ?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, entry_id);
                preparedStatement.setString(2, plate_number);
                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                preparedStatement.executeUpdate();

                return "Data inserted successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't be inserted.";
        }
    }

    public static String insertSpot(int spot, String spot_free) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "INSERT INTO spots (spot, spot_free) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, spot);
                preparedStatement.setString(2, spot_free);
                preparedStatement.executeUpdate();

                return "Data inserted successfully.";
            }
        } catch (SQLException e) {
            return "Data can't be inserted";
        }
    }

    public static String insertPayment(int shiftPayment, double payment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "INSERT INTO payment (shift_order,shifts_payment) VALUES (?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, shiftPayment);
                preparedStatement.setDouble(2, payment);
                preparedStatement.executeUpdate();

                return "Data inserted successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't be inserted.";
        }
    }

    //retrieve method
    public static String retrieveData(String table) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String selectQuery = "SELECT * FROM " + table;

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(selectQuery);

                while (resultSet.next()) {
                    if (table == "operator") {
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        int shift = resultSet.getInt("shift_time");
                        return "username: " + username + '\n' + "password: " + password + '\n' + "shift: " + shift;
                    } else if (table == "customers") {
                        int entry_id = resultSet.getInt("entry_id");
                        int plate_number = resultSet.getInt("plate_number");
                        Timestamp timestamp = resultSet.getTimestamp("tranaction_date");
                        int slot = resultSet.getInt("slot");
                        double payment = resultSet.getDouble("payment");
                        //.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

                        //here's write transaction date but i have problem with the datatype
                        return "entry id: " + entry_id + '\n' + "plate number: " + plate_number + '\n' + "tracation dateTime: '+timestamp" + "\n" + "slot:" + slot + "\n" + "payment: " + payment; // you have to print also the transaction date
                    } else if (table == "spots") {
                        int spots = resultSet.getInt("spot");
                        String spot_free = resultSet.getString("spot_free");
                        return "spot number: " + spots + " is " + spot_free;
                    } else if (table == "payment") {
                        int shift = resultSet.getInt("shift_order");
                        double shift_payment = resultSet.getDouble("shift_payment");
                        return "shift " + shift + " shift_payment is " + shift_payment;
                    }


                }
            }
        } catch (SQLException e) {
            return "data couldn't be inserted";
        }
        return table;
    }

    public static String updateCustomerId(int entry_id, int new_id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String updateQuery = "UPDATE customers SET entry_id = ? WHERE entry_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, new_id);
                preparedStatement.setInt(2, entry_id);
                preparedStatement.executeUpdate();

                return "Data updated successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't update successfully.";
        }
    }

    public static String updateOperatorUser(String username, String newUser) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String updateQuery = "UPDATE operator SET username = ? WHERE username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newUser);
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();

                return "Data updated successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't be updated.";
        }
    }

    public static String updateOperatorPass(String password, String newPass) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String updateQuery = "UPDATE operator SET password = ? WHERE password = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newPass);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();

                return "Data updated successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't be updated.";
        }
    }

    public static String updatePayment(int shift, double payment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String updateQuery = "UPDATE payment SET shifts_payment = ? WHERE shift_order = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setDouble(1, payment);
                preparedStatement.setInt(2, shift);
                preparedStatement.executeUpdate();

                return "Data updated successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't be updated.";
        }
    }

    public static String deleteCustomerData(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String deleteQuery = "DELETE FROM customers WHERE entry_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                return "Data deleted successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't delete.";
        }
    }

    public static String deleteOperatorData(String username) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String deleteQuery = "DELETE FROM operator WHERE username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.executeUpdate();

                return "Data deleted successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't be deleted.";
        }
    }
    public static Timestamp getDate(String id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String selectQuery = "SELECT transaction_date FROM customers WHERE entry_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, id); // Set the id parameter

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Timestamp date = resultSet.getTimestamp("transaction_date");
                    return date;
                } else {
                    // Handle the case where no result is found
                    return null; // or throw an exception, depending on your requirements
                }
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            return Timestamp.valueOf(LocalDateTime.now());
        }
    }
    public static String setFees(String id,double fees){
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "update customers set customer_payment = ? where entry_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setDouble(1, fees);
                preparedStatement.setString(2, id);
                preparedStatement.executeUpdate();

                return "Data inserted successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't be inserted.";
        }
    }
    public static String setExitDate(String id,Timestamp date){
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String insertQuery = "update customers set exit_transaction = ? where entry_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setTimestamp(1, date);
                preparedStatement.setString(2, id);
                preparedStatement.executeUpdate();

                return "Data inserted successfully.";
            }
        } catch (SQLException e) {
            return "Data couldn't be inserted.";
        }
    }
}








