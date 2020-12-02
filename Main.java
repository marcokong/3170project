import java.io.*;
import java.util.*;
import java.sql.*;
import com.opencsv.*;

public class Main {
    private static Scanner in;
    private static Connection conn;
    
    private static void task11() throws Exception { // Create tables
		PreparedStatement dropTrips = conn.prepareStatement("DROP TABLE IF EXISTS trips");
        dropTrips.execute();
        dropTrips.close();
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
			"id int NOT NULL," +
			"name varchar(255) NOT NULL," +
			"PRIMARY KEY (id)," +
			"CONSTRAINT c_id CHECK (id > 0)," +
			"CONSTRAINT c_name CHECK (name <> '' AND LEN(name) <= 30)" +
		")");
		createPassengers.execute();
		createPassengers.close();
		
 
		
        // taxi_stops
		PreparedStatement dropTaxistops = conn.prepareStatement("DROP TABLE IF EXISTS taxi_stops");
        dropTaxistops.execute();
        dropTaxistops.close();
        PreparedStatement createTaxistops = conn.prepareStatement("CREATE TABLE taxi_stops (" +
            "name varchar(255) NOT NULL," +
            "location_x int NOT NULL," +
            "location_y int NOT NULL," +
            "PRIMARY KEY (name)," +
            "CONSTRAINT c_name CHECK (name <> '' AND LEN(name) <= 20)" +
        ")");
        createTaxistops.execute();
        createTaxistops.close();
		
		// requests
		PreparedStatement dropRequests = conn.prepareStatement("DROP TABLE IF EXISTS requests");
		dropRequests.execute();
		dropRequests.close();
		PreparedStatement createRequests = conn.prepareStatement("CREATE TABLE requests (" +
			"id int NOT NULL," +
			"passengers_id int NOT NULL UNIQUE," +
			"start_location varchar(255) NOT NULL," +
			"destination varchar(255) NOT NULL," +
			"model varchar(255)," +
			"passengers int NOT NULL," +
			"taken boolean," +
			"driving_years int," +
			"PRIMARY KEY (id)," +
			"FOREIGN KEY (passengers_id) REFERENCES passengers(id) ON DELETE CASCADE," +
			"FOREIGN KEY (start_location) REFERENCES taxi_stops(name) ON DELETE CASCADE," +
			"FOREIGN KEY (destination) REFERENCES taxi_stops(name) ON DELETE CASCADE," +
			"CONSTRAINT c_id CHECK (id > 0)," +
			"CONSTRAINT c_passengers CHECK (passnegers >= 1 AND passengers <=8)" +
			//"CONSTRAINT c_locations CHECK (STRCMP(start_location, destination) != 0)" +
		")");
		createRequests.execute();
		createRequests.close();
		
		// trips
		PreparedStatement createTrips = conn.prepareStatement("CREATE TABLE trips (" +
            "id int NOT NULL," +
            "driver_id int NOT NULL," +
            "passenger_id int NOT NULL," +
            "start_time datetime NOT NULL," +
            "end_time datetime NOT NULL," +
            "start_location varchar(255) NOT NULL," +
            "destination varchar(255) NOT NULL," +
            "fee int NOT NULL," +
            "PRIMARY KEY (id)," +
            "FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE CASCADE,"+
            "FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE,"+
            "FOREIGN KEY (start_location) REFERENCES taxi_stops(name) ON DELETE CASCADE,"+
            "FOREIGN KEY (destination) REFERENCES taxi_stops(name) ON DELETE CASCADE,"+
            "CONSTRAINT c_id CHECK (id > 0)," +
            "CONSTRAINT c_fee CHECK (fee > 0)" +
        ")");
        createTrips.execute();
        createTrips.close();
        
