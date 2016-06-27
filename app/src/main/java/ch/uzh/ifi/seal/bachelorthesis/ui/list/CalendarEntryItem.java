package ch.uzh.ifi.seal.bachelorthesis.ui.list;

import android.view.View;
import android.widget.TextView;

import com.reconinstruments.ui.list.SimpleListItem;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.calendar.DateRange;

/**
 * Created by efric on 03.05.2016.
 */
public class CalendarEntryItem extends SimpleListItem {

    final DateRange range;
    private final String summary;

    public CalendarEntryItem(DateRange range, String summary) {
        this.range = range;
        this.summary = summary;
    }

    @Override
    public int getLayoutId() {
        return R.layout.calendar_row_entry;
    }

    @Override
    public void updateView(View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.entry_title);
        if (this.range != null) {
            DateTimeFormatter timeFormat = DateTimeFormat.forPattern("HH:mm");
            String timeText = timeFormat.print(range.getStart()) + " - " + timeFormat.print(range.getEnd());
            if (summary != null) {
                titleTextView.setText(summary);
                TextView timeTextView = (TextView) view.findViewById(R.id.time_from_to);
                timeTextView.setText(timeText);

            } else {
                titleTextView.setText(timeText);

            }

        } else {
            titleTextView.setText(R.string.entry_all_day);
        }
    }
}