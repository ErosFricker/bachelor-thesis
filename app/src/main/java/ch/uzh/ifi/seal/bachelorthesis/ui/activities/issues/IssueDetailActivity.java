package ch.uzh.ifi.seal.bachelorthesis.ui.activities.issues;

import android.os.Bundle;

import com.reconinstruments.ui.list.SimpleArrayAdapter;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.issue.Issue;
import ch.uzh.ifi.seal.bachelorthesis.model.issue.IssueDetailInformation;
import ch.uzh.ifi.seal.bachelorthesis.model.issue.IssueInformationTuple;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.BugDetailItem;

/**
 * Activity class for displaying bug details selected in {@link IssuesActivity}
 * <p/>
 * Created by Eros Fricker on 25/04/16.
 */
public class IssueDetailActivity extends SimpleListActivity {

    public static final String EXTRA_SELECTED_BUG = "selectedBug";
    private final IssueDetailInformation issueDetailInformation = new IssueDetailInformation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);
        Issue currentIssue = (Issue) getIntent().getSerializableExtra(EXTRA_SELECTED_BUG);
        extractInformation(currentIssue);
        List<SimpleListItem> listItems = new ArrayList<>();
        for (IssueInformationTuple issueInformationTuple : issueDetailInformation.getContents()) {
            listItems.add(new BugDetailItem(issueInformationTuple.getTitle(), issueInformationTuple.getDescription()));
        }
        setAdapter(createAdapter(listItems));
    }

    /**
     * Extracts the detail information from the given issue. This is needed for the creation of the detail list view texts.
     *
     * @param currentIssue The issue to extract the information
     */
    private void extractInformation(Issue currentIssue) {

        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();

        this.issueDetailInformation.insertInformation("Title", currentIssue.getSummary());
        this.issueDetailInformation.insertInformation("Description", currentIssue.getDescription());
        this.issueDetailInformation.insertInformation("Status", currentIssue.getStatus());
        this.issueDetailInformation.insertInformation("Product", currentIssue.getProduct());
        this.issueDetailInformation.insertInformation("Creator", currentIssue.getCreator());
        this.issueDetailInformation.insertInformation("Priority", currentIssue.getPriority());
        String creationDate = dateFormat.format(currentIssue.getCreationTime());
        this.issueDetailInformation.insertInformation("Created On", creationDate);
        this.issueDetailInformation.insertInformation("Severity", currentIssue.getSeverity());
        this.issueDetailInformation.insertInformation("Component", currentIssue.getComponent());

        String deadline = "N/A";
        if (currentIssue.getDeadline() != null) {
            deadline = dateFormat.format(currentIssue.getDeadline());
        }
        this.issueDetailInformation.insertInformation("Deadline", deadline);
        String lastChangedTime = dateFormat.format(currentIssue.getLastChangeTime());
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
