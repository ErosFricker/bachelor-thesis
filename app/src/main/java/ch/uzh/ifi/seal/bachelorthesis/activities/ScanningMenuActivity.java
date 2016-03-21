package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import ch.uzh.ifi.seal.bachelorthesis.R;
import com.reconinstruments.ui.carousel.CarouselActivity;

public class ScanningMenuActivity extends CarouselActivity {

    public static final String EXTRA_BUGRESULT = "bugresult";
    public static final String EXTRA_DEVELOPER_NAME = "developername";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.carousel_host);
        getCarousel().setPageMargin(30);
        String developerName = getIntent().getStringExtra(EXTRA_DEVELOPER_NAME);
        getCarousel().setContents(new MainMenuItem(developerName + "'s Issues", R.mipmap.bug, 0),
                new MainMenuItem(developerName+"'s Calendar", R.mipmap.scan, 1));
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
}
