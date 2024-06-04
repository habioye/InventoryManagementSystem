import java.sql.*;

public class InventoryManagementApp {
    String url = "jdbc:mysql://localhost:3306/sampledb";
    String username = "root";
    String password = "password";
    public static boolean createItem(String name, int count){
        try (Connection connection = DriverManager.getConnection(url,username,password)) {
            // Create operation
            String createQuery = "INSERT INTO users (name, count) VALUES (?, ?)";
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

    public static String readItem() {
        try (Connection connection = DriverManager.getConnection(url,username,password)) {

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static boolean updateItem() {
        try (Connection connection = DriverManager.getConnection(url,username,password)) {

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static boolean readItem() {
        try (Connection connection = DriverManager.getConnection(url,username,password)) {

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
    public static void displayInventory() {
        try (Connection connection = DriverManager.getConnection(url,username,password)) {

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
        displayInventory();
        displayInstructions();








    }
}
