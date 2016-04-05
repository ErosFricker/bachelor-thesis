package ch.uzh.ifi.seal.bachelorthesis.activities.menu;

import android.content.Context;
import android.content.Intent;
import ch.uzh.ifi.seal.bachelorthesis.activities.CalendarActivity;
import ch.uzh.ifi.seal.bachelorthesis.activities.IssuesActivity;
import ch.uzh.ifi.seal.bachelorthesis.activities.scanning.ScanDeveloperActivity;
import ch.uzh.ifi.seal.bachelorthesis.activities.SettingsActivity;
import ch.uzh.ifi.seal.bachelorthesis.model.SettingsParser;

/**
 * Created by erosfricker on 25.02.16.
 */
class MainMenuItem extends MenuItem {

    public MainMenuItem(String title, Integer image, Integer position) {
        super(title, image, position);
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent();
        switch (position){ //Sets the onClick action of the item based on its position in the menu
            case 0:
                intent = new Intent(context, IssuesActivity.class);
                intent.putExtra(IssuesActivity.EXTRA_USER_EMAIL, SettingsParser.getInstance(context).getUserName());
                break;

            case 1:
                intent = new Intent(context, CalendarActivity.class);
                break;
            case 2:
                intent = new Intent(context, ScanDeveloperActivity.class);
                break;
            case 3:
                intent = new Intent(context, SettingsActivity.class);
                break;
            default:
                break;

        }
        context.startActivity(intent);

    }
}
