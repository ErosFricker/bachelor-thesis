package ch.uzh.ifi.seal.bachelorthesis.rest;

/**
 * Created by erosfricker on 24.02.16.
 */
public interface BugzillaAsyncDelegate extends BasicAsyncDelegate{
    void onPostExecuteFinished(String result);
}
