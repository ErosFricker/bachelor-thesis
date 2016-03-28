package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.Bug;
import ch.uzh.ifi.seal.bachelorthesis.model.BugDetailInformation;
import ch.uzh.ifi.seal.bachelorthesis.model.InformationTuple;
import com.reconinstruments.ui.list.SimpleArrayAdapter;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity class for displaying bug details selected in {@link IssuesActivity}
 */
public class IssueDetailActivity extends SimpleListActivity {
    private Bug currentBug;
    static final String EXTRA_SELECTED_BUG = "selectedbug";
    private BugDetailInformation bugDetailInformation = new BugDetailInformation();

    /**
     * Custom List Item for displaying Bugs
     */
    class BugDetailItem extends SimpleListItem {

        private String title;
        private String description;

        BugDetailItem(String title, String description) {
            this.title = title;
            this.description = description;
        }

        @Override
        public int getLayoutId() {
            return R.layout.issue_detail_list_row;
        }

        @Override
        public void onClick(Context context) {

        }

        @Override
        public void updateView(View view) {
            TextView titleView = (TextView)view.findViewById(R.id.detail_title);
            TextView detailView = (TextView)view.findViewById(R.id.detail_text);
            titleView.setText(this.title);
            detailView.setText(this.description);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);
        currentBug = (Bug)getIntent().getSerializableExtra(EXTRA_SELECTED_BUG);
        extractInformation(currentBug);
        List<SimpleListItem> listItems = new ArrayList<>();
        for(InformationTuple informationTuple : bugDetailInformation.getContents()) {
            listItems.add(new BugDetailItem(informationTuple.getTitle().toString(), informationTuple.getDescription().toString()));
        }
        setAdapter(createAdapter(listItems));
    }

    private void extractInformation(Bug currentBug) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");

        this.bugDetailInformation.insertInformation("Title", currentBug.getSummary());
        this.bugDetailInformation.insertInformation("Description", currentBug.getDescription());
        this.bugDetailInformation.insertInformation("Status", currentBug.getStatus());
        this.bugDetailInformation.insertInformation("Product", currentBug.getProduct());
        this.bugDetailInformation.insertInformation("Creator", currentBug.getCreator());
        this.bugDetailInformation.insertInformation("Priority", currentBug.getPriority());
        String creationDate = currentBug.getCreation_time() == null ? "N/A" : dateFormat.format(currentBug.getCreation_time());
        this.bugDetailInformation.insertInformation("Created On", creationDate);
        this.bugDetailInformation.insertInformation("Severity", currentBug.getSeverity());
        this.bugDetailInformation.insertInformation("Component", currentBug.getComponent());
        String deadline = currentBug.getDeadline() == null ? "N/A" : dateFormat.format(currentBug.getDeadline());
        this.bugDetailInformation.insertInformation("Deadline", deadline);
        String lastChangedTime = currentBug.getLast_change_time() == null ? "N/A" : dateFormat.format(currentBug.getLast_change_time());
        this.bugDetailInformation.insertInformation("Last Changed", lastChangedTime);

    }

    private SimpleArrayAdapter<SimpleListItem> createAdapter(List<SimpleListItem> contents) {
        return new SimpleArrayAdapter<SimpleListItem>(this, contents) {
            @Override
            public int getViewTypeCount() {
                return 1; //We only have one View Type (custom ones)
            }

            @Override
            public int getItemViewType(int position) {
                return 2; //Custom Item ViewType
            }


        };
    }
}
