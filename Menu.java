import java.io.*;
import java.util.*;

public class Menu {
    private ArrayList<MenuItem> items = new ArrayList<>();

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public MenuItem getItem(int index) throws ItemNotFoundException {
        if (index < 0 || index >= items.size()) {
            throw new ItemNotFoundException("Item index out of range: " + index);
        }
        return items.get(index);
    }

    public int size() {
        return items.size();
    }

    public void listMenu() {
        if (items.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "Menu masih kosong." + ConsoleColors.RESET);
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.print(ConsoleColors.CYAN + (i + 1) + ". " + ConsoleColors.RESET);
            items.get(i).tampilMenu();
        }
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (MenuItem mi : items) {
                bw.write(mi.toDataString());
                bw.newLine();
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        items.clear();
        File f = new File(filename);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format: Type|name|price|category|extra
                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;
                String type = parts[0];
                String name = parts[1];
                double price = 0.0;
                try { price = Double.parseDouble(parts[2]); } catch (NumberFormatException e) {}
                String category = parts[3];
                if (type.equalsIgnoreCase("Makanan") && parts.length >= 5) {
                    items.add(new Makanan(name, price, category, parts[4]));
                } else if (type.equalsIgnoreCase("Minuman") && parts.length >= 5) {
                    items.add(new Minuman(name, price, category, parts[4]));
                } else if (type.equalsIgnoreCase("Diskon") && parts.length >= 5) {
                    double pct = 0.0;
                    try { pct = Double.parseDouble(parts[4]); } catch (NumberFormatException e) {}
                    items.add(new Diskon(name, pct, category));
                }
            }
        }
    }
}
