import java.sql.*;
import java.util.Scanner;

public class InventoryManagementApp {
    String url = "jdbc:mysql://localhost:3306/inventorydb";
    String username = "root";
    String password = "password";

    // adds the item in the inventory
    public static boolean createItem(String name, int count) {
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
    public static String readItem(String name) {
        String retName = "";
        int count = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String readQuery = "SELECT name, count FROM Inventory where name = "+name;
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
        String returnval = retName+" "+count;
        return returnval;
    }

    // updates the item value from the inventory.
    public static boolean updateItem(String name, int count) {
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
    public static boolean deleteItem(String name) {
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
    public static void displayInventory() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static void displayInstructions() {
        System.out.println("""
                1. Create an item in the database
                2. Read the item with a name
                3. Update the item with a new count
                4. Delete item with a name
                5. Exit
                """);
    }

    public static void main(String[] args) {
        System.out.println("""
                Hello User, here is a view of the inventory:
                """);

        Scanner scanner = new Scanner();
        int choice;
        String name;
        int count;

        // Main Inventory Visit Loop
        while (true) {
            displayInventory();
            displayInstructions();
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter (String name, int count)");
                    name = scanner.next();
                    count = scanner.nextInt();
                    createItem(name, count);


                }
                case 2 -> {
                    System.out.println("Enter (String name)");
                    name = scanner.next();
                    readItem(name);
                }
                case 3 -> {
                    System.out.println("Enter (String name, int count)");
                    name = scanner.next();
                    count = scanner.nextInt();
                    updateItem(name, count);
                }
                case 4 -> {
                    System.out.println("Enter (String name)");
                    name = scanner.next();
                    deleteItem(name);
                }
                case 5 -> {
                    System.out.println("Exited the inventory");
                    break;

                }
                default -> {
                    System.out.println("You entered an imporper value. Make sure the choice an integer in the" +
                            " range 1 to 5 (inclusive)");

                }
            }
            return;
        }


    }
}
