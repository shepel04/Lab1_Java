import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        int numThreads = 3;
        int sequenceLength = 10000;
        int step = 1;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        SequenceSum.setSequence(sequenceLength, step);

        int[] resultArray = new int[numThreads];

        int batchSize = sequenceLength / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * batchSize;
            int endIndex = (i == numThreads - 1) ? sequenceLength : (i + 1) * batchSize;
            executorService.execute(new SequenceSum(startIndex, endIndex, resultArray));
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int totalSum = 0;
        for (int value : resultArray) {
            totalSum += value;
        }

        System.out.println("Total Sum = " + totalSum);
        System.out.println("All threads have completed their work.");
    }
}