import java.io.*;
import java.util.*;
//arkhean is here

public class Main {
	private static final String MENU_FILE = "menu.txt";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Menu menu = new Menu();
		try { menu.loadFromFile(MENU_FILE); } catch (IOException e) { /* ignore */ }

		Pesanan currentPesanan = null;

		while (true) {
			System.out.println("\n" + ConsoleColors.BLUE + "=== SISTEM MANAJEMEN RESTORAN ===" + ConsoleColors.RESET);
			System.out.println(ConsoleColors.GREEN + "1. Tambah item ke menu" + ConsoleColors.RESET);
			System.out.println(ConsoleColors.GREEN + "2. Tampilkan menu restoran" + ConsoleColors.RESET);
			System.out.println(ConsoleColors.GREEN + "3. Simpan menu ke file" + ConsoleColors.RESET);
			System.out.println(ConsoleColors.GREEN + "4. Muat menu dari file" + ConsoleColors.RESET);
			System.out.println(ConsoleColors.GREEN + "5. Terima pesanan pelanggan" + ConsoleColors.RESET);
			System.out.println(ConsoleColors.GREEN + "6. Tampilkan dan simpan struk pesanan" + ConsoleColors.RESET);
			System.out.println(ConsoleColors.GREEN + "7. Keluar" + ConsoleColors.RESET);
			System.out.print(ConsoleColors.YELLOW + "Pilih opsi: " + ConsoleColors.RESET);
			String op = sc.nextLine().trim();

			try {
				switch (op) {
					case "1":
						tambahItem(menu, sc);
						break;
					case "2":
						menu.listMenu();
						break;
					case "3":
						menu.saveToFile(MENU_FILE);
						System.out.println(ConsoleColors.GREEN + "Menu disimpan ke " + MENU_FILE + ConsoleColors.RESET);
						break;
					case "4":
						menu.loadFromFile(MENU_FILE);
						System.out.println(ConsoleColors.GREEN + "Menu dimuat dari " + MENU_FILE + ConsoleColors.RESET);
						break;
					case "5":
						currentPesanan = terimaPesanan(menu, sc);
						break;
					case "6":
						if (currentPesanan == null) {
							System.out.println(ConsoleColors.RED + "Belum ada pesanan. Buat pesanan terlebih dahulu." + ConsoleColors.RESET);
						} else {
								System.out.println(ConsoleColors.YELLOW + currentPesanan.buildReceiptString() + ConsoleColors.RESET);
							String fname = "struk_" + System.currentTimeMillis() + ".txt";
							currentPesanan.saveReceiptToFile(fname);
								System.out.println(ConsoleColors.GREEN + "Struk disimpan ke " + fname + ConsoleColors.RESET);
						}
						break;
					case "7":
						System.out.println(ConsoleColors.BLUE + "Keluar. Sampai jumpa." + ConsoleColors.RESET);
						sc.close();
						return;
					default:
						System.out.println(ConsoleColors.RED + "Opsi tidak dikenal." + ConsoleColors.RESET);
				}
			} catch (ItemNotFoundException infe) {
				System.out.println(ConsoleColors.RED + "Kesalahan: " + infe.getMessage() + ConsoleColors.RESET);
			} catch (IOException ioe) {
				System.out.println(ConsoleColors.RED + "I/O error: " + ioe.getMessage() + ConsoleColors.RESET);
			} catch (Exception e) {
				System.out.println(ConsoleColors.RED + "Error: " + e.getMessage() + ConsoleColors.RESET);
			}
		}
	}

	private static void tambahItem(Menu menu, Scanner sc) {
		System.out.print("Jenis item (makanan/minuman/diskon): ");
		String jenis = sc.nextLine().trim().toLowerCase();
		System.out.print("Nama: ");
		String nama = sc.nextLine().trim();
		String kategori = "Umum";
		System.out.print("Kategori (tekan enter untuk default 'Umum'): ");
		String catInput = sc.nextLine().trim();
		if (!catInput.isEmpty()) kategori = catInput;

		if (jenis.equals("makanan")) {
			System.out.print("Jenis Makanan: ");
			String jenisMakanan = sc.nextLine().trim();
			double price = readDouble(sc, "Harga: ");
			menu.addItem(new Makanan(nama, price, kategori, jenisMakanan));
		} else if (jenis.equals("minuman")) {
			System.out.print("Jenis Minuman: ");
			String jenisMinuman = sc.nextLine().trim();
			double price = readDouble(sc, "Harga: ");
			menu.addItem(new Minuman(nama, price, kategori, jenisMinuman));
		} else if (jenis.equals("diskon")) {
			double pct = readDouble(sc, "Persen diskon (contoh 10 untuk 10%): ");
			menu.addItem(new Diskon(nama, pct, kategori));
		} else {
			System.out.println("Jenis tidak dikenali.");
			return;
		}
		System.out.println("Item berhasil ditambahkan ke menu.");
	}

	private static double readDouble(Scanner sc, String prompt) {
		while (true) {
			System.out.print(prompt);
			String s = sc.nextLine().trim();
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {
				System.out.println("Masukkan angka yang valid.");
			}
		}
	}

	private static Pesanan terimaPesanan(Menu menu, Scanner sc) throws ItemNotFoundException {
		Pesanan pesanan = new Pesanan();
		System.out.println("Masukkan nomor item dari menu. Ketik 0 untuk selesai.");
		while (true) {
			menu.listMenu();
			System.out.print("Pilih nomor item (0 selesai): ");
			String in = sc.nextLine().trim();
			int idx = -1;
			try {
				idx = Integer.parseInt(in);
			} catch (NumberFormatException e) {
				System.out.println("Masukkan nomor yang valid.");
				continue;
			}
			if (idx == 0) break;
			MenuItem mi = menu.getItem(idx - 1);
			pesanan.addItem(mi);
			System.out.println(mi.getName() + " ditambahkan ke pesanan.");
		}
		return pesanan;
	}
}

