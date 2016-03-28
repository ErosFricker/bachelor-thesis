package ch.uzh.ifi.seal.bachelorthesis.activities.menu;

import android.content.Context;
import android.content.Intent;
import ch.uzh.ifi.seal.bachelorthesis.activities.CalendarActivity;
import ch.uzh.ifi.seal.bachelorthesis.activities.IssuesActivity;

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
        switch (position){
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
