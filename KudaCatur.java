import java.util.*;

public class KudaCatur {
    private static final int UKURAN = 8;
    private static final int[] gerakBaris = {-2, -1, 1, 2, 2, 1, -1, -2}, gerakKolom = {1, 2, 2, 1, -1, -2, -2, -1};
    private static int[][] papan = new int[UKURAN][UKURAN];
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner masukan = new Scanner(System.in);
        System.out.println("\n==============================\n SELAMAT DATANG DI TUR KUDA!\n==============================\n");
        int x, y;
        while (true) {
            System.out.print("Masukkan baris awal (1 - 8): "); x = masukan.nextInt() - 1;
            System.out.print("Masukkan kolom awal (1 - 8): "); y = masukan.nextInt() - 1;
            if (x >= 0 && x < UKURAN && y >= 0 && y < UKURAN) break;
            System.out.println("Posisi tidak valid. Silakan coba lagi.\n");
        }

        for (int[] baris : papan) Arrays.fill(baris, 0);
        papan[x][y] = 1;
        if (jalankanTur(x, y)) {
            System.out.println("\n--- Papan Akhir ---");
            tampilkanPapan();
            System.out.println("\nSelamat! Kuda berhasil menjelajahi seluruh papan!");
        } else System.out.println("Tour gagal. Kuda terjebak sebelum mencapai 64 langkah.");
    }

    private static boolean jalankanTur(int x, int y) {
        for (int langkah = 2; langkah <= 64; langkah++) {
            List<int[]> kandidat = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                int nx = x + gerakBaris[i], ny = y + gerakKolom[i];
                if (isValid(nx, ny)) kandidat.add(new int[]{nx, ny, countDegree(nx, ny)});
            }
            if (kandidat.isEmpty()) return false;
            kandidat.sort(Comparator.comparingInt(a -> a[2]));
            int min = kandidat.get(0)[2];
            List<int[]> terbaik = kandidat.stream().filter(k -> k[2] == min).toList();
            int[] next = terbaik.get(random.nextInt(terbaik.size()));
            x = next[0]; y = next[1]; papan[x][y] = langkah;
            System.out.println("Langkah ke-" + langkah + ": (" + x + ", " + y + ")"); 
            tampilkanPapan(); delay(300);
            System.out.println();
        }
        return true;
    }

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < UKURAN && y >= 0 && y < UKURAN && papan[x][y] == 0;
    }

    private static int countDegree(int x, int y) {
        int count = 0;
        for (int i = 0; i < 8; i++) if (isValid(x + gerakBaris[i], y + gerakKolom[i])) count++;
        return count;
    }

    private static void tampilkanPapan() {
        for (int[] baris : papan) {
            for (int isi : baris)
                System.out.print(isi == 0 ? "  -  " : String.format("%3d  ", isi));
            System.out.println();
        }
    }

    private static void delay(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
