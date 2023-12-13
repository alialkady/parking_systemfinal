import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileNotFoundException;  // Import this class to handle errors

import java.io.FileWriter;
import java.util.*;
public class file {
    public static boolean admincheck;
    //Create file
    public static void createFile(String name) {
        try {
            // define object
            File myObj = new File(name + ".txt");
            // if condition to create and check in the same time
            if (myObj.createNewFile()) {
                System.out.println("file created successfully");
            } else {
                System.out.println("file already exist");
            }
        } catch (IOException e) {
            System.out.println("error occurred");
        }

    }

    //read file
    public static void readFile(String name){
        try {
            // Create obj with the file name
            File myObj = new File(name + ".txt");


            Scanner myReader = new Scanner(myObj);

            // Loop through lines
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                // Print
                System.out.println(data);
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            // Let the calling code handle the exception
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static boolean adminCheck(/*String name, String user, String pass*/){
        // creating two strings to hold the entered login info
        String user ="";
        String pass ="";
        //Scanning the login info
        Scanner adminCheckScan = new Scanner(System.in);
        System.out.print("Username: ");
        user += adminCheckScan.nextLine();
        System.out.print("Password: ");
        pass += adminCheckScan.nextLine();
        try {
            int singleLoginCheck = 0;
            // Create obj with the file name
            File myObj = new File("Admin.txt");
            Scanner myReader = new Scanner(myObj);

            // Loop through lines
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals(user)){
                    singleLoginCheck++;
                    if (myReader.hasNextLine()) {
                        String nextLine = myReader.nextLine();

                        if (nextLine.equals(pass)){
                            singleLoginCheck++;
                            admincheck = true;
                            //return true;
                        }
                    }
                }

            }
            // message to the admin if any entered info is wrong
            if (singleLoginCheck == 0 || singleLoginCheck == 1)
                admincheck = false;
                //return false;

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
       // return check;
        //return adminCheck(String name, String user, String pass);
        return admincheck;
    }


    //write in the file
    public static void writeFile(String name){
        try{
            //name of the file you will write in
            FileWriter write =  new FileWriter(name+".txt");
            Scanner writer = new Scanner(System.in);
            System.out.print("Write: ");
            String text = writer.nextLine();
            write.write(text);
            write.close();


        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void appendFile(String name, String infoToAppend) {
        try {
            // Check if the data already exists in the file
            if (!dataExists(name, infoToAppend)) {
                // Open the file in append mode
                FileWriter writer = new FileWriter(name + ".txt", true);

                // Append the provided information to the file
                writer.write("\n" + infoToAppend);

                // Close the file
                writer.close();

                System.out.println("Information appended successfully.");
            } else {
                System.out.println("Data already exists in the file. No changes made.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //edit data in file
    public static void editFile(String name, String oldUsername, String oldPassword) {
        try {
            // Read the existing content of the file
            File file = new File(name + ".txt");
            Scanner scanner = new Scanner(file);

            // Create a temporary file to store the updated content
            File tempFile = new File(name + "_temp.txt");
            FileWriter tempWriter = new FileWriter(tempFile);

            boolean found = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Check if the current line contains the old username
                if (line.contains(oldUsername)) {

                    // Check if there is a next line (password)
                    if (scanner.hasNextLine()) {
                        String passwordLine = scanner.nextLine();

                        // Check if the next line contains the old password
                        if (passwordLine.contains(oldPassword)) {
                            // Replace the old username and password with the new scanned ones
                            Scanner newLoginInfo = new Scanner(System.in);
                            System.out.print("Enter new Username: ");
                            String newUsername = newLoginInfo.nextLine();
                            System.out.print("Enter new Password: ");
                            String newPassword = newLoginInfo.nextLine();
                            tempWriter.write(newUsername + "\n");
                            tempWriter.write(newPassword + "\n");

                            found = true;
                        } else {
                            // If the password doesn't match, write the original lines
                            tempWriter.write(line + "\n");
                            tempWriter.write(passwordLine + "\n");
                        }
                    } else {
                        // If there is no next line, write the original line
                        tempWriter.write(line + "\n");
                    }
                } else {
                    // If the current line doesn't contain the old username, write the original line
                    tempWriter.write(line + "\n");
                }
            }

            // Close the scanners and writers
            scanner.close();
            tempWriter.close();

            // Replace the original file with the temporary file
            if (found) {
                file.delete();
                tempFile.renameTo(file);
                System.out.println("File updated successfully.");
            } else {
                tempFile.delete();
                System.out.println("Old username and password not found. No changes made.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void searchInFile(String name, String searchString) {
        try {
            File file = new File(name + ".txt");
            Scanner scanner = new Scanner(file);

            boolean found = false;
            int lineNumber = 0;

            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();

                // Check if the line contains the search string
                if (line.contains(searchString)) {
                    found = true;
                    System.out.println("Found at line " + lineNumber + ": " + line);
                }
            }

            scanner.close();

            if (!found) {
                System.out.println("Search string not found in the file.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void deleteSpecificLine(String name, String lineToDelete) {
        try {
            // Create obj with the file name
            File myObj = new File(name + ".txt");

            // Create a temporary file to store the updated content
            File tempFile = new File(name + "_temp.txt");
            FileWriter tempWriter = new FileWriter(tempFile);

            boolean found = false;

            // Read the existing content of the file
            try (Scanner myReader = new Scanner(myObj)) {
                // Iterate over the lines and update the list
                while (myReader.hasNextLine()) {
                    String currentLine = myReader.nextLine();

                    // Check if the current line contains the content to be deleted
                    if (currentLine.contains(lineToDelete)) {
                        found = true;

                        // Skip the current line and the next line
                        if (myReader.hasNextLine()) {
                            myReader.nextLine(); // Move to the next line
                        }
                    } else {
                        // Write the current line to the temporary file
                        tempWriter.write(currentLine + System.getProperty("line.separator"));
                    }
                }
            }

            tempWriter.close();

            // Replace the original file with the temporary file
            tempFile.renameTo(new File(name + ".txt"));

            if (found) {
                System.out.println("Specific line and the next line deleted successfully.");
            } else {
                System.out.println("Specified content not found in the file. No changes made.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static boolean dataExists(String name, String infoToAppend) {
        try {
            File file = new File(name + ".txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Check if the line contains the provided information
                if (line.equals(infoToAppend)) {
                    scanner.close();
                    return true;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while checking data existence.");
            e.printStackTrace();
        }

        return false;
    }
}