public class SorteringsHjelpere {

    public static int sumAv(int[] list) {
        int sum = 0;
        for (int i : list)
            sum += i;

        return sum;
    }

    public static boolean sjekkRekkefølgenTil(int[] list) {
        for (int i = 0; i < list.length - 2; i++) {
            if (list[i + 1] >= list[i])
                return true;
        }

        return false;
    }

}
