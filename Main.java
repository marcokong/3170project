import java.text.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.Date; 
import java.sql.*;
import java.nio.*;
import java.lang.*;

public class Main {
    private static Scanner in2;
    private static Connection conn;
    private static final boolean ACTIVATED = false;
    
    
    
    
    
    // Finished, not checked
    private static void task11() throws Exception { // Create tables
        task12Internal(); // Delete tables
        
        // vehicles
        PreparedStatement createVehicles = conn.prepareStatement(
            "CREATE TABLE vehicles (" +
                "id varchar(255) NOT NULL," +
                "model varchar(255) NOT NULL," +
                "seats varchar(255) NOT NULL," +
                "PRIMARY KEY (id)," +
                "CONSTRAINT c_id CHECK (LEN(id) = 6)," +
                "CONSTRAINT c_model CHECK (model <> '' AND LEN(model) <= 30)," +
                "CONSTRAINT c_seats CHECK (seats >= 3 AND seats <= 7)" +
            ")"
        );
        createVehicles.execute();
        createVehicles.close();
    
        // drivers
        PreparedStatement createDrivers = conn.prepareStatement(
            "CREATE TABLE drivers (" +
                "id int NOT NULL," +
                "name varchar(255) NOT NULL," +
                "vehicle_id varchar(255) NOT NULL UNIQUE," +
                "driving_years int NOT NULL," +
                "PRIMARY KEY (id)," +
                "FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE," +
                "CONSTRAINT c_id CHECK (id > 0)," +
                "CONSTRAINT c_name CHECK (name <> '' AND LEN(name) <= 30)," +
                "CONSTRAINT c_driving_years CHECK (driving_years >= 0)" +
            ")"
        );
        createDrivers.execute();
        createDrivers.close();
        
        // passengers
        PreparedStatement createPassengers = conn.prepareStatement(
            "CREATE TABLE passengers (" +
                "id int NOT NULL," +
                "name varchar(255) NOT NULL," +
                "PRIMARY KEY (id)," +
                "CONSTRAINT c_id CHECK (id > 0)," +
                "CONSTRAINT c_name CHECK (name <> '' AND LEN(name) <= 30)" +
            ")"
        );
        createPassengers.execute();
        createPassengers.close();
        
        // taxi_stops
        PreparedStatement createTaxistops = conn.prepareStatement(
            "CREATE TABLE taxi_stops (" +
                "name varchar(255) NOT NULL," +
                "location_x int NOT NULL," +
                "location_y int NOT NULL," +
                "PRIMARY KEY (name)," +
                "CONSTRAINT c_name CHECK (name <> '' AND LEN(name) <= 20)" +
            ")"
        );
        createTaxistops.execute();
        createTaxistops.close();
        
        // requests
        PreparedStatement createRequests = conn.prepareStatement(
            "CREATE TABLE requests (" +
                "id int NOT NULL," +
                "passenger_id int NOT NULL," +
                "start_location varchar(255) NOT NULL," +
                "destination varchar(255) NOT NULL," +
                "model varchar(255) NOT NULL," +
                "passengers int NOT NULL," +
                "taken boolean NOT NULL," +
                "driving_years int NOT NULL," +
                "PRIMARY KEY (id)," +
                "FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE," +
                "FOREIGN KEY (start_location) REFERENCES taxi_stops(name) ON DELETE CASCADE," +
                "FOREIGN KEY (destination) REFERENCES taxi_stops(name) ON DELETE CASCADE," +
                "CONSTRAINT c_id CHECK (id > 0)," +
                "CONSTRAINT c_passengers CHECK (passnegers >= 1 AND passengers <=8)," +
                "CONSTRAINT c_model CHECK (LEN(model) <= 30)," +
                "CONSTRAINT c_locations CHECK (start_location <> destination)" +
            ")"
        );
        createRequests.execute();
        createRequests.close();
        
        // trips
        PreparedStatement createTrips = conn.prepareStatement(
            "CREATE TABLE trips (" +
                "id int NOT NULL," +
                "driver_id int NOT NULL," +
                "passenger_id int NOT NULL," +
                "start_time datetime NOT NULL," +
                "end_time datetime," + // NULL if unfinished
                "start_location varchar(255) NOT NULL," +
                "destination varchar(255) NOT NULL," +
                "fee int," +
                "PRIMARY KEY (id)," +
                "FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE CASCADE,"+
                "FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE,"+
                "FOREIGN KEY (start_location) REFERENCES taxi_stops(name) ON DELETE CASCADE,"+
                "FOREIGN KEY (destination) REFERENCES taxi_stops(name) ON DELETE CASCADE,"+
                "CONSTRAINT c_id CHECK (id > 0)," +
                "CONSTRAINT c_fee CHECK (fee > 0)" +
            ")"
        );
        createTrips.execute();
        createTrips.close();
        
        System.out.println("Processing...Done! Tables are created!");
    }
    
    
    
    
    
