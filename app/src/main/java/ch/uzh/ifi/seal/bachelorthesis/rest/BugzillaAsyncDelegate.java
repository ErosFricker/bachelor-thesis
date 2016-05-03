package ch.uzh.ifi.seal.bachelorthesis.rest;

/**
 * Created by erosfricker on 24.02.16.
 */
public interface BugzillaAsyncDelegate {
    void onPostExecuteFinished(String result);
    void showProgressBar();
    void hideProgressBar();
}
