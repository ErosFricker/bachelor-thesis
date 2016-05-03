package ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu;

import android.app.Instrumentation;
import android.preference.PreferenceActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class MainMenuActivityTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {

    public MainMenuActivityTest() {
        super(MainMenuActivity.class);
    }

    @Test
    public void testOnCreate() throws Exception {

        MainMenuActivity mainMenuActivity = getActivity();
        assertTrue(mainMenuActivity.getCarousel().getCarouselAdapter().getCount() == 4);
        assertTrue(((TextView) mainMenuActivity.getCarousel().getCarouselAdapter().getView(0).findViewById(com.reconinstruments.ui.R.id.title)).getText().toString().equals("My Issues"));
        assertTrue(((TextView) mainMenuActivity.getCarousel().getCarouselAdapter().getView(1).findViewById(com.reconinstruments.ui.R.id.title)).getText().toString().equals("My Calendar"));
        assertTrue(((TextView) mainMenuActivity.getCarousel().getCarouselAdapter().getView(2).findViewById(com.reconinstruments.ui.R.id.title)).getText().toString().equals("Scan Developers"));
        assertTrue(((TextView) mainMenuActivity.getCarousel().getCarouselAdapter().getView(3).findViewById(com.reconinstruments.ui.R.id.title)).getText().toString().equals("Settings"));
        mainMenuActivity.finish();
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(PreferenceActivity.class.getName(), null, false);
        getInstrumentation().addMonitor(monitor);
        mainMenuActivity = getActivity();
        getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
        mainMenuActivity.finish();
        PreferencesFacade manager = PreferencesFacade.getInstance(mainMenuActivity.getApplicationContext());
        manager.savePassword("test");
        manager.saveServerURL("http://macaw.ifi.uzh.ch/bugzilla");
        manager.saveUserName("erosfricker@gmail.com");
        getInstrumentation().addMonitor(monitor);
        mainMenuActivity = getActivity();
        getInstrumentation().waitForMonitorWithTimeout(monitor, 1000);
        getInstrumentation().checkMonitorHit(monitor, 1);
        getInstrumentation().removeMonitor(monitor);




    }
}