        System.out.println("Processing...Done! Tables are created!");
    }
    
    private static void task12() throws Exception { // Delete tables
		PreparedStatement dropTrips = conn.prepareStatement("DROP TABLE IF EXISTS trips");
        dropTrips.execute();
        dropTrips.close();
		PreparedStatement dropRequests = conn.prepareStatement("DROP TABLE IF EXISTS requests");
		dropRequests.execute();
		dropRequests.close();
		PreparedStatement dropDrivers = conn.prepareStatement("DROP TABLE IF EXISTS drivers");
        dropDrivers.execute();
        dropDrivers.close();
		PreparedStatement dropVehicles = conn.prepareStatement("DROP TABLE IF EXISTS vehicles");
		dropVehicles.execute();
		dropVehicles.close();
		PreparedStatement dropPassengers = conn.prepareStatement("DROP TABLE IF EXISTS passengers");
		dropPassengers.execute();
		dropPassengers.close();
		PreparedStatement dropTaxistops = conn.prepareStatement("DROP TABLE IF EXISTS taxi_stops");
        dropTaxistops.execute();
        dropTaxistops.close();
		
		System.out.println("Processing...Done! Tables are deleted");
		
		
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
		path = "./" + folder + "/passengers.csv";
        csvReader = new CSVReader(new FileReader(path));
		while ((row = csvReader.readNext()) != null) {
            PreparedStatement insertIntoPassengers = conn.prepareStatement(
                "INSERT INTO passengers VALUES (?, ?)"
            );
			
			insertIntoPassengers.setInt(1, Integer.parseInt(row[0]));
			insertIntoPassengers.setString(2, row[1]);
			insertIntoPassengers.execute();
			insertIntoPassengers.close();
		}
		
		
		
        // trips
		
		
		
        // taxi_stops
		path = "./" + folder + "/taxi_stops.csv";
        csvReader = new CSVReader(new FileReader(path));
        while ((row = csvReader.readNext()) != null){
            PreparedStatement insertIntoTaxistops = conn.prepareStatement(
                "INSERT INTO taxi_stops VALUES (?, ?, ?)"
            );
            
            insertIntoTaxistops.setString(1, row[0]);
            insertIntoTaxistops.setInt(2, Integer.parseInt(row[1]));
            insertIntoTaxistops.setInt(3, Integer.parseInt(row[2]));
            insertIntoTaxistops.execute();
            insertIntoTaxistops.close();
        }
		
		System.out.println("Processing...Data is loaded!");
    }
    
    private static void task14() throws Exception { // Check data
		System.out.println("Numbers of records in each table:");
        Statement stmt= conn.createStatement();
        /*String query="SELECT COUNT(*) AS records FROM vehicles";
        ResultSet rs= stmt.executeQuery(query);
        while(rs.next()){
            System.out.println("Vehicle: "+ rs.getInt("records"));}*/
        String query="SELECT COUNT(*) AS records FROM passengers";
        ResultSet rs= stmt.executeQuery(query);
        while(rs.next()){
            System.out.println("Passenger: "+ rs.getInt("records"));}
        query="SELECT COUNT(*) AS records FROM drivers";
        rs= stmt.executeQuery(query);
        while(rs.next()){
            System.out.println("Driver: "+ rs.getInt("records"));}
        query="SELECT COUNT(*) AS records FROM trips";
        rs= stmt.executeQuery(query);
        while(rs.next()){
            System.out.println("Trip: "+ rs.getInt("records"));}
        /*query="SELECT COUNT(*) AS records FROM requests";
        rs= stmt.executeQuery(query);
        while(rs.next()){
            System.out.println("Request: "+ rs.getInt("records"));}*/
        query="SELECT COUNT(*) AS records FROM taxi_stops";
        rs= stmt.executeQuery(query);
        while(rs.next()){
            System.out.println("Taxi_stop: "+ rs.getInt("records"));}
    }

    private static void task1() throws Exception { // System Administrator
        while (true) {
            System.out.println("Administrator, what would you like to do?");
            System.out.println("1. Create tables");
            System.out.println("2. Deletes tables");
            System.out.println("3. Load data");
            System.out.println("4. Check data");
            System.out.println("5. Go back");
            System.out.println("Please enter [1-5]");
            
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
        while (true){
            System.out.println("Passanger, what would you like to do? \n1. Request a ride\n2. Check trip records\n3. Go back\nPlease enter [1-3]");
            
            String response = in.nextLine();
            if (response.equals("1")) task21();
            else if (response.equals("2")) task22();
            else if (response.equals("3")) break;
            // else invalid
        }
    }
    
    private static void task21() throws Exception{ //request a ride
        
    }
    
    private static void task22() throws Exception{ //check trip records
        
    }
    
    private static void task3() throws Exception { // Driver
        while(true){
            System.out.println("Driver, what would you like to do? \n1. Search requests\n2. Take a request\n3. Finish a trip\n4. Go back\nPlease enter [1-4]");
            
            String response = in.nextLine();
            if (response.equals("1")) task31();
            else if (response.equals("2")) task32();
            else if (response.equals("3")) task33();
            else if (response.equals("4")) break;
            // else invalid
        }
    }
    
    private static void task31() throws Exception{ //search requests
        
    }
    
    private static void task32() throws Exception{ //take a request
        
    }
    
    private static void task33() throws Exception{ //finish a trip
        
    }
    
    private static void task4() throws Exception { // Manager
        while(true){
            System.out.println("Manager, what would you like to do? \n1. Find trips\n2. Go back\nPlease enter [1-2]");
            
            String response = in.nextLine();
            if (response.equals("1")) task41();
            else if (response.equals("2")) break;
        }
    }
    
    private static void task41() throws Exception{ //list all finished trips with travelling distances within a range
        
    }
    
    private static void ask() throws Exception {
        while (true) {
            System.out.println("Welcome! Who are you?");
            System.out.println("1. An administrator");
            System.out.println("2. A passenger");
            System.out.println("3. A driver");
            System.out.println("4. A manager");
            System.out.println("5. None of the above");
            System.out.println("Please enter [1-4]");
            
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
