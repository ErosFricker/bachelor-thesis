package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.app.Activity;
import android.os.Bundle;
import ch.uzh.ifi.seal.bachelorthesis.R;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;

public class CalendarActivity extends Activity {
//TODO: Implement Calendar Event viewing functionality
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials("e.fricker@hotmail.com", "erta2008?");
        service.setCredentials(credentials);
    }

}
