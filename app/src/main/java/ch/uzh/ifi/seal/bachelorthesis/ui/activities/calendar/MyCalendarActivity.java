package ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar;

import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.reconinstruments.os.HUDOS;
import com.reconinstruments.os.metrics.HUDMetricsID;
import com.reconinstruments.os.metrics.HUDMetricsManager;
import com.reconinstruments.os.metrics.MetricsValueChangedListener;
import com.reconinstruments.ui.list.SimpleArrayAdapter;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.calendar.DateRange;
import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.rest.CalendarAsyncDelegate;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetCalendarAsyncTask;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.CalendarEntryItem;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.CalendarTitleItem;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.Item;

public class MyCalendarActivity extends SimpleListActivity implements CalendarAsyncDelegate, MetricsValueChangedListener {

    public static final float STANDING_SPEED = 1.0f;
    private HUDMetricsManager metricsManager;
    private ProgressBar progressBar;

    static {
        System.setProperty("android.org.apache.commons.logging.Log",
                "android.org.apache.commons.logging.impl.SimpleLog");
    }
    //TODO: Check if this is needed at runtime
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        this.progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        this.metricsManager = (HUDMetricsManager) HUDOS.getHUDService(HUDOS.HUD_METRICS_SERVICE);

        GetCalendarAsyncTask task = new GetCalendarAsyncTask(this, this);
        String exchangeUsername =PreferencesFacade.getInstance(this).getExchangeUser();
        task.execute(exchangeUsername);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.metricsManager.registerMetricsListener(this, HUDMetricsID.SPEED_HORIZONTAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.metricsManager.unregisterMetricsListener(this, HUDMetricsID.SPEED_HORIZONTAL);
    }

    private SimpleArrayAdapter<SimpleListItem> createAdapter(List<Item> appointments) {

        final List<SimpleListItem> items = new ArrayList<>();
        Calendar lastCal = Calendar.getInstance();
        lastCal.setTime(new Date(0));
        for (Item a : appointments) {
            Appointment appointment = (Appointment)a;
            try {
                Calendar appointmentCal = Calendar.getInstance();
                appointmentCal.setTime(appointment.getStart());
                DateTime start = new DateTime(appointment.getStart());
                DateTime end = new DateTime(appointment.getEnd());
                DateRange dateRange = new DateRange(start, end);
                if (appointmentCal.get(Calendar.DAY_OF_YEAR) == lastCal.get(Calendar.DAY_OF_YEAR) && appointmentCal.get(Calendar.YEAR) == lastCal.get(Calendar.YEAR)) {
                    items.add(new CalendarEntryItem(dateRange, appointment.getSubject()));
                }else {
                    items.add(new CalendarTitleItem(start));
                    items.add(new CalendarEntryItem(dateRange, appointment.getSubject()));
                    lastCal.setTime(appointment.getStart());
                }
            } catch (ServiceLocalException e) {
                e.printStackTrace();
            }
        }

        return new SimpleArrayAdapter<SimpleListItem>(this, items) {
            @Override
            public int getViewTypeCount() {
                return 2; //We have 2 custom view types
            }

            @Override
            public int getItemViewType(int position) {
                if (items.get(position) instanceof CalendarEntryItem) { //return the view type based on the class of the item at this position
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

    @Override
    public void onMetricsValueChanged(int id, float value, long l, boolean isValid) {
        if (value - STANDING_SPEED <= 0) {
            finish();
        }
    }

    @Override
    public void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.progressBar.setVisibility(View.GONE);

    }
}
