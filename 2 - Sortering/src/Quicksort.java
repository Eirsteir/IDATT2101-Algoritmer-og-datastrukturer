public class Quicksort {

    public static void quicksort(int[] t, int v, int h) {
        if (h - v > 2) {
            int delepos = split(t, v, h);
            quicksort(t, v, delepos - 1);
            quicksort(t, delepos + 1, h);
        } else {
            median3Sort(t, v, h);
        }
    }

    private static int split(int[] t, int v, int h) {
        int iv, ih;
        int m = median3Sort(t, v, h);
        int dv = t[m];
        bytt(t, m, h - 1);

        for (iv = v, ih = h - 1;;) {
            while (t[++iv] < dv);
            while (t[--ih] > dv);
            if (iv >= ih)
                break;
            bytt(t, iv, ih);
        }
        bytt(t, iv, h - 1);
        return iv;
    }

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
