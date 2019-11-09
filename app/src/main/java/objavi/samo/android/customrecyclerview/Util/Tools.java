package objavi.samo.android.customrecyclerview.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {

    public static String getFormattedDateSimple(long dateTime) {
        SimpleDateFormat newFormat = new SimpleDateFormat("MMMM dd, yyyy");
        if (dateTime > 0) {
            return newFormat.format(new Date(dateTime));
        }
        return "";
    }
}
