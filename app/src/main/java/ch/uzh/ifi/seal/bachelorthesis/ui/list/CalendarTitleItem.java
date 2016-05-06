package ch.uzh.ifi.seal.bachelorthesis.ui.list;

import android.view.View;
import android.widget.TextView;

import com.reconinstruments.ui.list.SimpleListItem;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Created by efric on 03.05.2016.
 */
public class CalendarTitleItem extends SimpleListItem {
    TextView dateTextView;
    final DateTime date;


    public CalendarTitleItem(DateTime date) {
        this.date = date;
    }

    @Override
    public int getLayoutId() {
        return R.layout.calendar_row_title;
    }

    @Override
    public void updateView(View view) {
        dateTextView = (TextView)view.findViewById(R.id.date_title);
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd. MMM yyyy");
        dateTextView.setText(dateFormat.print(date));
    }
}