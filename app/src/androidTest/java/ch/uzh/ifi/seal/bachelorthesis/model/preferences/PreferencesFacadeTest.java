package ch.uzh.ifi.seal.bachelorthesis.model.preferences;

import android.test.ActivityInstrumentationTestCase2;

import ch.uzh.ifi.seal.bachelorthesis.ui.activities.preferences.PreferencesActivity;

public class PreferencesFacadeTest extends ActivityInstrumentationTestCase2<PreferencesActivity> {

    PreferencesFacade preferencesFacade;

    public PreferencesFacadeTest() {
        super(PreferencesActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        preferencesFacade = PreferencesFacade.getInstance(getActivity());

    }

    public void testSaveUserName() throws Exception {
        preferencesFacade.saveUserName("test");
        assertTrue(preferencesFacade.getUsername().equals("test"));

    }

    public void testSavePassword() throws Exception {
        preferencesFacade.savePassword("test");
        assertTrue(preferencesFacade.getPassword().equals("test"));

    }

    public void testSaveServerURL() throws Exception {
        preferencesFacade.saveServerURL("url");
        assertTrue(preferencesFacade.getServerURL().equals("url"));
    }

    public void testSaveExchangeURL() throws Exception {
        preferencesFacade.saveExchangeURL("url");
        assertTrue(preferencesFacade.getExchangeURL().equals("url"));

    }

    public void testSaveExchangeUser() throws Exception {
        preferencesFacade.saveExchangeUser("user");
        assertTrue(preferencesFacade.getExchangeUser().equals("user"));
    }

    public void testSaveExchangePassword() throws Exception {
        preferencesFacade.saveExchangePassword("pw");
        assertTrue(preferencesFacade.getExchangePassword().equals("pw"));

    }

    public void testArePreferencesFilled() throws Exception {
        preferencesFacade.saveExchangePassword("test");
        preferencesFacade.saveUserName("test");
        preferencesFacade.saveExchangeUser("test");
        preferencesFacade.savePassword("test");
        preferencesFacade.saveExchangeURL("url");
        preferencesFacade.saveServerURL("url");
        assertTrue(preferencesFacade.arePreferencesFilled());

    }
}