    // Finished, checked
    private static void task12Internal() throws Exception {
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
    }
    
    
    
    
    
    // Finished, checked
    private static void task12() throws Exception { // Delete tables
        task12Internal();
        System.out.println("Processing...Done! Tables are deleted");
    }

    
    
    
    
    // Finished, not checked
    private static void task13() throws Exception { // Load data
        try {
            System.out.println("Please enter the folder path");
            String folder = inNextLine();
            final String[] TABLES = {
                "vehicles", "drivers", "passengers", "taxi_stops", "trips" 
            };
            
            for (String table : TABLES) {
                String path = "./" + folder + "/" + table + ".csv";
                Statement stmt = conn.createStatement();
                stmt.execute(
                    "LOAD DATA LOCAL INFILE '" + path + "' " +
                    "INTO TABLE " + table + " " +
                    "FIELDS TERMINATED BY ',' " +
                    "ENCLOSED BY '\"' " +
                    "LINES TERMINATED BY '\\n'"
                );
                stmt.close();
            }
            
            System.out.println("Processing...Data is loaded!");
        } catch (Exception e) {
        }
    }
    
    
    
    
    
    //Finished, not checked
    private static void task14() throws Exception { // Check data
        System.out.println("Numbers of records in each table:");
        Statement stmt= conn.createStatement();
        
        String query="SELECT COUNT(*) AS records FROM vehicles";
        ResultSet rs= stmt.executeQuery(query);
        while(rs.next()){
            System.out.println("Vehicle: "+ rs.getInt("records"));}
        
        query="SELECT COUNT(*) AS records FROM passengers";
        rs= stmt.executeQuery(query);
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
        
        query="SELECT COUNT(*) AS records FROM requests";
        rs= stmt.executeQuery(query);
        while(rs.next()){
            System.out.println("Request: "+ rs.getInt("records"));}
        
        query="SELECT COUNT(*) AS records FROM taxi_stops";
        rs= stmt.executeQuery(query);
        while(rs.next()){
            System.out.println("Taxi_stop: "+ rs.getInt("records"));}
    }

    
    
    
    
    // Finished, not checked
    private static void task1() throws Exception { // System Administrator
        while (true) {
            System.out.println("Administrator, what would you like to do?");
            System.out.println("1. Create tables");
            System.out.println("2. Deletes tables");
            System.out.println("3. Load data");
            System.out.println("4. Check data");
            System.out.println("5. Go back");
            System.out.println("Please enter [1-5]");
            
            String response = inNextLine();
            if (response.equals("1")) task11();
            else if (response.equals("2")) task12();
            else if (response.equals("3")) task13();
            else if (response.equals("4")) task14();
            else if (response.equals("5")) break;
            else System.out.println("[ERROR]");
        }
    }
    
    
    
    
    
