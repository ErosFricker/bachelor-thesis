package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.content.Intent;
import ch.uzh.ifi.seal.bachelorthesis.R;
import com.reconinstruments.ui.carousel.StandardCarouselItem;

/**
 * Created by erosfricker on 21/03/16.
 */
public abstract class MenuItem extends StandardCarouselItem {
    Integer position = -1;

    public MenuItem(String title, Integer image, Integer position){
        super(title, image);
        this.position = position;
    }

    @Override
    public int getLayoutId() {
        return R.layout.carousel_item_title_icon_column;
    }

}
