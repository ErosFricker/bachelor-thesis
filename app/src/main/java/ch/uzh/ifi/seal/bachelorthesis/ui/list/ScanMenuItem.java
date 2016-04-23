package ch.uzh.ifi.seal.bachelorthesis.ui.list;

import android.content.Context;
import android.content.Intent;

import ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning.ScanDeveloperActivity;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning.ScanMode;

/**
 * Created by erosfricker on 23/04/16.
 */
public class ScanMenuItem extends BasicMenuItem {

    public ScanMenuItem(String title, Integer image, Integer position) {
        super(title, image, position);
    }

    @Override
    Intent getIntentFromPosition(Context context, int position) {
        Intent intent = new Intent(context, ScanDeveloperActivity.class);

        switch (position){ //Sets the onClick action based on the item's position in the menu
            case 0:
                intent.putExtra(ScanDeveloperActivity.EXTRA_SCAN_MODE, ScanMode.QUICKSCAN.ordinal());
                break;
            case 1:
                intent.putExtra(ScanDeveloperActivity.EXTRA_SCAN_MODE, ScanMode.GLANCE.ordinal());
                break;
            default:
                break;

        }
        return intent;
    }
}
