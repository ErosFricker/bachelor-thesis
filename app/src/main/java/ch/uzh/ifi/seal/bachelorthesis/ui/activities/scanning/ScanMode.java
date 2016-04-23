package ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning;

/**
 * Created by erosfricker on 23/04/16.
 */
public enum ScanMode {
    QUICKSCAN,
    GLANCE;

    public static ScanMode fromInt(int mode) {
        switch (mode) {
            case 0:
                return QUICKSCAN;
            case 1:
                return GLANCE;
            default:
                return QUICKSCAN;
        }
    }
}
