package ch.uzh.ifi.seal.bachelorthesis.ui.list;

import android.content.Context;
import android.content.Intent;

/**
 * Created by erosfricker on 23/04/16.
 */
public abstract class BasicMenuItem extends MenuItem {

    public BasicMenuItem(String title, Integer image, Integer position) {
        super(title, image, position);
    }

    /**
     * Uses Factory Method to get the correct Intent based on the position
     * @param context The application's context
     */
    @Override
    public void onClick(Context context) {
        Intent intent = getIntentFromPosition(context, position);
        context.startActivity(intent);

    }
    abstract Intent getIntentFromPosition(Context context, int position);
}
