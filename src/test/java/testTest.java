import static org.junit.jupiter.api.Assertions.*;

class testTest {

    @org.junit.jupiter.api.Test
    void testCRUD() {
        String name = "box";
        int count = 10;
        boolean succes = false;

        // Create
        succes = InventoryManagementApp.createItem(name,count);
        assertEquals(succes,true);
        String output;

        // Read
        output = InventoryManagementApp.readItem(name);

        assertEquals(output, "box 10");

        // Update
        succes = updateItem(name,20);
        assertEquals(succes,true);

        // Delete
        success = deleteItem(name);
        assertEquals(succes,true);


    }

    @org.junit.jupiter.api.Test
    void testRead() {
    }

    @org.junit.jupiter.api.Test
    void testUpdate() {
    }

    @org.junit.jupiter.api.Test
    void testDelete() {
    }

    @org.junit.jupiter.api.Test
    void ensureIntegrity() {
    }

    @org.junit.jupiter.api.Test
    void searchProduct() {
    }

    @org.junit.jupiter.api.Test
    void testAuthentication() {
    }

    @org.junit.jupiter.api.Test
    void testReport() {
    }
}