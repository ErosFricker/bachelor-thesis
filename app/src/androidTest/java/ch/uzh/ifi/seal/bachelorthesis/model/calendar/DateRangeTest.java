package ch.uzh.ifi.seal.bachelorthesis.model.calendar;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static junit.framework.Assert.assertTrue;

/**
 * Created by efric on 04.05.2016.
 */
public class DateRangeTest {

    @Test
    public void testGetComparator() {
        Comparator<DateRange> comparator = DateRange.getComparator();
        List<DateRange> dateRangeList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            long random = rand.nextLong();
            dateRangeList.add(new DateRange(new DateTime().plus(random), new DateTime().plus(random)));
        }
        Collections.sort(dateRangeList, comparator);
        for (int i = 0; i < dateRangeList.size()-1; i++) {
            boolean startBefore = dateRangeList.get(i).getStart().isBefore(dateRangeList.get(i+1).getStart());
            boolean endBefore = dateRangeList.get(i).getEnd().isBefore(dateRangeList.get(i+1).getEnd());
            assertTrue(startBefore || endBefore);
        }

    }
}
