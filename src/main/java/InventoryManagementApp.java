import java.sql.*;
import java.util.ArrayList;
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
    public static boolean deleteItem(String name, String user) {
        if (!itemExists(user, name)) {
            return true;
        }
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String deleteQuery = "DELETE FROM Inventory WHERE name = ? and username = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setString(1, name);
                deleteStatement.setString(2, user);
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
    public static ArrayList<Item> getInventory(String user) {
        ArrayList<Item> ret = new ArrayList<>();
        Item item;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Read operation
            String readQuery = "SELECT Name, Count FROM inventory where username = " + user;
            try (Statement readStatement = connection.createStatement();
                 ResultSet resultSet = readStatement.executeQuery(readQuery)) {
                while (resultSet.next()) {
                    item = new Item(resultSet.getString("name"), resultSet.getInt("count"), user);
                    ret.add(item);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return ret;
    }

    public static void displayInventory(String user) {
        ArrayList<Item> items = getInventory(user);
        for (var item : items) {
            item.displayItem();
        }

    }

//    public static void displayInventoryInstructions() {
//        System.out.println("""
//                1. Create an item in the database
//                2. Read the item with a name
//                3. Update the item with a new count
//                4. Delete item with a name
//                5. Exit
//                """);
//    }

    public static void displayDirections() {
        System.out.println("""
                    
                                            Hello User, this is an inventory managment system.
                                            You can login with your username and password.
                                            You can register with a unique username and password combination.
                                            You can update the the count for each item using increment/decrement/setvalue options.
                                            You can Logout when you are done.
                    
                """);
    }

    public static void displayUserMenu() {
        System.out.println("""
                Select from the following options.
                1. View Entire Inventory
                2. Search for Item
                3. Add item
                4. Remove Item
                5. Delete Item
                6. Directions
                7. Exit
                """);
    }

    private static Item searchItem(String user, String name) {
        Item ret;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String readQuery = "Select * from inventory where username = " + user + " and Name = " + name;
            try (Statement readStatement = connection.createStatement();
                 ResultSet resultSet = readStatement.executeQuery(readQuery)) {
                ret = new Item(name, resultSet.getInt(1), user);
                return ret;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean itemExists(String user, String name) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String readQuery = "Select * from inventory where username = " + user + " and Name = " + name;
            try (Statement readStatement = connection.createStatement();
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

    private static int itemCount(String user, String name) {
        int ret = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String readQuery = "Select count from inventory where username = " + user + " and Name = " + name;
            try (Statement readStatement = connection.createStatement();
                 ResultSet resultSet = readStatement.executeQuery(readQuery)) {
                ret = resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static void addItem(String user, String name, int count) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (itemExists(user, name)) {
                int curr = itemCount(user, name);
                int newCount = curr + count;
                String updateQuery = "UPDATE inventory SET count = ? WHERE username = ? and Name = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, newCount);
                    updateStatement.setString(2, user);
                    updateStatement.setString(3, name);
                    updateStatement.executeUpdate();
                    System.out.println("Record updated successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                String createQuery = "INSERT INTO inventory ( Name, Count, username) VALUES (?, ?, ?)";
                try (PreparedStatement createStatement = connection.prepareStatement(createQuery)) {
                    createStatement.setString(1, name);
                    createStatement.setInt(2, count);
                    createStatement.setString(3, user);
                    createStatement.executeUpdate();
                    System.out.println("Record created successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void removeItem(String user, String name, int count) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (itemExists(user, name)) {
                int curr = itemCount(user, name);
                int newCount = curr + count;
                if (newCount < 0) {
                    System.out.println("You cannot delete more than you have");
                    return;
                }
                String updateQuery = "UPDATE inventory SET count = ? WHERE username = ? and Name = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, newCount);
                    updateStatement.setString(2, user);
                    updateStatement.setString(3, name);
                    updateStatement.executeUpdate();
                    System.out.println("Record updated successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("The item does not exist");
                return;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void runInventory(String user) {
        int choice;
        Scanner scanner = new Scanner(System.in);
//        public static void displayUserMenu() {
//            System.out.println("""
//                Select from the following options.
//                1. View Entire Inventory
//                2. Search for Item
//                3. Add item
//                4. Remove Item
//                5. Delete Item
//                6. Directions
//                7. Exit
//                """);
//        }
        while (true) {
            displayUserMenu();
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
// view entire inventory
                    displayInventory(user);
                }
                case 2 -> {
// search for item
                    System.out.println("What is the name of the item that you want to search?");
                    String name = scanner.nextLine();
                    if (!itemExists(user, name)) {
                        System.out.println("The item does not exist");
                        continue;
                    }
                    Item curr = searchItem(user, name);
                    if (curr == null) {
                        System.out.println("The item does not exist");
                    } else {
                        curr.displayItem();

                    }


                }
                case 3 -> {
                    // add item
                    System.out.println("What is the name of the item that you want to add?");
                    String name = scanner.nextLine();
                    if (!itemExists(user, name)) {
                        System.out.println("The item does not exist");
                        continue;
                    }
                    System.out.println("How much of the item do you want to add?");
                    int count = scanner.nextInt();
                    addItem(user, name, count);
                }
                case 4 -> {
// remove item
                    System.out.println("What is the name of the item that you want to remove?");
                    String name = scanner.nextLine();
                    if (!itemExists(user, name)) {
                        System.out.println("The item does not exist");
                        continue;
                    }
                    System.out.println("How much of the item do you want to delete");
                    int count = scanner.nextInt();
                    removeItem(user, name, count);
                }
                case 5 -> {
                    // delete item
                    System.out.println("What is the name of the item that you want to delete?");
                    String name = scanner.nextLine();
                    if (itemExists(user, name)) {
                        deleteItem(user, name);
                    } else {
                        System.out.println("The item does not exist");
                    }

                }
                case 6 -> {
                    // display directions
                    displayDirections();
                }
                case 7 -> {

                    return;
                }
                default -> {
                    System.out.println("Invalid input detected");
                }
            }
        }
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
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String readQuery = "Select * from users where user = " + user + " and password = " + pass;
            try (Statement readStatement = connection.createStatement();
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
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String readQuery = "Select * from users where user = " + user;
            try (Statement readStatement = connection.createStatement();
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

    private static void insertUserNamePassword(String user, String pass) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
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


            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    // Login
                    while (true) {
                        System.out.println("Enter your User Name: ");
                        user = getNotNullInput(scanner);
                        System.out.println("Enter your password: ");
                        pass = getNotNullInput(scanner);
                        if (userpassExists(user, pass)) {
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
                                    case 2 -> {
                                        exiting = true;
                                    }
                                    default -> {
                                        System.out.println("You chose an invalid option");
                                    }

                                }
                                if (exiting) break;
                            }
                            if (exiting) break;
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
                        insertUserNamePassword(user, pass);

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
        }


    }
}
