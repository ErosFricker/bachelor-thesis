package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.reconinstruments.ui.carousel.CarouselItem;
import com.reconinstruments.ui.carousel.StandardCarouselItem;
import com.reconinstruments.ui.dialog.CarouselDialog;
import com.reconinstruments.ui.dialog.DialogBuilder;
import com.reconinstruments.ui.list.SimpleArrayAdapter;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.Bug;
import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;
import ch.uzh.ifi.seal.bachelorthesis.model.IssueStatus;
import ch.uzh.ifi.seal.bachelorthesis.model.PreferenceManager;
import ch.uzh.ifi.seal.bachelorthesis.model.SortType;
import ch.uzh.ifi.seal.bachelorthesis.rest.AsyncDelegate;
import ch.uzh.ifi.seal.bachelorthesis.rest.BugzillaAsyncTask;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetIssuesTask;

public class IssuesActivity extends SimpleListActivity implements AsyncDelegate {

    private Bug[] bugArray;
    private List<Bug> bugList;
    public static final String EXTRA_USER_EMAIL = "useremail";
    private CarouselDialog sortingDialog;
    private SortingStrategy sortingStrategy = new SortingByLastChangeDate(0);

    /**
     * Overrides the onKeyDown method to support swipe left / right gestures.
     * @param keyCode the keycode used
     * @param event the event of the key
     * @return returns if the key event should be registered
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
            createSelectionDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Custom List Item for displaying Bugs
     */
    class BugListItem extends SimpleListItem {

        private final String title;
        private final IssueStatus status;
        private final Date lastChangeTime;

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
            showDetailIntent.putExtra(IssueDetailActivity.EXTRA_SELECTED_BUG, bugList.get(position));
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
        GetIssuesTask task = new GetIssuesTask(getApplicationContext(), userEmail, PreferenceManager.getInstance(getApplicationContext()).getServerURL(), this);
        task.setAsyncDelegate(this);
        task.execute();
    }

    @Override
    public void onPostExecuteFinished(String result, BugzillaAsyncTask asyncTask) {
        Gson gson = new Gson();
        BugResult bugResult = new BugResult();
        try {
            bugResult = gson.fromJson(result, BugResult.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (bugResult != null) {
            this.bugList = bugResult.getBugs();
            bugArray = bugResult.getBugs().toArray(new Bug[bugResult.getBugs().size()]);
            refreshAdapter();
        }
    }

    public class CheckedSelectionItem extends StandardCarouselItem {
        final int value;
        public CheckedSelectionItem(String title,int value) {
            super(title);
            this.value = value;
        }

        @Override
        public void updateView(View view) {
            super.updateView(view);
            view.findViewById(R.id.checkmark).setVisibility(value == sortingStrategy.getPosition() ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public int getLayoutId() {
            return R.layout.carousel_item_checkmark;
        }

    }

    public List<CarouselItem> getSelections() {
        return selections;
    }

    private void fillSelections(){
        for (SortType s : SortType.values()){
            this.selections.add(new CheckedSelectionItem(s.toString(), s.ordinal()));
        }
    }
    private final List<CarouselItem> selections = new ArrayList<>();

    private void createSelectionDialog() {
        if(sortingDialog == null) {
            DialogBuilder builder = new DialogBuilder(this).setTitle("Sort List");
            sortingDialog = builder.createSelectionDialog(selections, sortingStrategy.getPosition(), new CarouselDialog.OnItemSelectedListener() {
                @Override
                public void onItemSelected(CarouselDialog dialog, CarouselItem item, int position) {
                    switch (position) {
                        case 0:
                            sortingStrategy = new SortingByLastChangeDate(position);
                            break;
                        case 1:
                            sortingStrategy = new SortingByName(position);
                            break;
                        case 2:
                            sortingStrategy = new SortingByStatus(position);
                            break;
                        default:
                            break;
                    }
                    sortBugs();
                    dialog.dismiss();
                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sortingDialog.show();
            }
        });
    }

    public Bug[] getBugArray() {
        return bugArray;
    }

    private void sortBugs() {
        Collections.sort(bugList, new Comparator<Bug>() {
            @Override
            public int compare(Bug lhs, Bug rhs) {
                return sortingStrategy.compare(lhs, rhs);

            }
        });
        refreshAdapter();
    }

    public CarouselDialog getSortingDialog() {
        return sortingDialog;
    }

    private void refreshAdapter() {
        final List<SimpleListItem> listItems = new ArrayList<>();
        for (Bug b : this.bugList) {
            IssueStatus status = IssueStatus.fromString(b.getStatus());
            listItems.add(new BugListItem(b.getSummary(), status, b.getLast_change_time()));
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setAdapter(createAdapter(listItems));
                getAdapter().notifyDataSetChanged();
            }
        });

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
