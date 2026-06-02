public class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String name, double price, String category, String jenisMakanan) {
        super(name, price, category);
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    public void setJenisMakanan(String jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.printf(ConsoleColors.GREEN + "Makanan: %s" + ConsoleColors.RESET + " | Harga: " + ConsoleColors.YELLOW + "%.2f" + ConsoleColors.RESET + " | Kategori: %s | Jenis: %s\n",
            getName(), getPrice(), getCategory(), jenisMakanan);
    }

    @Override
    public String toDataString() {
        return String.format("Makanan|%s|%f|%s|%s", getName(), getPrice(), getCategory(), jenisMakanan);
    }
}
