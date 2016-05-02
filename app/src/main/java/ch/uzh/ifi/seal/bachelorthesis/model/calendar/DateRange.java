package ch.uzh.ifi.seal.bachelorthesis.model.calendar;

import org.joda.time.DateTime;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Eros Fricker on 04/28/16.
 */
public class DateRange {
    DateTime start;
    DateTime end;

    public DateRange(DateTime start, DateTime end) {
        this.start = start;
        this.end = end;
    }

    public DateTime getStart() {
        return start;
    }


    public DateTime getEnd() {
        return end;
    }


    public static Comparator<DateRange> getComparator(){
        return new Comparator<DateRange>() {
            @Override
            public int compare(DateRange lhs, DateRange rhs) {
                int comparison = lhs.getStart().compareTo(rhs.getStart());
                if (comparison == 0) {
                    return lhs.getEnd().compareTo(rhs.getEnd());
                } else {
                    return comparison;
                }
            }
        };
    }
}