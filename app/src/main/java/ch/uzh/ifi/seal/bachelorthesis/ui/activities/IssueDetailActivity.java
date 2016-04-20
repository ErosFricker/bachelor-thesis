package ch.uzh.ifi.seal.bachelorthesis.ui.activities;

import android.os.Bundle;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.issue.Issue;
import ch.uzh.ifi.seal.bachelorthesis.model.issue.IssueDetailInformation;
import ch.uzh.ifi.seal.bachelorthesis.model.InformationTuple;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.BugDetailItem;

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

    static final String EXTRA_SELECTED_BUG = "selectedbug";
    private final IssueDetailInformation issueDetailInformation = new IssueDetailInformation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);
        Issue currentIssue = (Issue) getIntent().getSerializableExtra(EXTRA_SELECTED_BUG);
        extractInformation(currentIssue);
        List<SimpleListItem> listItems = new ArrayList<>();
        for(InformationTuple informationTuple : issueDetailInformation.getContents()) {
            listItems.add(new BugDetailItem(informationTuple.getTitle().toString(), informationTuple.getDescription().toString()));
        }
        setAdapter(createAdapter(listItems));
    }

    private void extractInformation(Issue currentIssue) {
        //TODO: Refactor this for better use of design patterns (not maintainable like this)
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

        this.issueDetailInformation.insertInformation("Title", currentIssue.getSummary());
        this.issueDetailInformation.insertInformation("Description", currentIssue.getDescription());
        this.issueDetailInformation.insertInformation("Status", currentIssue.getStatus());
        this.issueDetailInformation.insertInformation("Product", currentIssue.getProduct());
        this.issueDetailInformation.insertInformation("Creator", currentIssue.getCreator());
        this.issueDetailInformation.insertInformation("Priority", currentIssue.getPriority());
        String creationDate = currentIssue.getCreation_time() == null ? "N/A" : dateFormat.format(currentIssue.getCreation_time());
        this.issueDetailInformation.insertInformation("Created On", creationDate);
        this.issueDetailInformation.insertInformation("Severity", currentIssue.getSeverity());
        this.issueDetailInformation.insertInformation("Component", currentIssue.getComponent());
        String deadline = currentIssue.getDeadline() == null ? "N/A" : dateFormat.format(currentIssue.getDeadline());
        this.issueDetailInformation.insertInformation("Deadline", deadline);
        String lastChangedTime = currentIssue.getLast_change_time() == null ? "N/A" : dateFormat.format(currentIssue.getLast_change_time());
        this.issueDetailInformation.insertInformation("Last Changed", lastChangedTime);

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
