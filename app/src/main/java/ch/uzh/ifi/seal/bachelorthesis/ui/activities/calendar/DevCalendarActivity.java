package ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.reconinstruments.ui.list.SimpleArrayAdapter;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.calendar.DateRange;
import ch.uzh.ifi.seal.bachelorthesis.rest.CalendarAsyncDelegate;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetCalendarAsyncTask;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.Item;

public class DevCalendarActivity extends SimpleListActivity implements CalendarAsyncDelegate {

    private final HashMap<Integer, List<Appointment>> userAppointmentMap = new HashMap<>();
    private final HashMap<Integer, List<Appointment>> sharedAppointmentMap = new HashMap<>();
    public static final String EXTRA_USER_EMAIL = "developer-name";
    private ProgressBar progressBar;



    class CalendarTitleItem extends SimpleListItem {
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

    class CalendarEntryItem extends SimpleListItem {

        final DateRange range;

        public CalendarEntryItem(DateRange range) {
            this.range = range;
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
                titleTextView.setText(timeText);

            }
            else {
                titleTextView.setText(R.string.entry_all_day);
            }
        }
    }

    static {
        System.setProperty("android.org.apache.commons.logging.Log",
                "android.org.apache.commons.logging.impl.SimpleLog");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dev_calendar);

        this.progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        GetCalendarAsyncTask task = new GetCalendarAsyncTask(this, this);
        String developerName = getIntent().getStringExtra(EXTRA_USER_EMAIL);
        task.execute(developerName);
    }

    private SimpleArrayAdapter<SimpleListItem> createAdapter() {
        try {
            final List<SimpleListItem> items = calculateAvailableAppointments();

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


        }catch (ServiceLocalException e) {
            e.printStackTrace();
        }
        return new SimpleArrayAdapter<SimpleListItem>(this, new ArrayList<SimpleListItem>(0)) {
            @Override
            public int getViewTypeCount() {
                return 2; //We only have one View Type (custom ones)
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

        };

    }

    private List<SimpleListItem> calculateAvailableAppointments() throws ServiceLocalException {
        List<SimpleListItem> items = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        Calendar end = (Calendar)start.clone();
        end.add(Calendar.DAY_OF_YEAR, 7);

        for (; start.before(end); start.add(Calendar.DATE, 1)) {
            Integer currentDay = start.get(Calendar.DAY_OF_YEAR);
            if (!userAppointmentMap.containsKey(currentDay)) {
                if(!sharedAppointmentMap.containsKey(currentDay)) { //Both do not have any appointment on that day
                    items.add(new CalendarTitleItem(new DateTime(start.getTime())));
                    items.add(new CalendarEntryItem(null));
                }else { //The scanned dev does have one or more appointments on that day, but the user not
                    List<Appointment> sharedAppointments = sharedAppointmentMap.get(currentDay);
                    List<DateRange> possibleDates = getPossibleDates(sharedAppointments);
                    List<SimpleListItem> currentItems = getListItemsFromPossibleDates(possibleDates);
                    for (SimpleListItem i : currentItems) {
                        items.add(i);
                    }
                }
            }else {
                if (!sharedAppointmentMap.containsKey(currentDay)) { //The user has one or more appointments on that day, but the scanned dev does not
                    List<Appointment> userAppointments = userAppointmentMap.get(currentDay);
                    List<DateRange> possibleDates = getPossibleDates(userAppointments);
                    List<SimpleListItem> currentItems = getListItemsFromPossibleDates(possibleDates);
                    for (SimpleListItem i : currentItems) {
                        items.add(i);
                    }
                }else { //both have one or more appointments on that day
                    List<Appointment> sharedAppointments = sharedAppointmentMap.get(currentDay);
                    List<Appointment> userAppointments = userAppointmentMap.get(currentDay);
                    List<DateRange> possibleUserDates = getPossibleDates(userAppointments);
                    List<DateRange> possibleSharedDates = getPossibleDates(sharedAppointments);
                    List<DateRange> possibleCommonDates = getPossibleDates(possibleUserDates, possibleSharedDates);
                    List<SimpleListItem> currentItems = getListItemsFromPossibleDates(possibleCommonDates);
                    for (SimpleListItem i : currentItems) {
                        items.add(i);
                    }
                }
            }

        }
        return items;

    }

    private List<DateRange> getPossibleDates(List<DateRange> possibleUserDates, List<DateRange> possibleSharedDates) {
        Collections.sort(possibleUserDates, DateRange.getComparator());
        Collections.sort(possibleSharedDates, DateRange.getComparator());
        List<DateRange> possibleDates = new ArrayList<>();

        Integer i = 0;
        Integer j = 0;

        while (i < possibleUserDates.size() && j < possibleSharedDates.size()) {

            DateRange currentUserDate = possibleUserDates.get(i);
            DateRange currentSharedDate = possibleSharedDates.get(j);
            DateTime startDate;
            DateTime endDate;
            DateRange a;
            DateRange b;

            if (isBefore(currentUserDate.getStart(), currentSharedDate.getStart())){
                a = currentUserDate;
                b = currentSharedDate;

                if (isBefore(a.getEnd(), b.getStart())) {
                    i = i+1;
                }else if (isAfter(a.getEnd(), b.getStart())) {
                    startDate = b.getStart();
                    endDate = a.getEnd();
                    possibleDates.add(new DateRange(startDate, endDate));
                    i = i+1;
                }else if (isBefore(b.getEnd(), a.getEnd())) {
                    startDate = b.getStart();
                    endDate = b.getEnd();
                    j = j+1;
                    possibleDates.add(new DateRange(startDate, endDate));
                }else if (isEqual(a.getEnd(), b.getEnd())) {
                    startDate = b.getStart();
                    endDate = a.getEnd();
                    possibleDates.add(new DateRange(startDate, endDate));
                    i = i+1;
                    j = j+1;
                } else if (isEqual(a.getEnd(), b.getStart())) {
                    i = i+1;
                }

            }else if (isAfter(currentUserDate.getStart(), currentSharedDate.getStart())){
                a = currentSharedDate;
                b = currentUserDate;
                if (isBefore(a.getEnd(), b.getStart())) {
                    j = j+1;
                }else if (isAfter(a.getEnd(), b.getStart())) {
                    startDate = b.getStart();
                    endDate = a.getEnd();
                    possibleDates.add(new DateRange(startDate, endDate));
                    j = j+1;
                }else if (isBefore(b.getEnd(), a.getEnd())) {
                    startDate = b.getStart();
                    endDate = b.getEnd();
                    i = i+1;
                    possibleDates.add(new DateRange(startDate, endDate));
                }else if (isEqual(a.getEnd(), b.getEnd())) {
                    startDate = b.getStart();
                    endDate = a.getEnd();
                    possibleDates.add(new DateRange(startDate, endDate));
                    i = i+1;
                    j = j+1;
                } else if (isEqual(a.getEnd(), b.getStart())) {
                    j = j+1;
                }

            }else if (isEqual(currentUserDate.getStart(), currentSharedDate.getStart())) {
                if (isBefore(currentUserDate.getEnd(), currentSharedDate.getEnd())) {
                    startDate = currentUserDate.getStart();
                    endDate = currentUserDate.getEnd();
                    possibleDates.add(new DateRange(startDate, endDate));
                    i++;
                }else if (isEqual(currentUserDate.getEnd(), currentSharedDate.getEnd())){
                    startDate = currentUserDate.getStart();
                    endDate = currentSharedDate.getEnd();
                    possibleDates.add(new DateRange(startDate, endDate));
                    i++;
                    j++;
                } else if (isAfter(currentUserDate.getEnd(), currentSharedDate.getEnd())) {
                    startDate = currentUserDate.getStart();
                    endDate = currentSharedDate.getEnd();
                    possibleDates.add(new DateRange(startDate, endDate));
                    j++;
                }
            }


        }
        return possibleDates;

    }


    private boolean isBefore(DateTime date1, DateTime date2) {
        return date1.compareTo(date2) == -1;
    }
    private boolean isAfter(DateTime date1, DateTime date2) {
        return date1.compareTo(date2) == 1;
    }
    private boolean isEqual(DateTime date1, DateTime date2) {
        return date1.compareTo(date2) == 0;
    }
    private List<SimpleListItem> getListItemsFromPossibleDates(List<DateRange> possibleDates) {
        List<SimpleListItem> listItems = new ArrayList<>();
        if (possibleDates.size()!= 0) {
            listItems.add(new CalendarTitleItem(possibleDates.get(0).getStart()));
            for (int i = 0; i < possibleDates.size() - 1; i++) {
                listItems.add(new CalendarEntryItem(possibleDates.get(i)));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(possibleDates.get(i).getStart().toDate());
                int dayOfCurrent = calendar.get(Calendar.DAY_OF_YEAR);

                calendar.setTime(possibleDates.get(i + 1).getStart().toDate());
                int dayOfNext = calendar.get(Calendar.DAY_OF_YEAR);

                if (dayOfNext - dayOfCurrent != 0) {
                    listItems.add(new CalendarTitleItem(possibleDates.get(i + 1).getStart()));
                }
            }
            if (possibleDates.size() > 1) {
                listItems.add(new CalendarEntryItem(possibleDates.get(possibleDates.size() - 1)));
            }
        }
        return listItems;
    }

    private List<DateRange> getPossibleDates(List<Appointment> appointments) throws ServiceLocalException {
        List<DateRange> possibleDates = new ArrayList<>();
        Collections.sort(appointments, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment lhs, Appointment rhs) {
                try {
                    int comparison = lhs.getStart().compareTo(rhs.getStart());
                    if (comparison == 0) {
                        return lhs.getEnd().compareTo(rhs.getEnd());
                    }
                    return comparison;

                } catch (ServiceLocalException e) {
                    e.printStackTrace();
                }
                return -1;
            }
        });
        for (Appointment a :  appointments) { //If there is an all-day event, return an empty list
            if (a.getIsAllDayEvent()){
                possibleDates = new ArrayList<>();
                return possibleDates;
            }
        }

        //add the time from 00:00 until the first appointments
        Appointment currentAppointment = appointments.get(0);
        Calendar possibleCal = Calendar.getInstance();
        possibleCal.setTime(currentAppointment.getStart());
        possibleCal.set(Calendar.HOUR_OF_DAY, 0);
        possibleCal.set(Calendar.MINUTE, 0);
        possibleCal.set(Calendar.SECOND, 0);
        if (possibleCal.getTime().compareTo(currentAppointment.getStart()) != 0) {
            possibleDates.add(new DateRange(new DateTime(possibleCal.getTime()), new DateTime(currentAppointment.getStart())));
        }

        //Add the time between appointments
        for (int i = 0; i < appointments.size()-1; i++) {
            Appointment startAppointment = appointments.get(i);
            Appointment endAppointment = appointments.get(i+1);
            if (startAppointment.getEnd().compareTo(endAppointment.getStart()) == -1) {
                possibleDates.add(new DateRange(new DateTime(startAppointment.getEnd()), new DateTime(endAppointment.getStart())));
            }

        }
        //Add the time between the last appointments and 23:59
        currentAppointment = appointments.get(appointments.size() - 1);
        possibleCal= Calendar.getInstance();
        possibleCal.setTime(currentAppointment.getEnd());
        possibleCal.set(Calendar.HOUR_OF_DAY, 23);
        possibleCal.set(Calendar.MINUTE, 59);
        possibleCal.set(Calendar.SECOND, 59);

        possibleDates.add(new DateRange(new DateTime(currentAppointment.getEnd()), new DateTime(possibleCal.getTime())));

        return possibleDates;
    }

    @Override
    public void onPostExecuteFinished(ArrayList<ArrayList<Item>> appointments) {
        try {
            fillAppointmentMaps(appointments);
            Log.d("", "USER APPOINTMENTS =========");
            for (List<Appointment> l : userAppointmentMap.values()){
                for (Appointment a : l) {
                    Log.d("", a.getStart().toString() + " - "+a.getEnd().toString());
                }
            }
            Log.d("", "SHARED APPOINTMENTS =========");
            for (List<Appointment> l : sharedAppointmentMap.values()){
                for (Appointment a : l) {
                    Log.d("", a.getStart().toString() + " - "+a.getEnd().toString());
                }
            }

        } catch (ServiceLocalException e) {
            e.printStackTrace();
        }

        setAdapter(createAdapter());
        getAdapter().notifyDataSetChanged();
    }

    private void fillAppointmentMaps(ArrayList<ArrayList<Item>> appointments) throws ServiceLocalException {
        for (Item i : appointments.get(0)) {
            Appointment appointment = (Appointment)i;
            addToMap(userAppointmentMap, appointment);
        }
        for (Item i : appointments.get(1)) {
            Appointment appointment = (Appointment)i;
            addToMap(sharedAppointmentMap, appointment);
        }
    }

    private void addToMap(HashMap<Integer, List<Appointment>> appointmentMap, Appointment appointment) throws ServiceLocalException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(appointment.getStart());
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        if (appointmentMap.containsKey(dayOfYear)) {
            appointmentMap.get(dayOfYear).add(appointment);
        }else {
            ArrayList<Appointment> list = new ArrayList<>();
            list.add(appointment);
            appointmentMap.put(dayOfYear, list);
        }

    }

    @Override
    public void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {
        this.progressBar.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.GONE);

    }
}

