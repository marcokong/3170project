import java.util.Scanner;

public class Main {
  static void welcomeMessage() {
    System.out.println("Welcome! Who are you?\n1. An administrator\n2. A passenger\n3. A driver\n4. A manager\n5. None of the above\nPlease enter [1-4]");
  }
    
  static void adminMessage() {
    System.out.println("Administrator, what would you like to do?\n1. Create tables\n2. Deletes tables\n3. Load data\n4. Check data\n5. Go back\nPlease enter [1-5]");
    Scanner userInput= new Scanner(System.in);
    int newInteger= userInput.nextInt();
    switch(newInteger){
                case 1:
                    System.out.println("Administrator 1");
                    break;
                case 2:
                    System.out.println("Administrator 2");
                    break;
                case 3:
                    System.out.println("Administrator 3");
                    break;
                case 4:
                    System.out.println("Administrator 4");
                    break;
                case 5:
                    System.out.println("Administrator 5");
                    break;
            }
  }
    
  static void passengerMessage() {
    System.out.println("Passanger, what would you like to do? \n1. Request a ride\n2. Check trip records\n3. Go back\nPlease enter [1-3]");
    Scanner userInput= new Scanner(System.in);
    int newInteger= userInput.nextInt();
    switch(newInteger){
                case 1:
                    System.out.println("Passenger 1");
                    break;
                case 2:
                    System.out.println("Passenger 2");
                    break;
                case 3:
                    System.out.println("Passenger 3");
                    break;
            }
  }
  static void driverMessage() {
    System.out.println("Driver, what would you like to do? \n1. Search requests\n2. Take a request\n3. Finish a trip\n4. Go back\nPlease enter [1-4]");
    Scanner userInput= new Scanner(System.in);
    int newInteger= userInput.nextInt();
    switch(newInteger){
            case 1:
                System.out.println("Driver 1");
                break;
            case 2:
                System.out.println("Driver 2");
                break;
            case 3:
                System.out.println("Driver 3");
                break;
            case 4:
                System.out.println("Driver 4");
                break;
            }
  }
  static void managerMessage() {
    System.out.println("Manager, what would you like to do? \n1. Find trips\n2. Go back\nPlease enter [1-2]");
    Scanner userInput= new Scanner(System.in);
    int newInteger= userInput.nextInt();
    switch(newInteger){
            case 1:
                System.out.println("Manager 1");
                break;
            case 2:
                System.out.println("Manager 2");
                break;
            }    
  }
    
  public static void main(String[] args) {
    while(true){
        Scanner userInput= new Scanner(System.in);
        welcomeMessage();
        int identity= userInput.nextInt();
        switch(identity){
            case 1:
                adminMessage();
                break;
            case 2:
                passengerMessage();
                break;
            case 3:
                driverMessage();
                break;
            case 4:
                managerMessage();
                break;
        }
    }
  }
}
