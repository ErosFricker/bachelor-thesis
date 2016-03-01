package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.Bug;

/**
 * Activity class for displaying bug details selected in {@link MyIssuesActivity}
 */
public class ShowBugDetailActivity extends AppCompatActivity {

    Bug currentBug;
    static final String SELECTED_BUG_EXTRA = "selectedbug";

    TextView titleView;
    TextView detailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bug_detail);
        currentBug = (Bug)getIntent().getSerializableExtra(SELECTED_BUG_EXTRA);
        titleView = (TextView)findViewById(R.id.title_bug_detail);

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
