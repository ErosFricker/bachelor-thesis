package ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.uzh.ifi.seal.bachelorthesis.R;
import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.autodiscover.exception.AutodiscoverLocalException;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.exception.service.local.ServiceLocalException;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

public class CalendarActivity extends Activity {

    private List<Item> items = new ArrayList<Item>();
    static {
        System.setProperty("android.org.apache.commons.logging.Log",
                "android.org.apache.commons.logging.impl.SimpleLog");
    }
//TODO: Implement Calendar Event viewing functionality
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                sendMail();

            }
        });
        thread.start();

    }

    private synchronized void sendMail() {
            ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);

            ExchangeCredentials credentials = new WebCredentials("erosfricker@erosfricker.onmicrosoft.com", "?");
            service.setCredentials(credentials);
            try {
                service.setUrl(new URI("https://outlook.office365.com/EWS/Exchange.asmx"));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = formatter.parse("2016-04-01 12:00:00");
                Date endDate = formatter.parse("2016-05-30 13:00:00");
                CalendarFolder cf= CalendarFolder.bind(service, WellKnownFolderName.Calendar);
                FindItemsResults<Appointment> findResults = cf.findAppointments(new CalendarView(startDate, endDate));
                for (Appointment appt : findResults.getItems()) {
                    items.add(appt);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ServiceLocalException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        try {
            service.loadPropertiesForItems(items, PropertySet.FirstClassProperties);
            for (Item app : items) {
                Log.d("EVENT", "=====SUBJECT" + app.getSubject());
                Log.d("EVENT", "=====BODY" + app.getBody());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
