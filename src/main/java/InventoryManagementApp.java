import java.sql.*;
import java.util.Scanner;

public class InventoryManagementApp {
    static String url = "jdbc:mysql://localhost:3306/inventorydb";
    static String username = "root";
    static String password = "root";

    // adds the item in the inventory
    public static boolean createItem(String name, int count, String user) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Create operation
            String createQuery = "INSERT INTO Inventory (name, count) VALUES (?, ?)";
            try (PreparedStatement createStatement = connection.prepareStatement(createQuery)) {
                createStatement.setString(2, name);
                createStatement.setInt(3, count);
                createStatement.executeUpdate();
                System.out.println("Record created successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }

    // gets the item value from the inventory
    public static String readItem(String name, String user) {
        String retName = "";
        int count = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String readQuery = "SELECT name, count FROM Inventory where name = " + name;
            try (Statement readStatement = connection.createStatement();
                 ResultSet resultSet = readStatement.executeQuery(readQuery)) {
                while (resultSet.next()) {
                    retName = resultSet.getString("name");
                    count = resultSet.getInt("count");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return null;
        }
        String returnval = retName + " " + count;
        return returnval;
    }

    // updates the item value from the inventory.
    public static boolean updateItem(String name, int count, String user) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String updateQuery = "UPDATE Inventory SET count = ? WHERE name = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, count);
                updateStatement.setString(2, name);
                updateStatement.executeUpdate();
                System.out.println("Record updated successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }

    // deletes the item from the inventory
    public static boolean deleteItem(String name, String) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String deleteQuery = "DELETE FROM Inventory WHERE name = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setString(1, name);
                deleteStatement.executeUpdate();
                System.out.println("Record deleted successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }

    // Shows the entire inventory (excluding the id)
    public static void displayInventory(String user) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static void displayInventoryInstructions() {
        System.out.println("""
                1. Create an item in the database
                2. Read the item with a name
                3. Update the item with a new count
                4. Delete item with a name
                5. Exit
                """);
    }

    public static void displayDirections() {
        System.out.println("""
                    
                                            Hello User, this is an inventory managment system.
                                            You can login with your username and password.\s
                                            You can register with a unique username and password combination.
                                            You can update the the count for each item using increment/decrement/setvalue options.
                                            You can Logout when you are done""\");
                    
                """);
    }

    public static void displayUserMenu() {
        System.out.println("""
                Select from the following options.
                1. View Entire Inventory
                2. Add item
                3. Remove Item
                4. Logout
                5. Exit
                """);
    }

    public static void displayMainMenu() {
        System.out.println("""       
                Select from the following options for the inventory.
                1. Login
                2. Register
                3. View Instructions
                4. Exit
                """);
    }

    public static String getNotNullInput(Scanner scanner) {
        String ret;
        while (true) {
            ret = scanner.nextLine();
            if (ret.isEmpty() || ret.isBlank()) {
                System.out.println("Do not give a blank input");
            } else {
                return ret;
            }
        }
    }

    public static boolean userpassExists(String user, String pass) {
        try(Connection connection = DriverManager.getConnection(url,username,password)) {
            String readQuery = "Select * from users where user = " + user+" and password = "+pass;
            try(Statement readStatement = connection.createStatement();
            ResultSet resultSet = readStatement.executeQuery(readQuery)) {
                return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean userExists(String user) {
        try(Connection connection = DriverManager.getConnection(url,username,password)) {
            String readQuery = "Select * from users where user = " + user;
            try(Statement readStatement = connection.createStatement();
                ResultSet resultSet = readStatement.executeQuery(readQuery)) {
                return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private static void runInventory(String user) {

    }
    private static void insertUserNamePassword(String user, String pass) {
        try (Connection connection = DriverManager.getConnection(url, username,password)) {
            // Create operation
            String createQuery = "INSERT INTO users ( username, password) VALUES ( ?, ?)";
            try (PreparedStatement createStatement = connection.prepareStatement(createQuery)) {
                createStatement.setString(1, user);
                createStatement.setString(2, pass);
                createStatement.executeUpdate();
                System.out.println("Record created successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        displayDirections();

        Scanner scanner = new Scanner(System.in);
        int choice;
        String name;
        int count;
        String user;
        String pass;
        boolean exiting;

        // Main Inventory Visit Loop
        while (true) {
            displayMainMenu();
            displayInventory();

            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    // Login
                    while (true) {
                        System.out.println("Enter your User Name: ");
                        user = getNotNullInput(scanner);
                        System.out.println("Enter your password: ");
                        pass = getNotNullInput(scanner);
                        if (userpassExists(user,pass)) {
                            runInventory(user);
                            break;
                        } else {
                            System.out.println("The User Name and password combination does not exist");
                            exiting = false;
                            while (true) {
                                System.out.println("""
                                        1. To try again
                                        2. Exit""");
                                choice = scanner.nextInt();
                                switch (choice) {
                                    case 1 -> {
                                    }
                                    case 2-> {
                                        exiting = true;
                                    }
                                    default -> {
                                        System.out.println("You chose an invalid option");
                                    }

                                }
                                if (exiting) break;
                            }
                        }


                    }

                }
                case 2 -> {
                    // Register
                    while (true) {
                        System.out.println("Enter your new User Name: ");
                        user = getNotNullInput(scanner);
                        if (userExists(user)) {
                            System.out.println("The username is already in use");
                            continue;
                        }
                        System.out.println("Enter your new password: ");
                        pass = getNotNullInput(scanner);
                        insertUserNamePassword(user,pass);

                    }
                }
                case 3 -> {
                    // View Instructions
                    displayDirections();
                }
                case 4 -> {
                    // Exit
                    System.out.println("Exiting.");
                    return;

                }
                default -> {
                    System.out.println("You selected an invalid option");

                }
            }
            return;
        }


    }
}
