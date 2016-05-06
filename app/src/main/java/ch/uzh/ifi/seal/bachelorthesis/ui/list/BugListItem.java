package ch.uzh.ifi.seal.bachelorthesis.ui.list;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.reconinstruments.ui.list.SimpleListItem;

import java.text.DateFormat;
import java.util.Date;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.issue.IssueStatus;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.issues.IssueDetailActivity;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.issues.IssuesActivity;

/**
 * Custom List Item for displaying Bugs
 */
public class BugListItem extends SimpleListItem {

    private final String title;
    private final IssueStatus status;
    private final Date lastChangeTime;
    private final IssuesActivity activity;

    public BugListItem(String title, IssueStatus status, Date lastChangeTime, IssuesActivity activity) {
        this.title = title;
        this.status = status;
        this.lastChangeTime = lastChangeTime;
        this.activity = activity;
    }

    @Override
    public int getLayoutId() {
        return R.layout.issue_list_row;
    }

    @Override
    public void onClick(Context context) {
        Intent showDetailIntent = new Intent(context, IssueDetailActivity.class);
        int position = activity.getAdapter().getPosition(this);
        showDetailIntent.putExtra(IssueDetailActivity.EXTRA_SELECTED_BUG, activity.getIssueList().get(position));
        context.startActivity(showDetailIntent);

    }

    @Override
    public void updateView(View view) {
        TextView titleView = (TextView)view.findViewById(R.id.issue_title);
        titleView.setText(this.title);

        TextView changeDateView = (TextView)view.findViewById(R.id.issue_change_date);
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        changeDateView.setText(dateFormat.format(this.lastChangeTime));

        ImageView imageView = (ImageView)view.findViewById(R.id.issue_icon);
        switch (this.status) {
            case CONFIRMED:
                imageView.setImageDrawable(activity.getResources().getDrawable(R.mipmap.ic_issue_confirmed));
                break;
            case IN_PROGRESS:
                imageView.setImageDrawable(activity.getResources().getDrawable(R.mipmap.ic_issue_in_progress));
                break;
            case RESOLVED:
                imageView.setImageDrawable(activity.getResources().getDrawable(R.mipmap.ic_issue_resolved));
                break;
        }


    }
}
