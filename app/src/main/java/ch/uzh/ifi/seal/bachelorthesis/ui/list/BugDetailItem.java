package ch.uzh.ifi.seal.bachelorthesis.ui.list;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.reconinstruments.ui.list.SimpleListItem;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Custom List Item for displaying Bugs
 */
public class BugDetailItem extends SimpleListItem {

    private final String title;
    private final String description;

    public BugDetailItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public int getLayoutId() {
        return R.layout.issue_detail_list_row;
    }

    @Override
    public void onClick(Context context) {
        //Overridden to not do anything on click
    }

    @Override
    public void updateView(View view) {
        TextView titleView = (TextView) view.findViewById(R.id.detail_title);
        TextView detailView = (TextView) view.findViewById(R.id.detail_text);
        titleView.setText(this.title);
        detailView.setText(this.description);
    }
}
