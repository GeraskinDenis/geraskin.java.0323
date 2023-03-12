package geraskindenis.hw2.statsaccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int count = 0;
    private int sum = 0;
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;
    private Double avg = 0d;

    @Override
    public void add(int value) {
        count++;
        sum += value;
        min = Integer.min(min, value);
        max = Integer.max(max, value);
        avg = (double) sum / (double) count;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        return avg;
    }
}
