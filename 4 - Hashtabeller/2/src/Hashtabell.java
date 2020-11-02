import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Hashtabell {

    int[] tabell;
    int antallKollisjoner;
    int storrelse;

    public Hashtabell(int[] tabell) {
        int nestePrimtall = nestePrimtall((int) (tabell.length * 1.3));
        this.tabell = new int[nestePrimtall];
        this.antallKollisjoner = 0;
        this.storrelse = 0;

        leggTilAlle(tabell);
    }

    public static int nestePrimtall(int n) {
        n++;
        for (int i = 2; i < n; i++) {
            if(n%i == 0) {
                n++;
                i=2;
            }
        }

        return n;
    }

    private void leggTilAlle(int[] tabell) {
        for (int i : tabell)
            leggInn(i);
    }

    public void leggInn(Integer nokkel) {
        int indeks = hash1(nokkel);

        if (tabell[indeks] == 0) {
            // Ingen kollisjon
            tabell[indeks] = nokkel;
        } else {
            int indeks2 = hash2(nokkel);

            for (int i = 0; i < tabell.length; i++) {
                int nyIndeks = probe(indeks, indeks2, i);

                if (tabell[nyIndeks] == 0) {
                    // Ingen kollisjon, lagre nøkkel
                    tabell[nyIndeks] = nokkel;
                    break;
                }

                antallKollisjoner++;
            }
        }

        storrelse++;
    }

    private int hash1(int k) {
        return k % 17;
    }

    private int hash2(int k) {
        return k % (tabell.length - 1) + 1;
    }

    private int probe(int h1, int h2, int i) {
        int pos = h1;
        while (i-- > 0)
            pos = (pos + h2) % tabell.length;

        return pos;
    }

    public double lastfaktor() {
        return (double) storrelse / tabell.length;
    }

    public int lengde() {
        return tabell.length;
    }
}


class Main {

    private static final int MAX = 100000000; // 100 mill
    private static final int MIN = 0;


    public static void main(String[] args) {
        int storrelse = 10000000; // 10 mill
        int[] tilfeldigTabell = genererTilfeldigTabell(storrelse);

        long start = System.nanoTime();
        Hashtabell hashtabell =  new Hashtabell(tilfeldigTabell);;
        long slutt = System.nanoTime();

        long tidForAaFylleEgen = TimeUnit.NANOSECONDS.toMillis(slutt - start);

        System.out.println("Tid å fylle egen hashtabell: " + tidForAaFylleEgen + " ms");
        System.out.println("Antall kollisjoner: " + hashtabell.antallKollisjoner);
        System.out.println("Lastfaktor: " + hashtabell.lastfaktor());
        System.out.println("Antall elementer: " + storrelse);
        System.out.println("Tabellstørrelse: " + hashtabell.lengde());

        System.out.println();

        HashMap<Integer, Integer> hashMap =  new HashMap<>();
        start = System.nanoTime();
        for (int i = 0; i < storrelse; i++)
            hashMap.put(i, tilfeldigTabell[i]);
        slutt = System.nanoTime();

        long tidForAaFylleJava  = TimeUnit.NANOSECONDS.toMillis(slutt - start);

        System.out.println("Tid å fylle Java hashtabell: " + tidForAaFylleJava  + " ms");
        System.out.println();
        System.out.println("Differanse: " + (tidForAaFylleEgen - tidForAaFylleJava) + " ms");
    }

    private static int[] genererTilfeldigTabell(int n) {
        int[] tabell = new int[n];
        while (n-- > 0)
            tabell[n] = (int)(Math.random() * ((MAX - MIN) + 1)) + MIN;

        return tabell;
    }
}