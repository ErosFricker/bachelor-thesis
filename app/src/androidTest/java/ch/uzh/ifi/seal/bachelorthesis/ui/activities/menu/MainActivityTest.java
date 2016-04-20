package ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu;

import android.app.Instrumentation;
import android.preference.PreferenceActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.model.PreferenceManager;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mainActivity = null;
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Test
    public void testOnCreate() throws Exception {

        mainActivity = getActivity();
        assertTrue(mainActivity.getCarousel().getCarouselAdapter().getCount() == 4);
        assertTrue(((TextView) mainActivity.getCarousel().getCarouselAdapter().getView(0).findViewById(com.reconinstruments.ui.R.id.title)).getText().toString().equals("My Issues"));
        assertTrue(((TextView) mainActivity.getCarousel().getCarouselAdapter().getView(1).findViewById(com.reconinstruments.ui.R.id.title)).getText().toString().equals("My Calendar"));
        assertTrue(((TextView) mainActivity.getCarousel().getCarouselAdapter().getView(2).findViewById(com.reconinstruments.ui.R.id.title)).getText().toString().equals("Scan Developers"));
        assertTrue(((TextView) mainActivity.getCarousel().getCarouselAdapter().getView(3).findViewById(com.reconinstruments.ui.R.id.title)).getText().toString().equals("Settings"));
        mainActivity.finish();
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(PreferenceActivity.class.getName(), null, false);
        getInstrumentation().addMonitor(monitor);
        mainActivity = getActivity();
        getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
        mainActivity.finish();
        PreferenceManager manager = PreferenceManager.getInstance(mainActivity.getApplicationContext());
        manager.savePassword("test");
        manager.saveServerURL("http://macaw.ifi.uzh.ch/bugzilla");
        manager.saveUserName("erosfricker@gmail.com");
        getInstrumentation().addMonitor(monitor);
        mainActivity = getActivity();
        getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
        getInstrumentation().checkMonitorHit(monitor, 1);
        getInstrumentation().removeMonitor(monitor);




    }
}