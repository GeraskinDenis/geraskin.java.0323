package io.ylab.intensive.lesson02.statsaccumulator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

@RunWith(Parameterized.class)
public class StatsAccumulatorImplTest {
    private final StatsAccumulatorImpl sa = new StatsAccumulatorImpl();
    private final int[] ints;
    private final Map<String, Number> expecteds;

    public StatsAccumulatorImplTest(int[] ints, Map<String, Number> expecteds) {
        this.ints = ints;
        this.expecteds = expecteds;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> dataForTest() {
        List<Object[]> dataForTest = new ArrayList<>();

        // Case for test 1
        {
            int[] ints = {1, 3, 123, -23};

            Map<String, Number> e = new HashMap<>();
            e.put("min", -23);
            e.put("max", 123);
            e.put("count", ints.length);
            double avg = getAvg(ints);
            e.put("avg", avg);

            dataForTest.add(new Object[]{ints, e});
        }

        // Case for test 2
        {
            int[] ints = {77, -35, 888, -3, 56, 7};

            Map<String, Number> e = new HashMap<>();
            e.put("min", -35);
            e.put("max", 888);
            e.put("count", ints.length);
            double avg = getAvg(ints);
            e.put("avg", avg);

            dataForTest.add(new Object[]{ints, e});
        }

        return dataForTest;
    }

    @Before
    public void setUp() {
        for (int i : ints) {
            sa.add(i);
        }
    }

    @Test
    public void getMin() {
        Assert.assertEquals(expecteds.get("min"), sa.getMin());
    }

    @Test
    public void getMax() {
        Assert.assertEquals(expecteds.get("max"), sa.getMax());
    }

    @Test
    public void getCount() {
        Assert.assertEquals(expecteds.get("count"), sa.getCount());
    }

    @Test
    public void getAvg() {
        Assert.assertEquals(expecteds.get("avg"), sa.getAvg());
    }

    private static double getAvg(int[] ints) {
        return (double) Arrays.stream(ints).sum() / (double) ints.length;
    }
}