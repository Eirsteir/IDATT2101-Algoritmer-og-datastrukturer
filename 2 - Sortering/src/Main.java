import java.util.Arrays;
import java.util.LinkedList;

/**
 * Øving 2 - Sortering
 */
public class Main {

    public static final int MAX = 10000;
    public static final int MIN = 0;

    // For enkelthetens og plassens skyld inkluderes det
    // i innleveringen kun kjøringer for én n-verdi
    public static void main(String[] args) {
        int n = 100000000;

        sjekkQuicksort(n);
        System.out.println();
        sjekkDualQuickSort(n);
    }

    private static void sjekkQuicksort(int n) {
        int[] tabell = genererSortertTabell(n);
        int sumNotSorted = SorteringsHjelpere.sumAv(tabell);

        // Tidsmåling
        long startTid = System.nanoTime();
        Quicksort.quicksort(tabell, 0, n - 1);
        long sluttTid = System.nanoTime();
        long varighet = (sluttTid - startTid) / 1000000 ;
        System.out.println("Kjøringstid i millisekunder: " + varighet);

        System.out.println("Quicksort m/ett delingstall, n=" + n + ":");
        int sumSorted = SorteringsHjelpere.sumAv(tabell);
        System.out.println("Har lik sum før og etter sortering: " + (sumNotSorted == sumSorted));
        System.out.println("Er i riktig rekkefølge: " + SorteringsHjelpere.sjekkRekkefølgenTil(tabell));
    }

    private static void sjekkDualQuickSort(int n) {
        int[] tabell = genererSortertTabell(n);
        int sumNotSorted = SorteringsHjelpere.sumAv(tabell);

        // Tidsmåling
        long startTid = System.nanoTime();
        DualPivotQuicksort.dualPivotQuickSort(tabell, 0, n - 1);
        long sluttTid = System.nanoTime();
        long varighet = (sluttTid - startTid) / 1000000 ;
        System.out.println("Kjøringstid i millisekunder: " + varighet);

        System.out.println("Quicksort m/ett delingstall, n=" + n + ":");
        int sumSorted = SorteringsHjelpere.sumAv(tabell);
        System.out.println("Har lik sum før og etter sortering: " + (sumNotSorted == sumSorted));
        System.out.println("Er i riktig rekkefølge: " + SorteringsHjelpere.sjekkRekkefølgenTil(tabell));
    }


    private static int[] genererTilfeldigTabell(int n) {
        int[] tabell = new int[n];
        while (n-- > 0)
            tabell[n] = (int)(Math.random() * ((MAX - MIN) + 1)) + MIN;

        return tabell;
    }

    private static int[] genererTabellMedDuplikater(int n, int x, int y) {
        int[] tabell = new int[n];
        while (n-- > 0) {
            if (n % 2 == 0)
                tabell[n] = x;
            else
                tabell[n] = y;
        }

        return tabell;
    }

    private static int[] genererSortertTabell(int n) {
        int[] tabell = new int[n];
        for (int i = 0; i < tabell.length; i++)
            tabell[i] = i;

        return tabell;
    }

    private static void printTabell(int[] tabellQuicksort) {
        Arrays.stream(tabellQuicksort)
                .forEach(i -> System.out.print(i + " "));
    }

}