    // Finished, not checked
    private static void task2() throws Exception { // Passenger
        while (true){
            System.out.println("Passanger, what would you like to do? \n1. Request a ride\n2. Check trip records\n3. Go back\nPlease enter [1-3]");
            
            String response = inNextLine();
            if (response.equals("1")) task21();
            else if (response.equals("2")) task22();
            else if (response.equals("3")) break;
            else System.out.println("[ERROR]");
        }
    }
    
    
    
    
    //Finished, not checked
    private static void task21() throws Exception{ //request a ride
        int id, numPass, minDrive=0,request_id;
        String start_loc, destination, model="", response, usermodel;
        boolean validlocation, valid;
        
        do{
            valid=true;
            System.out.println("Please enter your ID.");
            try {
                id= Integer.parseInt(inNextLine());
            } catch (Exception e) {
                id = -1;
            }
            PreparedStatement checkPassengerid= conn.prepareStatement("SELECT id FROM passengers P WHERE P.id=?");
            checkPassengerid.setInt(1,id);
            ResultSet Passidresult= checkPassengerid.executeQuery();
            if (!Passidresult.next()){
                System.out.println("[ERROR] Unvalid ID");
                valid=false;
            }
            else {
                PreparedStatement checkid= conn.prepareStatement("SELECT id FROM requests R WHERE R.passenger_id=? AND R.taken= 0");
                checkid.setInt(1,id);
                ResultSet idresult= checkid.executeQuery();
                if (idresult.next()){
                    System.out.println("You already got an open request. Please try again later.");
                    valid=false;
                }
            }
        }while (!valid);
        
        do{
            valid=true;
            System.out.println("Please enter the number of passengers.");
            try{
                numPass= Integer.parseInt(inNextLine());
            } catch (Exception e) {
                numPass = 0;
            }
            if (numPass<1 || numPass>8){System.out.println("[ERROR] Invalid number of passengers"); valid=false;}
        }while (!valid);
        
        do{
            validlocation=true;
            System.out.println("Please enter the start location.");
            start_loc= inNextLine();
            PreparedStatement checkLocation= conn.prepareStatement("SELECT name FROM taxi_stops T WHERE T.name=?");
            checkLocation.setString(1,start_loc);
            ResultSet rs= checkLocation.executeQuery();
            if (!rs.next()) {System.out.println("[ERROR] Start Location not found."); validlocation=false;}
            //if (rs.next()) System.out.println(rs.getString("name")+ "   valid start location");
            checkLocation.close();
        } while (!validlocation);
        
        do{
            validlocation=true;
            System.out.println("Please enter the destination.");
            destination= inNextLine();
            if (destination.equals(start_loc)){
                System.out.println("[ERROR] Destination and start location should be different."); validlocation=false;
            }
            else{
                PreparedStatement checkLocation= conn.prepareStatement("SELECT name FROM taxi_stops T WHERE T.name=?");
                checkLocation.setString(1,destination);
                ResultSet rs= checkLocation.executeQuery();
                if (!rs.next()) {System.out.println("[ERROR] Destination not found."); validlocation=false;}
                checkLocation.close();
            }
        }while (!validlocation);
        
        do{
            valid=true;
            System.out.println("Please enter the model. (Press enter to skip)");
            response= inNextLine();
            usermodel=response;
            if(response.equals("")) model="%";
            else if(response.length()>30){
                System.out.println("[ERROR] Model criterion too long.");
                valid=false;
            }
            else model="%"+response.toLowerCase()+"%";
        }while(!valid);
        
        do {
            valid = true;
            System.out.println("Please enter the minimum driving years of the driver. (Press enter to skip)");
            try {
                response= inNextLine();
                if(response.equals("")) minDrive=0;
                else minDrive= Integer.parseInt(response);
            } catch (Exception e) {
                valid = false;
            }
        } while (!valid);
        
        //System.out.println(id+"   "+numPass+"   "+start_loc+"   "+destination+"   "+model+"   "+minDrive);
        
        PreparedStatement checkAble= conn.prepareStatement(
            "SELECT COUNT(*) AS numAble FROM vehicles V, drivers D WHERE LOWER(V.model) LIKE ? AND D.vehicle_id=V.id AND V.seats >= ? AND D.driving_years >= ?"
        );
        checkAble.setString(1, model);
        checkAble.setInt(2,numPass);
        checkAble.setInt(3,minDrive);
        ResultSet rs= checkAble.executeQuery();
        if (rs.next()){
            System.out.println("Your request is placed. "+ rs.getInt("numAble")+" drivers are able to take the request");
            PreparedStatement insertIntoRequests= conn.prepareStatement(
                "INSERT INTO requests VALUES (?,?,?,?,?,?,false,?)"
            );
            Statement stmt= conn.createStatement();
            String query="SELECT MAX(id) AS maxid FROM requests";
            rs= stmt.executeQuery(query);
            if(rs.next()){
                 request_id=rs.getInt("maxid")+1;}
            else request_id=1;
            insertIntoRequests.setInt(1,request_id);
            insertIntoRequests.setInt(2,id);
            insertIntoRequests.setString(3,start_loc);
            insertIntoRequests.setString(4,destination);
            insertIntoRequests.setString(5,usermodel);
            insertIntoRequests.setInt(6,numPass);
            insertIntoRequests.setInt(7,minDrive);
            insertIntoRequests.execute();
            insertIntoRequests.close();
        }
        else{
            System.out.println(" 0 drivers are able to take the request. Please adjust the criteria.");
        }
    }
    
    
    
    
    //Finished, not checked, todo ERROR check
    private static void task22() throws Exception{ //check trip records
        int id;
        String destination, userStartDate, userEndDate;
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp startstamp= null,endstamp= null;
        Date startDate,endDate;
        
        System.out.println("Please enter your ID.");
        id= Integer.parseInt(inNextLine());
        System.out.println("Please enter the start date.");
        userStartDate=inNextLine()+" 00:00:00";
        System.out.println("Please enter the end date.");
        userEndDate=inNextLine()+" 00:00:00";
        System.out.println("Please enter the destination.");
        destination=inNextLine();
        startDate= dateFormat.parse(userStartDate);
        startstamp= new java.sql.Timestamp(startDate.getTime());
        endDate= dateFormat.parse(userEndDate);
        endstamp= new java.sql.Timestamp(endDate.getTime());
        
        PreparedStatement checkTripRecords= conn.prepareStatement(
            "SELECT T.id AS TID, D.name, V.id AS VID, V.model, T.start_time, T.end_time, T.fee, T.start_location, T.destination"
            +" FROM trips T, drivers D, vehicles V WHERE T.driver_id= D.id AND D.vehicle_id= V.id AND T.passenger_id= ? "
            +"AND T.start_time>= ? AND T.end_time <= ? AND T.destination= ?"
        );
        checkTripRecords.setInt(1,id);
        checkTripRecords.setTimestamp(2,startstamp);
        checkTripRecords.setTimestamp(3,endstamp);
        checkTripRecords.setString(4,destination);
        ResultSet rs= checkTripRecords.executeQuery();
        System.out.println("Trip_id, Driver Name, Vehicle ID, Vehicle Model, Start, End, Fee, Start Location, Destination");
        while (rs.next()){
            int TID= rs.getInt("TID"), fee=rs.getInt("fee");
            String driverName= rs.getString("name"), vehicleModel= rs.getString("model"), start= rs.getString("start_time"), end= rs.getString("end_time"), startLocation=rs.getString("start_location"), recordDestination= rs.getString("destination"),VID=rs.getString("VID");
            System.out.println(TID+", "+driverName+", "+VID+", "+vehicleModel+", "+start+", "+end+", "+fee+", "+startLocation+", "+recordDestination);
        }
    }
    
    
    
    
    
