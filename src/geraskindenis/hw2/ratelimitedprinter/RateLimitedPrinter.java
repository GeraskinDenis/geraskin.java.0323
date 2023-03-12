package geraskindenis.hw2.ratelimitedprinter;

public class RateLimitedPrinter {
    private final int interval;
    private long lastPrint = 0;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
    }

    public void print(String message) {
        if (lastPrint + interval < System.currentTimeMillis()) {
            System.out.println(message);
            lastPrint = System.currentTimeMillis();
        }
    }
}