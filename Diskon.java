public class Diskon extends MenuItem {
    private double diskonPercent;

    public Diskon(String name, double diskonPercent, String category) {
        super(name, 0.0, category);
        this.diskonPercent = diskonPercent;
    }

    public double getDiskonPercent() {
        return diskonPercent;
    }

    public void setDiskonPercent(double diskonPercent) {
        this.diskonPercent = diskonPercent;
    }

    @Override
    public void tampilMenu() {
        System.out.printf(ConsoleColors.PURPLE + "Diskon: %s" + ConsoleColors.RESET + " | Potongan: " + ConsoleColors.RED + "%.2f%%" + ConsoleColors.RESET + " | Kategori: %s\n",
            getName(), diskonPercent, getCategory());
    }

    @Override
    public String toDataString() {
        return String.format("Diskon|%s|%f|%s|%f", getName(), 0.0, getCategory(), diskonPercent);
    }
}
