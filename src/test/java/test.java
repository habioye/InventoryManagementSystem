import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class test {





    @Test
    public void test_valid_username_and_password_returns_true() {
        InventoryManagementApp app = new InventoryManagementApp();
        boolean result = app.userpassExists("validUser", "validPass");
        assertTrue(result);
    }

    @Test
    public void test_username_or_password_contains_special_characters() {
        InventoryManagementApp app = new InventoryManagementApp();
        boolean result = app.userpassExists("user!@#", "pass$%^");
        assertFalse(result);
    }




    @Test
    public void test_prints_main_menu_options_correctly() {
        InventoryManagementApp app = new InventoryManagementApp();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        app.displayMainMenu();

        String expectedOutput = "Select from the following options for the inventory.\n" +
                "1. Login\n" +
                "2. Register\n" +
                "3. View Instructions\n" +
                "4. Exit\n";
        assertEquals(expectedOutput, outContent.toString());
    }































}
