package items;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item newItem) {
        items.add(newItem);
        System.out.println(newItem.getName() + " added to inventory!");
    }

    public boolean removeItem(String itemName) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getSize() {
        return items.size();
    }

    public void displayInventory() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty!");
            return;
        }

        System.out.println("\n=== INVENTORY ===");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + items.get(i).toString());
        }
        System.out.println("=================");
    }

    public String toSaveString() {
        StringBuilder sb = new StringBuilder();

        for (Item i : items) {
            sb.append(i.getName()).append(",");
        }

        return sb.toString();
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
