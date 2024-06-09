

public class Item {
    private String name;
    private int count;
    public String user;

    public Item(String name, int count) {
        this.name = name;
        this.count = count;
    }
    public Item(String name, int count, String user) {
        this.name = name;
        this.count = count;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.format("%-20s %10d", name, count);
    }
    public void displayItem() {
        // Print the header
        String format = "%-20s %10s";
        System.out.println(String.format(format, "Name", "Count"));
        System.out.println(String.format(format, "--------------------", "----------"));
        System.out.println(toString());
    }
}
