public class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String name, double price, String category, String jenisMinuman) {
        super(name, price, category);
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() {
        return jenisMinuman;
    }

    public void setJenisMinuman(String jenisMinuman) {
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.printf(ConsoleColors.CYAN + "Minuman: %s" + ConsoleColors.RESET + " | Harga: " + ConsoleColors.YELLOW + "%.2f" + ConsoleColors.RESET + " | Kategori: %s | Jenis: %s\n",
            getName(), getPrice(), getCategory(), jenisMinuman);
    }

    @Override
    public String toDataString() {
        return String.format("Minuman|%s|%f|%s|%s", getName(), getPrice(), getCategory(), jenisMinuman);
    }
}