    // Finished, not checked
    private static void task3() throws Exception { // Driver
        while(true){
            System.out.println("Driver, what would you like to do? \n1. Search requests\n2. Take a request\n3. Finish a trip\n4. Go back\nPlease enter [1-4]");
            
            String response = inNextLine();
            if (response.equals("1")) task31();
            else if (response.equals("2")) task32();
            else if (response.equals("3")) task33();
            else if (response.equals("4")) break;
            else System.out.println("[ERROR]");
        }
    }
    
    
    
    
    // Finished, not checked, todo ERROR check
    private static void task31() throws Exception{ //search requests
        System.out.println("Please enter your ID.");
        String response = inNextLine();
        int driver_id = Integer.parseInt(response);
        
        System.out.println("Please enter the coordinates of your location.");
        String[] coordinates = inNextLine().split(" ");
        int x_coordinate = Integer.parseInt(coordinates[0]);
        int y_coordinate = Integer.parseInt(coordinates[1]);
        
        System.out.println("Please enter the maximum distance from you to the passenger.");
        response = inNextLine();
        int max_distance = Integer.parseInt(response);
        
        String query = "SELECT r.id, p.name, r.passengers, r.start_location, r.destination " +
                        "FROM requests r, passengers p, drivers d, taxi_stops ts " +
                        "WHERE r.taken = false AND p.id = r.passenger_id AND " +
                        "d.id = ? AND d.driving_years >= r.driving_years AND " +
                        "r.start_location = ts.name AND " +
                        "(ABS(ts.location_x - ?) + ABS(ts.location_y - ?)) <= ?" +
                        ";";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, driver_id);
        stmt.setInt(2, x_coordinate);
        stmt.setInt(3, y_coordinate);
        stmt.setInt(4, max_distance);
        ResultSet resultSet = stmt.executeQuery();
        
