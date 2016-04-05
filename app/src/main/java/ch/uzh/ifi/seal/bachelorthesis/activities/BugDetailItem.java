package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.reconinstruments.ui.list.SimpleListItem;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Custom List Item for displaying Bugs
 */
class BugDetailItem extends SimpleListItem {

    private final String title;
    private final String description;

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
