package ch.uzh.ifi.seal.bachelorthesis.ui.list;

import com.reconinstruments.ui.carousel.StandardCarouselItem;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Created by erosfricker on 21/03/16.
 */
abstract class MenuItem extends StandardCarouselItem {
    Integer position = -1;

    MenuItem(String title, Integer image, Integer position) {
        super(title, image);
        this.position = position;
    }


    @Override
    public int getLayoutId() {
        return R.layout.carousel_item_title_icon_column;
    }

}
