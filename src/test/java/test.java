import static org.junit.Assert.*;

import org.junit.*;

public class test {

    @Test
    public void testCRUD() {
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
        assertEquals(true);

        // Delete




    }

    @Test
    public void testRead() {

    }
    @Test
    public void testUpdate() {

    }
    @Test
    public void testDelete() {

    }

    @Test
    public void ensureIntegrity() {

    }

    @Test
    public void searchProduct() {

    }

    @Test
    public void testAuthentication() {

    }

    @Test
    public void testReport() {

    }

}
