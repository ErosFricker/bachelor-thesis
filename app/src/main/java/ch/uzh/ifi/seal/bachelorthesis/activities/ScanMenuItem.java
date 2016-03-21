package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.content.Intent;

/**
 * Created by erosfricker on 21/03/16.
 */
public class ScanMenuItem extends MenuItem {

    public ScanMenuItem(String title, Integer image, Integer position) {
        super(title, image, position);
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent();
        //TODO: Change this behavior to use other classes
        //TODO: Change Buglist & Calendar classes to be more generic for both "myissues" and "dev's issues"
        switch (position){
            case 0:
                intent = new Intent(context, MyIssuesActivity.class);
                break;

            case 1:
                intent = new Intent(context, ScanningActivity.class);
                break;

            case 2:
                intent = new Intent(context, SettingsActivity.class);
                break;
            default:

                break;

        }
        context.startActivity(intent);

    }
}
