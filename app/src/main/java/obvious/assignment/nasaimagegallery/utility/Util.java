package obvious.assignment.nasaimagegallery.utility;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public String getDate() {
        return sdf.format(new Date());
    }

    public String[] getOldDates(int dateGapFromCurrent) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, dateGapFromCurrent);

    }
}
