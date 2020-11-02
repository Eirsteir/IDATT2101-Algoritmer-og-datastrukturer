import java.util.function.BiFunction;

public class Exponents {

    // 2.1-1: O(n)
    public static double power1(double base, int n) {
        if (n == 0)
            return 1;
        else if (n < 0)
            return 0;

        return base * power1(base, n - 1);
    }

    // 2.2-3: O(log n)
    public static double power2(double base, int n) {
        if (n == 0)
            return 1;
        else if (n % 2 == 1)
            return base * power2(base * base, (n - 1) / 2);

        return power2(base * base, n/2);
    }

    public static double javaPow(double base, int n) {
        return Math.pow(base, n);
    }

    public static void time(BiFunction<Double, Integer, Double> algorithm, double base, int n) {
        long start = System.nanoTime();
        int runs = 0;
        int seconds = 1;
        double secondsInNano = 1000000000.0;
        double tid;
        long end;

        do {
            algorithm.apply(base, n);
            end = System.nanoTime();
            ++runs;
        } while (end - start < secondsInNano * seconds);

        tid = (double) ((end - start)) / runs;
        System.out.println("Nanoseconds per round:" + tid);
    }

    public static void main(String[] args) {
        System.out.println("Method 1: ");
        System.out.println("2^10 = " + power1(2, 10));
        System.out.println("3^14 = " + power1(3, 14));

        time(Exponents::power1, 2, 10);
        time(Exponents::power1, 2, 100);
        time(Exponents::power1, 2, 1000);
        time(Exponents::power1, 2, 10000);

        System.out.println("Method 2: ");
        System.out.println("2^10 = " + power2(2, 10));
        System.out.println("3^14 = " + power2(3, 14));

        time(Exponents::power2, 2, 10);
        time(Exponents::power2, 2, 100);
        time(Exponents::power2, 2, 1000);
        time(Exponents::power2, 2, 10000);

        System.out.println("Native method: ");
        System.out.println("2^10 = " + javaPow(2, 10));
        System.out.println("3^14 = " + javaPow(3, 14));

        time(Exponents::javaPow, 2, 10);
        time(Exponents::javaPow, 2, 100);
        time(Exponents::javaPow, 2, 1000);
        time(Exponents::javaPow, 2, 10000);
    }

}