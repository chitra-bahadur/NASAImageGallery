package obvious.assignment.nasaimagegallery.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public String getDate() {
        return sdf.format(new Date());
    }

    public String[] getDateListForFetching(String dateTillDataFetched) {
        String[] dateArr = new String[10];
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(dateTillDataFetched));
            cal.add(Calendar.DAY_OF_YEAR, -10);
            for(int i = 0; i < 10; i++) {
                cal.add(Calendar.DAY_OF_YEAR, 1);
                dateArr[i] = sdf.format(cal.getTime());
            }
            return dateArr;
        } catch (Exception ae) {
            ae.printStackTrace();
            return null;
        }

    }
}
