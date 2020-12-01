import java.io.*;
import java.util.*;
import java.sql.*;
import com.opencsv.*;

public class Main {
    private static Scanner in;
    private static Connection conn;
    
    private static void task11() throws Exception { // Create tables
        // drivers
        PreparedStatement dropDrivers = conn.prepareStatement("DROP TABLE IF EXISTS drivers");
        dropDrivers.execute();
        dropDrivers.close();
        PreparedStatement createDrivers = conn.prepareStatement("CREATE TABLE drivers (" +
            "id int NOT NULL," +
            "name varchar(255) NOT NULL," +
            "vehicle_id varchar(255) NOT NULL," +
            "driving_years int NOT NULL," +
            "PRIMARY KEY (id)," +
            "CONSTRAINT c_id CHECK (id > 0)," +
            "CONSTRAINT c_name CHECK (name <> '' AND LEN(name) <= 30)," +
            "CONSTRAINT c_vehicle_id CHECK (LEN(driving_years) = 6)," +
            "CONSTRAINT c_driving_years CHECK (driving_years > 0)" +
        ")");
        createDrivers.execute();
        createDrivers.close();
        
        // vehicles
        // passengers
        PreparedStatement dropPassengers = conn.prepareStatement("DROP TABLE IF EXISTS passengers");
        dropPassengers.execute();
        dropPassengers.close();
        PreparedStatement createPassengers = conn.prepareStatement("CREATE TABLE passengers (" +
            "id varchar(255) NOT NULL," +
            "PRIMARY KEY (id)," +
            "CONSTRAINT c_id CHECK (id > 0)" +
        ")");
        createPassengers.execute();
        createPassengers.close();
        // requests
        // taxi_stop
        PreparedStatement dropTaxistop = conn.prepareStatement("DROP TABLE IF EXISTS taxi_stop");
        dropTaxistop.execute();
        dropTaxistop.close();
        PreparedStatement createTaxistop = conn.prepareStatement("CREATE TABLE taxi_stop (" +
            "name varchar(255) NOT NULL," +
            "location_x int NOT NULL," +
            "location_y int NOT NULL," +
            "PRIMARY KEY (name)," +
            "CONSTRAINT c_name CHECK (name <> '' AND LEN(name) <= 20)" +
        ")");
        createTaxistop.execute();
        createTaxistop.close();
        
        // trips
        PreparedStatement dropTrips = conn.prepareStatement("DROP TABLE IF EXISTS trips");
        dropTrips.execute();
        dropTrips.close();
        PreparedStatement createTrips = conn.prepareStatement("CREATE TABLE trips (" +
            "id int NOT NULL," +
            "driver_id int NOT NULL" +
            "passenger_id int NOT NULL" +
            "start_location varchar(255) NOT NULL," +
            "destination varchar(255) NOT NULL," +
            "start_time datetime NOT NULL" +
            "end_time datetime NOT NULL" +
            "fee int NOT NULL" +
            "PRIMARY KEY (id)," +
            "FOREIGN KEY (driver_id) REFERENCES drivers(id),"+
            "FOREIGN KEY (passenger_id) REFERENCES passengers(id),"+
            "FOREIGN KEY (start_location) REFERENCES taxi_stop(name),"+
            "FOREIGN KEY (destination) REFERENCES taxi_stop(name),"+
            "CONSTRAINT c_id CHECK (id > 0)," +
            "CONSTRAINT c_fee CHECK (fee > 0)" +                      
        ")");
        createTrips.execute();
        createTrips.close();
        System.out.println("Processing...Done! Tables are created!");
    }
    
    private static void task12() throws Exception { // Delete tables
    }

    private static void task13() throws Exception { // Load data
        System.out.println("Please enter the folder path");
        String folder = in.nextLine();
        String path;
        CSVReader csvReader;
        String[] row;
    
        // drivers
        path = "./" + folder + "/drivers.csv";
        csvReader = new CSVReader(new FileReader(path));
        while ((row = csvReader.readNext()) != null) {
            PreparedStatement insertIntoDrivers = conn.prepareStatement(
                "INSERT INTO drivers VALUES (?, ?, ?, ?)"
            );
            
            // System.out.println(row[0] + " " + row[1] + " " + row[2] + " " + row[3]);
            
            insertIntoDrivers.setInt(1, Integer.parseInt(row[0]));
            insertIntoDrivers.setString(2, row[1]);
            insertIntoDrivers.setString(3, row[2]);
            insertIntoDrivers.setInt(4, Integer.parseInt(row[3]));
            insertIntoDrivers.execute();
            insertIntoDrivers.close();
        }
        
        // vehicles
        // passengers
        // trips
        // taxi_stops
    }
    
    private static void task14() throws Exception { // Check data
    }

    private static void task1() throws Exception { // System Administrator
        while (true) {
            System.out.println("Administrator, what would you like to do?");
            System.out.println("1. Create tables");
            System.out.println("");
            System.out.println("3. Load data");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            
            String response = in.nextLine();
            if (response.equals("1")) task11();
            else if (response.equals("2")) task12();
            else if (response.equals("3")) task13();
            else if (response.equals("4")) task14();
            else if (response.equals("5")) break;
            // else invalid
        }
    }
    
    private static void task2() throws Exception { // Passenger
    }
    
    private static void task3() throws Exception { // Driver
    }
    
    private static void task4() throws Exception { // Manager
    }
    
    private static void ask() throws Exception {
        while (true) {
            System.out.println("Welcome! Who are you?");
            System.out.println("1. An");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            
            String response = in.nextLine();
            if (response.equals("1")) task1();
            else if (response.equals("2")) task2();
            else if (response.equals("3")) task3();
            else if (response.equals("4")) task4();
            else if (response.equals("5")) break;
            // else invalid
        }
    }

    public static void main(String[] args) {
        try {
            in = new Scanner(System.in);
            
            String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/group33";
            String dbUsername = "Group33";
            String dbPassword = "3170group33";
            conn = DriverManager.getConnection(
                dbAddress,
                dbUsername,
                dbPassword
            );
            
            Statement stmt = conn.createStatement();
            stmt.execute("USE group33");
            
            ask();
            
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
