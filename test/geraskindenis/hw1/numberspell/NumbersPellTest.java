package geraskindenis.hw1.numberspell;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class NumbersPellTest {

    @Test
    public void numberPell() {
        String pellNumbers = "0,1,2,5,12,29,70,169,408,985,2378,5741,13860,33461,80782,195025,470832,1136689,2744210,"
                + "6625109,15994428,38613965,93222358,225058681,543339720,1311738121,3166815962,7645370045,18457556052,"
                + "44560482149,107578520350";
        long[] expecteds = Arrays.stream(pellNumbers.split(",")).map(String::trim).mapToLong(Long::parseLong).toArray();

        for (int i = 0; i < expecteds.length; i++) {
            Assert.assertEquals(expecteds[i], NumbersPell.numberPell(i));
        }
    }
}