package ch.uzh.ifi.seal.bachelorthesis.ui.list;

import android.view.View;

import com.reconinstruments.ui.carousel.StandardCarouselItem;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting.SortingStrategy;

/**
 * Created by efric on 03.05.2016.
 */
public class CheckedSelectionItem extends StandardCarouselItem {
    final int value;
    final SortingStrategy sortingStrategy;
    public CheckedSelectionItem(String title,int value, SortingStrategy strategy) {
        super(title);
        this.value = value;
        this.sortingStrategy = strategy;
    }

    @Override
    public void updateView(View view) {
        super.updateView(view);
        view.findViewById(R.id.checkmark).setVisibility(value == sortingStrategy.getPosition() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.carousel_item_checkmark;
    }

}
