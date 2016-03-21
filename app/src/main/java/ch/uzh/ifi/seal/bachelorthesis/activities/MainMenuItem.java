package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.content.Intent;

import com.reconinstruments.ui.carousel.StandardCarouselItem;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Created by erosfricker on 25.02.16.
 */
public class MainMenuItem extends MenuItem {

    public MainMenuItem(String title, Integer image, Integer position) {
        super(title, image, position);
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent();
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
