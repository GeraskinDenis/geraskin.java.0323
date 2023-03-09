package geraskindenis.hw1.numberspell;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class NumbersPellTest {
    private final int argument;
    private final long expected;

    public NumbersPellTest(long argument, long expected) {
        this.argument = (int) argument;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: Pell number of {0} = {1}")
    public static Iterable<Object[]> dataForTest() {
        String strPellNumbers = "0,1,2,5,12,29,70,169,408,985,2378,5741,13860,33461,80782,195025,470832,1136689,2744210,"
                + "6625109,15994428,38613965,93222358,225058681,543339720,1311738121,3166815962,7645370045,18457556052,"
                + "44560482149,107578520350";

        long[] pelleNumbers = Arrays.stream(strPellNumbers.split(","))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .toArray();

        List<Object[]> dataForTest = new ArrayList<>(pelleNumbers.length);
        for (int i = 0; i < pelleNumbers.length; i++) {
            dataForTest.add(new Object[]{(long) i, pelleNumbers[i]});
        }
        return dataForTest;
    }

    @Test
    public void numberPell() {
        Assert.assertEquals(expected, NumbersPell.numberPell(argument));
    }
}