package ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

import com.journeyapps.barcodescanner.CaptureActivity;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu.ScanMenuActivity;
import ch.uzh.ifi.seal.bachelorthesis.model.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetUserTask;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class ScanDeveloperActivityTest extends ActivityInstrumentationTestCase2<ScanDeveloperActivity>{


    public ScanDeveloperActivityTest() {
        super(ScanDeveloperActivity.class);
    }

    @Test
    public void testOnCreate() throws Exception {
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(CaptureActivity.class.getName(), null, false);
        getInstrumentation().addMonitor(monitor);
        ScanDeveloperActivity activity = getActivity();
        getInstrumentation().waitForMonitor(monitor);

    }


    @Test
    public void testGetDeveloperName() throws Exception {
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(ScanMenuActivity.class.getName(), null, false);
        getInstrumentation().addMonitor(monitor);
        ScanDeveloperActivity activity = getActivity();
        PreferencesFacade.getInstance(activity.getApplicationContext()).saveUserName("erosfricker@gmail.com");
        PreferencesFacade.getInstance(activity.getApplicationContext()).saveServerURL("http://macaw.ifi.uzh.ch/bugzilla");
        activity.loadDeveloperName("erosfricker@gmail.com");
        getInstrumentation().waitForMonitor(monitor);
    }



    @Test
    public void testOnPostExecuteFinished() throws Exception {
        GetUserTask task = new GetUserTask(getActivity().getApplicationContext(), "erosfricker@gmail.com", "http://macaw.ifi.uzh.ch/bugzilla");
        Instrumentation.ActivityMonitor monitor = new Instrumentation.ActivityMonitor(ScanMenuActivity.class.getName(), null, false);
        getInstrumentation().addMonitor(monitor);
        ScanDeveloperActivity activity = getActivity();
        String result = "{\n" +
                "   \"users\" : [\n" +
                "      {\n" +
                "         \"id\" : 2,\n" +
                "         \"name\" : \"erosfricker@gmail.com\",\n" +
                "         \"real_name\" : \"Eros Fricker\"\n" +
                "      }\n" +
                "   ]\n" +
                "}";
        activity.onPostExecuteFinished(result, task);
        getInstrumentation().waitForMonitor(monitor);

    }
}