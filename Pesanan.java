import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Pesanan {
    private ArrayList<MenuItem> ordered = new ArrayList<>();

    public void addItem(MenuItem m) {
        ordered.add(m);
    }

    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(ordered);
    }

    public double calculateTotal() {
        double subtotal = 0.0;
        ArrayList<Diskon> discounts = new ArrayList<>();
        for (MenuItem mi : ordered) {
            if (mi instanceof Diskon) {
                discounts.add((Diskon) mi);
            } else {
                subtotal += mi.getPrice();
            }
        }
        double total = subtotal;
        for (Diskon d : discounts) {
            total = total * (1 - d.getDiskonPercent() / 100.0);
        }
        return total;
    }

    public String buildReceiptString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== STRUK PESANAN =====\n");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        sb.append("Waktu: ").append(sdf.format(new Date())).append('\n');
        double subtotal = 0.0;
        for (MenuItem mi : ordered) {
            if (mi instanceof Diskon) {
                sb.append(String.format("(Diskon) %s - %.2f%%\n", mi.getName(), ((Diskon) mi).getDiskonPercent()));
            } else {
                sb.append(String.format("%s - %.2f\n", mi.getName(), mi.getPrice()));
                subtotal += mi.getPrice();
            }
        }
        sb.append(String.format("Subtotal: %.2f\n", subtotal));
        double total = calculateTotal();
        sb.append(String.format("Total setelah diskon: %.2f\n", total));
        sb.append("========================\n");
        return sb.toString();
    }

    public void saveReceiptToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(buildReceiptString());
        }
    }
}
