package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.Bug;
import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;
import ch.uzh.ifi.seal.bachelorthesis.model.IssueStatus;
import ch.uzh.ifi.seal.bachelorthesis.model.SortType;
import ch.uzh.ifi.seal.bachelorthesis.rest.AsyncDelegate;
import ch.uzh.ifi.seal.bachelorthesis.rest.BugzillaAsyncTask;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetIssuesTask;
import ch.uzh.ifi.seal.bachelorthesis.rest.SettingsParser;
import com.google.gson.Gson;
import com.reconinstruments.ui.carousel.CarouselItem;
import com.reconinstruments.ui.carousel.StandardCarouselItem;
import com.reconinstruments.ui.dialog.CarouselDialog;
import com.reconinstruments.ui.dialog.DialogBuilder;
import com.reconinstruments.ui.list.SimpleArrayAdapter;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;
import com.reconinstruments.ui.list.StandardListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class IssuesActivity extends SimpleListActivity implements AsyncDelegate {

    private float x1;
    private float x2;
    private Bug[] bugArray;
    private List<Bug> bugList;
    public static final String EXTRA_USER_EMAIL = "useremail";
    private SortType sortSelection = SortType.ByChangeDate;
    /**
     * Custom List Item for displaying Bugs
     */
    class BugListItem extends SimpleListItem {

        private String title;
        private IssueStatus status;
        private Date lastChangeTime;

        BugListItem(String title, IssueStatus status, Date lastChangeTime) {
            this.title = title;
            this.status = status;
            this.lastChangeTime = lastChangeTime;
        }

        @Override
        public int getLayoutId() {
            return R.layout.issue_list_row;
        }

        @Override
        public void onClick(Context context) {
            Intent showDetailIntent = new Intent(context, IssueDetailActivity.class);
            int position = getAdapter().getPosition(this);
            showDetailIntent.putExtra(IssueDetailActivity.EXTRA_SELECTED_BUG, bugArray[position]);
            context.startActivity(showDetailIntent);

        }

        @Override
        public void updateView(View view) {
            TextView titleView = (TextView)view.findViewById(R.id.issue_title);
            titleView.setText(this.title);

            TextView changeDateView = (TextView)view.findViewById(R.id.issue_change_date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
            changeDateView.setText(dateFormat.format(this.lastChangeTime));

            ImageView imageView = (ImageView)view.findViewById(R.id.issue_icon);
            switch (this.status) {
                case CONFIRMED:
                    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_issue_confirmed));
                    break;
                case IN_PROGRESS:
                    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_issue_in_progress));
                    break;
                case RESOLVED:
                    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_issue_resolved));
                    break;
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        String userEmail = getIntent().getStringExtra(EXTRA_USER_EMAIL);
        fillSelections();
        GetIssuesTask task = new GetIssuesTask(userEmail, SettingsParser.getInstance(getApplicationContext()).getServerURL(), this);
        task.setAsyncDelegate(this);
        task.execute();
    }

    @Override
    public void onPostExecuteFinished(String result, BugzillaAsyncTask asyncTask) {
        Gson gson = new Gson();
        BugResult bugResult = new BugResult();
        System.out.println(result);
        try {
            bugResult = gson.fromJson(result, BugResult.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        this.bugList = bugResult.getBugs();
        bugArray = bugResult.getBugs().toArray(new Bug[bugResult.getBugs().size()]);
        refreshAdapter();
        createSelectionDialog();


    }
    public interface OnClickCallback {
        void onClick(SortMenuListItem item);
    }
    public class CheckedSelectionItem extends StandardCarouselItem {
        SortType value;
        public CheckedSelectionItem(String title,SortType value) {
            super(title);
            this.value = value;
        }

        @Override
        public void updateView(View view) {
            super.updateView(view);
            view.findViewById(R.id.checkmark).setVisibility(value==sortSelection?View.VISIBLE:View.INVISIBLE);
        }

        @Override
        public int getLayoutId() {
            return R.layout.carousel_item_checkmark;
        }

    }

    private void fillSelections(){
        for (SortType s : SortType.values()){
            this.selections.add(new CheckedSelectionItem(s.toString(), s));
        }
    }
    List<CarouselItem> selections = new ArrayList<>();

    public class SortMenuListItem extends StandardListItem {
        String subtitle;
        OnClickCallback callback;
        public SortMenuListItem(String text, OnClickCallback callback){
            super(text);
            this.callback = callback;
        }
        public void onClick(Context context) {
            callback.onClick(this);
        }
        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
            TextView subtitleView = (TextView)getView().findViewById(R.id.subtitle);
            subtitleView.setVisibility(View.VISIBLE);
            subtitleView.setText(subtitle);
        }
        public String getSubtitle() {
            return subtitle;
        }
    }

    public void createSelectionDialog() {

        DialogBuilder builder = new DialogBuilder(this).setTitle("Timeout");
        builder.createSelectionDialog(selections, sortSelection.ordinal(), new CarouselDialog.OnItemSelectedListener() {
            @Override
            public void onItemSelected(CarouselDialog dialog, CarouselItem item, int position) {
                sortSelection = SortType.values()[position];
                sortBugs();
                dialog.dismiss();
            }
        }).show();
    }

    @NonNull
    private void sortBugs() {
        //TODO: Refactor to use Strategy Pattern
        Collections.sort(bugList, new Comparator<Bug>() {
            @Override
            public int compare(Bug lhs, Bug rhs) {
                switch (sortSelection) {
                    case ByChangeDate:
                        return lhs.getLast_change_time().compareTo(rhs.getLast_change_time());
                    case ByName:
                        return lhs.getSummary().compareTo(rhs.getSummary());
                    case ByStatus:
                        return lhs.getStatus().compareTo(rhs.getSummary());
                    default:
                        return lhs.getLast_change_time().compareTo(rhs.getLast_change_time());
                }

            }
        });
        refreshAdapter();
    }

    private void refreshAdapter() {
        List<SimpleListItem> listItems = new ArrayList<>();
        for (Bug b : this.bugList) {
            IssueStatus status = getIssueStatusFromString(b.getStatus());
            listItems.add(new BugListItem(b.getSummary(), status, b.getLast_change_time()));
        }
        setAdapter(createAdapter(listItems));
        getAdapter().notifyDataSetChanged();

    }

    private IssueStatus getIssueStatusFromString(String status) {
        IssueStatus issueStatus = IssueStatus.NONE;
        if(status.equals(IssueStatus.CONFIRMED.name())) {
            issueStatus = IssueStatus.CONFIRMED;

        }else if (status.equals(IssueStatus.IN_PROGRESS.name())){
            issueStatus = IssueStatus.IN_PROGRESS;

        }else if(status.equals(IssueStatus.RESOLVED)){
            issueStatus = IssueStatus.RESOLVED;
        }
        return issueStatus;
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

  /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float dx = x2 - x1;
                if (Math.abs(dx) > 150) {
                    createSelectionDialog();
                }
        }
        return super.onTouchEvent(event);
    }*/
}
