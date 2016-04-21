package ch.uzh.ifi.seal.bachelorthesis.ui.list;

import android.content.Context;
import android.content.Intent;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar.CalendarActivity;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.issues.IssuesActivity;

/**
 * Created by erosfricker on 21/03/16.
 */
public class ScanMenuItem extends MenuItem {

    private String emailAddress = "";

    public ScanMenuItem(String title, Integer image, Integer position, String emailAddress) {
        super(title, image, position);
        this.emailAddress = emailAddress;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent();
        switch (position){ //Sets the onClick action based on the item's position in the menu
            case 0:
                intent = new Intent(context, IssuesActivity.class);
                intent.putExtra(IssuesActivity.EXTRA_USER_EMAIL, this.emailAddress);
                break;
            case 1:
                intent = new Intent(context, CalendarActivity.class);
                break;
            default:
                break;

        }
        context.startActivity(intent);

    }
}
