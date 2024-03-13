
class SequenceSum implements Runnable {
    private static int nextThreadId = 1;
    private static final Object lock = new Object();

    private static int[] sequence;
    private static int sequenceLength;
    private static int step;

    private int threadId;
    private int startIndex;
    private int endIndex;
    private int[] resultArray;

    public SequenceSum(int startIndex, int endIndex, int[] resultArray) {
        synchronized (lock) {
            this.threadId = nextThreadId++;
        }
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.resultArray = resultArray;
    }

    public static void setSequence(int length, int step) {
        sequenceLength = length;
        SequenceSum.step = step;

        sequence = new int[sequenceLength];
        for (int i = 0; i < sequenceLength; i++) {
            sequence[i] = i * step;
        }
    }

    @Override
    public void run() {
        int localSum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            localSum += sequence[i];
        }

        synchronized (lock) {
            resultArray[threadId - 1] = localSum;
        }

        System.out.println("Thread " + threadId + ": Local Sum = " + localSum + ", Elements Count = " + (endIndex - startIndex));
    }
}