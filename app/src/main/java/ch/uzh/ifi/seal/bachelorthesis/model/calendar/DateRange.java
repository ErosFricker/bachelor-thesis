package ch.uzh.ifi.seal.bachelorthesis.model.calendar;

import org.joda.time.DateTime;

import java.util.Comparator;

/**
 * Custom class representing date ranges for the calendar event functionality.
 * Created by Eros Fricker on 04/28/16.
 */
public class DateRange {
    private final DateTime start;
    private final DateTime end;

    public DateRange(DateTime start, DateTime end) {
        if (start.isBefore(end)) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }

    }

    /**
     * Creates a custom comparator for the {@link DateRange} class.
     *
     * @return The custom comparator
     */
    public static Comparator<DateRange> getComparator() {
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

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }
}