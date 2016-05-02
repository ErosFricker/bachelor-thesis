package ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.reconinstruments.ui.list.SimpleArrayAdapter;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.rest.CalendarAsyncDelegate;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetCalendarAsyncTask;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.Item;

public class MyCalendarActivity extends SimpleListActivity implements CalendarAsyncDelegate {


    class CalendarTitleItem extends SimpleListItem {
        TextView titleTextView;
        TextView dateTextView;
        Date date;


        public CalendarTitleItem(Date date) {
            this.date = date;
        }

        @Override
        public int getLayoutId() {
            return R.layout.calendar_row_title;
        }

        @Override
        public void updateView(View view) {
            dateTextView = (TextView)view.findViewById(R.id.date_title);
            DateFormat dateFormat = SimpleDateFormat.getDateInstance();
            dateTextView.setText(dateFormat.format(date));
        }
    }

    class CalendarEntryItem extends SimpleListItem {

        Appointment appointment;

        public CalendarEntryItem(Appointment appointment) {
            this.appointment = appointment;
        }

        @Override
        public int getLayoutId() {
            return R.layout.calendar_row_entry;
        }

        @Override
        public void updateView(View view) {
            TextView titleTextView = (TextView)view.findViewById(R.id.entry_title);
            TextView timeTextView = (TextView)view.findViewById(R.id.time_from_to);
            DateFormat timeFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT, Locale.GERMANY);
            try {
                titleTextView.setText(appointment.getSubject());
                String timeText = timeFormat.format(appointment.getStart()) + " - " + timeFormat.format(appointment.getEnd());
                timeTextView.setText(timeText);

            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Item> items = new ArrayList<Item>();
    static {
        System.setProperty("android.org.apache.commons.logging.Log",
                "android.org.apache.commons.logging.impl.SimpleLog");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        GetCalendarAsyncTask task = new GetCalendarAsyncTask(this, this);
        String exchangeUsername =PreferencesFacade.getInstance(this).getExchangeUser();
        task.execute(exchangeUsername);
    }

    private SimpleArrayAdapter<SimpleListItem> createAdapter(List<Item> appointments) {

        final List<SimpleListItem> items = new ArrayList<>();
        Calendar lastCal = Calendar.getInstance();
        lastCal.setTime(new Date(0));
        DateFormat dateformat = SimpleDateFormat.getDateInstance();
        for (Item a : appointments) {
            Appointment appointment = (Appointment)a;
            try {
                Calendar appointmentCal = Calendar.getInstance();
                appointmentCal.setTime(appointment.getStart());
                if (appointmentCal.get(Calendar.DAY_OF_YEAR) == lastCal.get(Calendar.DAY_OF_YEAR) && appointmentCal.get(Calendar.YEAR) == lastCal.get(Calendar.YEAR)) {
                    items.add(new CalendarEntryItem(appointment));
                }else {
                    items.add(new CalendarTitleItem(appointment.getStart()));
                    items.add(new CalendarEntryItem(appointment));
                    lastCal.setTime(appointment.getStart());
                }
            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }
        }

        return new SimpleArrayAdapter<SimpleListItem>(this, items) {
            @Override
            public int getViewTypeCount() {
                return 2; //We only have one View Type (custom ones)
            }

            @Override
            public int getItemViewType(int position) {
                if (items.get(position) instanceof CalendarEntryItem) {
                    return 1;
                }else {
                    return 0;
                }
            }


        };
    }

    @Override
    public void onPostExecuteFinished(ArrayList<ArrayList<Item>> appointments) {
        setAdapter(createAdapter(appointments.get(0)));
        getAdapter().notifyDataSetChanged();
        getListView().setEmptyView(null);
    }
}
