public class QuicksortForklart {

    public static void quicksort(int[] t, int v, int h) {
        if (h - v > 2) { // sjekk om deltabellen er stor nok til å deles videre
            int delepos = split(t, v, h);  // stokker om tallene og finner delepunkt
            // kaller rekursivt på de to deltabellene
            quicksort(t, v, delepos - 1);
            quicksort(t, delepos + 1, h);
        } else {
            median3Sort(t, v, h);
        }
    }

    private static int split(int[] t, int v, int h) {
        int iv, ih;
        int m = median3Sort(t, v, h); // finner indeks til medianen
        int dv = t[m]; // henter tallverdien til medianen
        bytt(t, m, h - 1); // setter medianen/pivot i enden

        for (iv = v, ih = h - 1;;) { // går gjennom fra hver sin ende
            while (t[++iv] < dv); // gå fremover fra venstre til tallet er mindre enn medianen
            while (t[--ih] > dv); // gå bakover fra høyre til tallet er større enn medianen
            if (iv >= ih)  // de møtes eller går forbi hverandre
                break;
            bytt(t, iv, ih); // ikke møttes enda, bytt så venstre del får små tall og høyre store
        }
        bytt(t, iv, h - 1); // bytter det første av de store tallene med delingstallet lagret på h-1
        return iv;
    }

    // Finner pivot i midten ved å sortere tallet i midten,
    // det første og siste og velge det i midten
    // Gjetter at dette er (indeks til) medianen av hele tabellen
    private static int median3Sort(int[] t, int v, int h) {
        int m = (v + h) / 2;
        if (t[v] > t[m])
            bytt(t, v, m);
        if (t[m] > t[h]) {
            bytt(t, m, h);
            if (t[v] > t[m])
                bytt(t, v, m);
        }

        return m;
    }

    private static void bytt(int[] t, int i, int j) {
        int k = t[j];
        t[j] = t[i];
        t[i] = k;
    }
}
