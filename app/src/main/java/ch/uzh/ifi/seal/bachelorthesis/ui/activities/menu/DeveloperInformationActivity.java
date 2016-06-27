package ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu;

import android.os.Bundle;
import android.widget.Toast;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.DeveloperInformationMenuItem;

/**
 * Activity for the menu view, after a developer has been scanned.
 * <p/>
 * Created by Eros Fricker on 25/04/16.
 */
public class DeveloperInformationActivity extends MenuMovementActivity {

    public static final String EXTRA_DEVELOPER_NAME = "developername";
    public static final String EXTRA_DEVELOPER_EMAIL = "email";
    private String developerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_host);
        getCarousel().setPageMargin(30);
        this.developerName = getIntent().getStringExtra(EXTRA_DEVELOPER_NAME);
        String developerEmail = getIntent().getStringExtra(EXTRA_DEVELOPER_EMAIL);
        String developerFirstName = this.developerName.substring(0, this.developerName.indexOf(" "));
        getCarousel().setContents(new DeveloperInformationMenuItem(developerFirstName + "'s Issues", R.mipmap.bug, 0, developerEmail),
                new DeveloperInformationMenuItem(getString(R.string.find_meeting_time), R.mipmap.calendar, 1, developerEmail));
        notifyDeveloperFound();
    }

    /**
     * Notifies the user, that a developer has been found by showing a Toast with the developer's name
     */
    private void notifyDeveloperFound() {
        Toast.makeText(getApplicationContext(), "Found developer " + this.developerName, Toast.LENGTH_SHORT).show();
    }
}
