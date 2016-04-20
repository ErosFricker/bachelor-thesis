package ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting;

/**
 * Created by erosfricker on 28/03/16.
 */
public enum SortType {
    ByChangeDate, ByName, ByStatus;


    @Override
    public String toString() {
        switch (this) {
            case ByChangeDate:
                return "Change Date";
            case ByName:
                return "Name";
            case ByStatus:
                return "Status";
        }
        return "";
    }
}
