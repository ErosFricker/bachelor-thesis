package ch.uzh.ifi.seal.bachelorthesis.rest;

/**
 * Created by Eros Fricker on 24.02.16.
 */
public interface BugzillaAsyncDelegate extends BasicAsyncDelegate {
    void onPostExecuteFinished(String result);
}
