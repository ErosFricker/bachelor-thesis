package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
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

    Activity activity;
    CalendarAsyncDelegate asyncDelegate;
    ExchangeService service;

    public GetCalendarAsyncTask(Activity activity, CalendarAsyncDelegate asyncDelegate) {
        this.activity = activity;
        this.asyncDelegate = asyncDelegate;
        this.service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        String exchangeURL = PreferencesFacade.getInstance(this.activity).getExchangeURL();
        String username = PreferencesFacade.getInstance(this.activity).getExchangeUser();
        String password = PreferencesFacade.getInstance(this.activity).getExchangePassword();
        ExchangeCredentials credentials = new WebCredentials(username, password);
        this.service.setCredentials(credentials);
        try {
            this.service.setUrl(new URI(exchangeURL)); //"https://outlook.office365.com/EWS/Exchange.asmx"
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected ArrayList<ArrayList<Item>> doInBackground(String... params) {
        return getCalendar(params[0]);
    }

    private synchronized ArrayList<ArrayList<Item>> getCalendar(String username) {
        ArrayList<ArrayList<Item>> result = new ArrayList<>();
        ArrayList<Item> userAppointments = new ArrayList<Item>();
        ArrayList<Item> sharedAppointments = new ArrayList<>();
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

    private ArrayList<Item> getUserCalendar(Date startDate, Date endDate) {
        ArrayList<Item> appointments = new ArrayList<Item>();
        CalendarFolder cf= null;
        try {
            cf = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
            FindItemsResults<Appointment> findResults = cf.findAppointments(new CalendarView(startDate, endDate));
            for (Appointment appt : findResults.getItems()) {
                appointments.add(appt);
            }
            service.loadPropertiesForItems(appointments, PropertySet.FirstClassProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    private ArrayList<Item> getSharedCalendar(Date startDate, Date endDate, String eMail) {
        ArrayList<Item> appointments = new ArrayList<Item>();
        CalendarFolder cf= null;
        try {
            cf = CalendarFolder.bind(service, new FolderId(WellKnownFolderName.Calendar, new Mailbox(eMail)));
            FindItemsResults<Appointment> findResults = cf.findAppointments(new CalendarView(startDate, endDate));
            for (Appointment appt : findResults.getItems()) {
                appointments.add(appt);
            }
            service.loadPropertiesForItems(appointments, PropertySet.FirstClassProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }


    @Override
    protected void onPostExecute(ArrayList<ArrayList<Item>> appointments) {
        super.onPostExecute(appointments);
        asyncDelegate.onPostExecuteFinished(appointments);

    }

}
