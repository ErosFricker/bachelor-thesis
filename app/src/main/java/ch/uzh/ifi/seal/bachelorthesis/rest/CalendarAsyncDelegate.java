package ch.uzh.ifi.seal.bachelorthesis.rest;

import java.util.ArrayList;

import microsoft.exchange.webservices.data.core.service.item.Item;

/**
 * Created by Eros Fricker on 04/27/16.
 */
public interface CalendarAsyncDelegate extends BasicAsyncDelegate {
    void onPostExecuteFinished(ArrayList<ArrayList<Item>> appointments);
}
