package ch.uzh.ifi.seal.bachelorthesis.ui.activities.issues;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import ch.uzh.ifi.seal.bachelorthesis.model.issue.Issue;
import ch.uzh.ifi.seal.bachelorthesis.model.issue.IssueRestResult;
import ch.uzh.ifi.seal.bachelorthesis.model.issue.IssueStatus;
import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.BugListItem;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.CheckedSelectionItem;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting.SortType;
import ch.uzh.ifi.seal.bachelorthesis.rest.BugzillaAsyncDelegate;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetIssuesTask;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting.SortingByLastChangeDate;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting.SortingByName;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting.SortingByStatus;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting.SortingStrategy;

public class IssuesActivity extends SimpleListActivity implements BugzillaAsyncDelegate {

    private Issue[] issueArray;
    private List<Issue> issueList;
    public static final String EXTRA_USER_EMAIL = "useremail";
    private CarouselDialog sortingDialog;
    private SortingStrategy sortingStrategy = new SortingByLastChangeDate(0);
    public ProgressBar progressBar;

    public List<Issue> getIssueList() {
        return issueList;
    }

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        this.progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        String userEmail = getIntent().getStringExtra(EXTRA_USER_EMAIL);
        fillSelections();
        GetIssuesTask task = new GetIssuesTask(this, userEmail, PreferencesFacade.getInstance(getApplicationContext()).getServerURL(), this);
        task.setAsyncDelegate(this);
        task.execute();
    }

    @Override
    public void onPostExecuteFinished(String result) {
        Gson gson = new Gson();
        IssueRestResult issueRestResult = new IssueRestResult();
        try {
            issueRestResult = gson.fromJson(result, IssueRestResult.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (issueRestResult != null) {
            this.issueList = issueRestResult.getIssues();
            issueArray = issueRestResult.getIssues().toArray(new Issue[issueRestResult.getIssues().size()]);
            refreshAdapter();
        }
    }



    public List<CarouselItem> getSelections() {
        return selections;
    }

    private void fillSelections(){
        for (SortType s : SortType.values()){
            this.selections.add(new CheckedSelectionItem(s.toString(), s.ordinal(), sortingStrategy));
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

    public Issue[] getIssueArray() {
        return issueArray;
    }

    private void sortBugs() {
        Collections.sort(issueList, new Comparator<Issue>() {
            @Override
            public int compare(Issue lhs, Issue rhs) {
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
        for (Issue b : this.issueList) {
            IssueStatus status = IssueStatus.fromString(b.getStatus());
            listItems.add(new BugListItem(b.getSummary(), status, b.getLastChangeTime(), this));
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

    @Override
    public void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.progressBar.setVisibility(View.GONE);
    }
}
