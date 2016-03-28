package ch.uzh.ifi.seal.bachelorthesis.activities.menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import ch.uzh.ifi.seal.bachelorthesis.R;
import com.reconinstruments.ui.carousel.CarouselActivity;

public class ScanMenuActivity extends CarouselActivity {

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
        getCarousel().setContents(new ScanMenuItem(developerFirstName + "'s Issues", R.mipmap.bug, 0, developerEmail),
                new ScanMenuItem(developerFirstName+"'s Calendar", R.mipmap.calendar, 1, developerEmail));
        notifyDeveloperFound();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanning_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void notifyDeveloperFound() {
        Toast.makeText(getApplicationContext(), "Found developer "+this.developerName, Toast.LENGTH_SHORT).show();
    }
}
