package ch.uzh.ifi.seal.bachelorthesis.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Created by erosfricker on 23.02.16.
 */
public class BugArrayAdapter extends ArrayAdapter<Bug> {

    private final Context mContext;
    private final Bug[] mBugs;

    public BugArrayAdapter(Context context, Bug[] bugs) {
        super(context, -1, bugs);
        this.mBugs = bugs;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.mBugs.length;
    }

    @Override
    public Bug getItem(int position) {
        return this.mBugs[position];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.bug_list_row, parent, false);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ONCLICK", "Item "+position+" clicked!");
            }
        });
        Bug currentBug = mBugs[position];
        TextView titleView = (TextView)rowView.findViewById(R.id.issue_title);
        titleView.setText(currentBug.getSummary());

        TextView statusView = (TextView)rowView.findViewById(R.id.issue_status);
        statusView.setText(currentBug.getStatus());

        return rowView;
    }
}
