package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.app.Activity;
import android.os.AsyncTask;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.property.complex.Mailbox;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

/**
 * Created by Eros Fricker on 04/27/16.
 */
public class GetCalendarAsyncTask extends AsyncTask<String,Void, ArrayList<ArrayList<Item>>> {

    private final Activity activity;
    private final CalendarAsyncDelegate asyncDelegate;
    private final ExchangeService service;

    public GetCalendarAsyncTask(Activity activity, CalendarAsyncDelegate asyncDelegate) {
        this.activity = activity;
        this.asyncDelegate = asyncDelegate;
        this.service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        final PreferencesFacade preferencesFacade = PreferencesFacade.getInstance(this.activity);
        String exchangeURL = preferencesFacade.getExchangeURL();
        String username = preferencesFacade.getExchangeUser();
        String password = preferencesFacade.getExchangePassword();
        ExchangeCredentials credentials = new WebCredentials(username, password);
        this.service.setCredentials(credentials);
        try {
            this.service.setUrl(new URI(exchangeURL));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ArrayList<ArrayList<Item>> doInBackground(String... params) {
        this.asyncDelegate.showProgressBar();
        return getCalendar(params[0]);
    }

    /**
     * Thread-safe access to get the calendar events
     * @param username The username to get the events from
     * @return A list of event-lists (one for the user and eventually one for the shared calendar events)
     */
    private synchronized ArrayList<ArrayList<Item>> getCalendar(String username) {
        ArrayList<ArrayList<Item>> result = new ArrayList<>();
        ArrayList<Item> userAppointments;
        ArrayList<Item> sharedAppointments;
        Date startDate = new Date();
        Date endDate = (Date)startDate.clone();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, 7);
        endDate.setTime(calendar.getTime().getTime());

        if (username.equals(PreferencesFacade.getInstance(activity).getExchangeUser())) {
            userAppointments = getUserCalendar(startDate, endDate);
            result.add(userAppointments);
        }else {
            userAppointments = getUserCalendar(startDate, endDate);
            result.add(userAppointments);
            sharedAppointments = getSharedCalendar(startDate, endDate, username);
            result.add(sharedAppointments);
        }
        return result;
    }

    /**
     * Gets the events from the App user (address stored in {@link android.content.SharedPreferences}
     * @param startDate The start date to get the events
     * @param endDate The end date to get the events
     * @return The events from the App user between startDate and (including) endDate
     */
    private ArrayList<Item> getUserCalendar(Date startDate, Date endDate) {
        ArrayList<Item> appointments = new ArrayList<>();
        CalendarFolder cf;
        try {
            cf = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
            FindItemsResults<Appointment> findResults = cf.findAppointments(new CalendarView(startDate, endDate));
            for (Appointment appt : findResults.getItems()) {
                appointments.add(appt);
            }
            if(appointments.size() > 0) {
                service.loadPropertiesForItems(appointments, PropertySet.FirstClassProperties);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Accesses the events from the shared calendar from Microsoft Exchange Server
     * @param startDate The start date to get the events
     * @param endDate The end date to get the events
     * @param eMail The email address of the user to get the events
     * @return The events in the range from startDate to endDate from the user with the eMail address
     */
    private ArrayList<Item> getSharedCalendar(Date startDate, Date endDate, String eMail) {
        ArrayList<Item> appointments = new ArrayList<>();
        CalendarFolder cf;
        try {
            cf = CalendarFolder.bind(service, new FolderId(WellKnownFolderName.Calendar, new Mailbox(eMail)));
            FindItemsResults<Appointment> findResults = cf.findAppointments(new CalendarView(startDate, endDate));
            for (Appointment appt : findResults.getItems()) {
                appointments.add(appt);
            }
            if (appointments.size() > 0) {
                service.loadPropertiesForItems(appointments, PropertySet.FirstClassProperties);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }


    @Override
    protected void onPostExecute(ArrayList<ArrayList<Item>> appointments) {
        super.onPostExecute(appointments);
        this.asyncDelegate.hideProgressBar();
        asyncDelegate.onPostExecuteFinished(appointments);
    }

}
