package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.content.Intent;

import com.reconinstruments.ui.carousel.StandardCarouselItem;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Created by erosfricker on 25.02.16.
 */
public class ImageMenuItem extends StandardCarouselItem {

    Integer position = -1;

    public ImageMenuItem(String title, Integer image, Integer position){
        super(title, image);
        this.position = position;
    }

    @Override
    public int getLayoutId() {
        return R.layout.carousel_item_title_icon_column;
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

                break;
            default:

                break;

        }
        context.startActivity(intent);

    }
}
