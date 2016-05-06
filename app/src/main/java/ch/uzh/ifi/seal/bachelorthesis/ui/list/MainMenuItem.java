package ch.uzh.ifi.seal.bachelorthesis.ui.list;

import android.content.Context;
import android.content.Intent;

import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar.MyCalendarActivity;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.issues.IssuesActivity;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.preferences.PreferencesActivity;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning.ScanMenuActivity;

/**
 * Created by erosfricker on 25.02.16.
 */

/**
 * Class for the Main Menu Item, defining the onClick() behaviour
 */
public class MainMenuItem extends BasicMenuItem {

    public MainMenuItem(String title, Integer image, Integer position) {
        super(title, image, position);
    }

    @Override
    Intent getIntentFromPosition(Context context, int position) {
        Intent intent = new Intent();
        switch (position){ //Sets the onClick action of the item based on its position in the menu
            case 0:
                intent = new Intent(context, IssuesActivity.class);
                PreferencesFacade manager = PreferencesFacade.getInstance(context);
                intent.putExtra(IssuesActivity.EXTRA_USER_EMAIL, manager.getUsername());
                break;

            case 1:
                intent = new Intent(context, MyCalendarActivity.class);
                break;
            case 2:
                intent = new Intent(context, ScanMenuActivity.class);
                break;
            case 3:
                intent = new Intent(context, PreferencesActivity.class);
                break;
            default:
                break;

        }
        return intent;
    }
}