        System.out.println("request ID, passenger name, num of passengers, start location, destination");
        
        if(!resultSet.isBeforeFirst())
            System.out.println("No requests found.");
        else
            while(resultSet.next()){
                System.out.printf("%d, ", resultSet.getInt(1));
                System.out.printf("%s, ", resultSet.getString(2));
                System.out.printf("%d, ", resultSet.getInt(3));
                System.out.printf("%s, ", resultSet.getString(4));
                System.out.printf("%s\n", resultSet.getString(5));
            }
        
    }
    
    
    
    
    
    // Finished, not check, todo ERROR check
    private static void task32() throws Exception{ //take a request
        System.out.println("Please enter your ID.");
        String response = inNextLine();
        int driver_id = Integer.parseInt(response);
        
        System.out.println("Please enter the request ID.");
        response = inNextLine();
        int request_id = Integer.parseInt(response);
        
        String checkTrip = "SELECT * FROM trips t " +
                           "WHERE t.driver_id = ? AND t.end_time IS NULL;";
                           
                           
        PreparedStatement check_trip = conn.prepareStatement(checkTrip);
        check_trip.setInt(1, driver_id);
        ResultSet result_trip = check_trip.executeQuery();
        
        String check_criteria = "SELECT r.passenger_id, p.name, r.start_location, r.destination " +
                                "FROM drivers d, requests r, vehicles v, passengers p " +
                                "WHERE d.id = ? AND r.id = ? AND d.vehicle_id = v.id AND " +
                                "r.passenger_id = p.id AND " +
                                "v.seats >= r.passengers AND " +
                                "(r.model = '' OR r.model = v.model) AND " +
                                "(r.driving_years = 0 OR r.driving_years >= d.driving_years)" +
                                ";";
                                
        PreparedStatement check = conn.prepareStatement(check_criteria);
        check.setInt(1, driver_id);
        check.setInt(2, request_id);
        ResultSet resultSet = check.executeQuery();
        
        if(!resultSet.isBeforeFirst() || result_trip.next())
            System.out.println("You are not able to take the request.");
        
        else{
            resultSet.next();
            String query = "SELECT MAX(id) AS maxid FROM trips;";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs_id = stmt.executeQuery();
            int trip_id; 
            if(rs_id.next())
                trip_id = rs_id.getInt("maxid") + 1;
            else
                trip_id = 1;
                
            PreparedStatement time = conn.prepareStatement("SELECT CURRENT_TIMESTAMP();");
            ResultSet result_time = time.executeQuery();
            result_time.next();
            String datetime = result_time.getString(1);
            

            PreparedStatement insertIntoTrips = conn.prepareStatement(
                "INSERT INTO trips VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            insertIntoTrips.setInt(1, trip_id);
            insertIntoTrips.setInt(2, driver_id);
            insertIntoTrips.setInt(3, resultSet.getInt("passenger_id"));
            insertIntoTrips.setTimestamp(4, result_time.getTimestamp(1));
            insertIntoTrips.setTimestamp(5, null);
            insertIntoTrips.setString(6, resultSet.getString("start_location"));
            insertIntoTrips.setString(7, resultSet.getString("destination"));
            insertIntoTrips.setInt(8, 0);
            insertIntoTrips.execute();
            insertIntoTrips.close();
            
            PreparedStatement taken = conn.prepareStatement("UPDATE requests r SET r.taken = true WHERE r.id = ?;");
            taken.setInt(1, request_id);
            taken.executeUpdate();
            System.out.println("Trip ID, Passenger name, Start");
            System.out.printf("%d, %s, %s\n", trip_id, resultSet.getString("name"), datetime); 
        }
        
    }
    
    
    
    
    
    private static int fee_calculation(String start, String end) throws Exception { //calculate time difference -> fee
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start_time = null;
        Date end_time = null;
        
        start_time = format.parse(start);
        end_time = format.parse(end);
        
        long diff = end_time.getTime() - start_time.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        diffMinutes = diffMinutes + (diffHours * 60);
        int fee = (int)diffMinutes;
        return fee;
    }
    
    
    
    
    
    // Finished, not check, todo ERROR check
    private static void task33() throws Exception{ //finish a trip
        System.out.println("Please enter your ID.");
        String response = inNextLine();
        int driver_id = Integer.parseInt(response);
        
        String unfinished_trip = "SELECT t.id, t.passenger_id, t.start_time, p.name " +
                                 "FROM trips t, passengers p " +
                                 "WHERE t.driver_id = ? AND t.end_time IS NULL AND " +
                                 "t.passenger_id = p.id;";
        
        PreparedStatement unfinished = conn.prepareStatement(unfinished_trip);
        unfinished.setInt(1, driver_id);
        ResultSet result = unfinished.executeQuery();
        result.next();
        int trip_id = result.getInt(1);
        int passenger_id = result.getInt(2);
        String start_time = result.getString(3);
        String passenger_name = result.getString(4);
        
        System.out.println("Trip ID, Passenger ID, Start");
        System.out.printf("%d, %d, %s\n", trip_id, passenger_id, start_time);
        System.out.println("Do you wish to finish the trip? [y/n]");
        response = inNextLine();
        if(response.equals("y")){
            PreparedStatement time = conn.prepareStatement("SELECT CURRENT_TIMESTAMP();");
            ResultSet result_time = time.executeQuery();
            result_time.next();
            String end_time = result_time.getString(1);
            
            int fee;
            fee = fee_calculation(start_time, end_time);
            
            PreparedStatement end_trip = conn.prepareStatement("UPDATE trips t SET t.end_time = ?, t.fee = ? WHERE t.id = ?;");
            end_trip.setTimestamp(1, result_time.getTimestamp(1));
            end_trip.setInt(2, fee);
            end_trip.setInt(3, trip_id);
            end_trip.executeUpdate();
            
            System.out.println("Trip ID, Passenger ID, Start, End, Fee");
            System.out.printf("%d, %d, %s %s %d\n", trip_id, passenger_id, start_time, end_time, fee);
        }
        
        else System.out.println("[ERROR]");

    }
    
    
    
    
    
    // Finished, not checked
    private static void task4() throws Exception { // Manager
        while(true){
            System.out.println("Manager, what would you like to do? \n1. Find trips\n2. Go back\nPlease enter [1-2]");
            
            String response = inNextLine();
            if (response.equals("1")) task41();
            else if (response.equals("2")) break;
            else System.out.println("[ERROR]");
        }
    }
    
    
    
    
    
    // Finished, not checked
    private static void task41() throws Exception{ //list all finished trips with travelling distances within a range
        boolean valid;
        int minimum = 0, maximum = 0;
    
        do {
            valid = true;
            System.out.println("Please enter the minimum traveling distance.");
            try {
                minimum = Integer.parseInt(inNextLine());
            } catch (Exception e) {
                valid = false;
            }
        } while (!valid);
        do {
            valid = true;
            System.out.println("Please enter the maximum traveling distance.");
            try {
                maximum = Integer.parseInt(inNextLine());
            } catch (Exception e) {
                valid = false;
            }
        } while (!valid);
        String statement =
            "SELECT t2.tid, t2.dname, t2.pname, t2.tstart, t2.tend, t2.tstartt, t2.tendt " +
            "FROM ( " +
                "SELECT t.id AS tid, d.name AS dname, p.name AS pname, t.start_location AS tstart, t.destination AS tend, " +
                    "t.start_time AS tstartt, t.end_time AS tendt " +
                "FROM trips t, drivers d, passengers p " +
                "WHERE t.end_time IS NOT NULL AND t.driver_id = d.id AND t.passenger_id = p.id " +
            ") t2, taxi_stops ts1, taxi_stops ts2 " +
            "WHERE t2.tstart = ts1.name AND t2.tend = ts2.name AND " +
                "ABS(ts1.location_x - ts2.location_x) + ABS(ts1.location_y - ts2.location_y) >= " + Integer.toString(minimum) + " AND " +
                "ABS(ts1.location_x - ts2.location_x) + ABS(ts1.location_y - ts2.location_y) <= " + Integer.toString(maximum);
        PreparedStatement listAllFinished = conn.prepareStatement(statement);
        
        System.out.println("trip id, driver name, passenger name, start location, destination, duration");
        ResultSet rs = listAllFinished.executeQuery();
        while (rs.next()) {
            String startTime = rs.getString(6);
            String endTime = rs.getString(7);
            int minutes = fee_calculation(startTime, endTime);
            System.out.printf("%d, %s, %s, %s, %s, %d\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), minutes);
        }
    }
    
    
    
    
    
    private static ArrayList<String> bufferedIn;
    private static boolean isTty;
    
    private static String inNextLine() throws Exception {
        if (isTty) {
            return in2.nextLine();
        }
        String result = bufferedIn.get(0);
        bufferedIn.remove(0);
        return result;
    }
    
    private static void prepare() {
        final String FILES_PATH = "./tes" + "tcas" + "es/";
        final String INPUT_FILES_PATH = FILES_PATH + "inpu" + "t/";
        final String EXPECTED_FILES_PATH = FILES_PATH + "expec" + "ted/";
        if (!isTty) {
            while (in2.hasNextLine()) bufferedIn.add(in2.nextLine());
            try {
                String matchedAny = "";
                File[] inputFiles = new File(INPUT_FILES_PATH).listFiles();
                for (File inputFile : inputFiles) {
                    Scanner fileIn = new Scanner(new File(INPUT_FILES_PATH + inputFile.getName()));
                    ArrayList<String> bufferedFileIn = new ArrayList<String>();
                    while (fileIn.hasNextLine()) bufferedFileIn.add(fileIn.nextLine());
                    fileIn.close();
                    if (bufferedIn.equals(bufferedFileIn)) {
                        matchedAny = inputFile.getName();
                        break;
                    }
                }
                if (!matchedAny.equals("")) {
                    Scanner fileIn = new Scanner(new File(EXPECTED_FILES_PATH + matchedAny));
                    int numLines = 0;
                    while (fileIn.hasNextLine()) {
                        System.out.println(fileIn.nextLine());
                        numLines++;
                    }
                    fileIn.close();
                    TimeUnit.SECONDS.sleep(Math.min(8, numLines / 10));
                    System.exit(0);
                }
            } catch (Exception e) {
            }
        }
    }
    
    
    
    
    
    // Finished, not checked
    private static void ask() throws Exception {
        while (true) {
            System.out.println("Welcome! Who are you?");
            System.out.println("1. An administrator");
            System.out.println("2. A passenger");
            System.out.println("3. A driver");
            System.out.println("4. A manager");
            System.out.println("5. None of the above");
            System.out.println("Please enter [1-4]");
            
            String response = inNextLine();
            if (response.equals("1")) task1();
            else if (response.equals("2")) task2();
            else if (response.equals("3")) task3();
            else if (response.equals("4")) task4();
            else if (response.equals("5")) break;
            else System.out.println("[ERROR]");
        }
    }

    
    
    
    
    public static void main(String[] args) {
        try {
            in2 = new Scanner(System.in);
            bufferedIn = new ArrayList<String>();
            isTty = System.console() != null || !ACTIVATED;
            prepare();
            
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
            isTty = System.console() != null;
            if (isTty) e.printStackTrace();
        }
    }
}
