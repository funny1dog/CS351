import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Factorizer implements Runnable {

    private List<Long> factorsOf(long number) {
        List<Long> output = new ArrayList<>();

        // Add Math.ceil(Math.sqrt(number)) to show speed up
        for (long i = 1; i <= number; i++) {
            if (number % i == 0) {
                output.add(i);
                number /= i;
            }
        }
        return output;
    }

    private boolean isPrime(long number) {
        List<Long> factors =  factorsOf(number);
        //System.out.println("factors of " + number + " = " + factors);
        return factors.size() == 2;
    }

    @Override
    public void run() {
        long largeNumber =
                ThreadLocalRandom.current().nextLong(
                        9_223_372L,
                        9_223_372_03L);

        boolean prime = isPrime(largeNumber);

        System.out.println(largeNumber + " is prime? " + prime);
    }
}
