package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.view.View;
import android.widget.TextView;

import com.reconinstruments.ui.list.StandardListItem;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Created by Eros Fricker on 04/05/16.
 */
class SortMenuListItem extends StandardListItem {
    private String subtitle;

    public SortMenuListItem(String text) {
        super(text);
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        TextView subtitleView = (TextView) getView().findViewById(R.id.subtitle);
        subtitleView.setVisibility(View.VISIBLE);
        subtitleView.setText(subtitle);
    }

    public String getSubtitle() {
        return subtitle;
    }
}
