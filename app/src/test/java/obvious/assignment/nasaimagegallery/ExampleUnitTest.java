package obvious.assignment.nasaimagegallery;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import obvious.assignment.nasaimagegallery.utility.Util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    private Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void testFormattedDate() {
        Util util = new Util();
        String actualDate = util.getDate();
        String expectedDate = "2019-09-09";
        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testGetDateListTillFetched() {
        Util util = new Util();
        String[] actualDateArr = util.getDateListForFetching("2019-09-09");
        String[] expectedDateArr = {"2019-08-31", "2019-09-01", "2019-09-02", "2019-09-03", "2019-09-04", "2019-09-05", "2019-09-06", "2019-09-07", "2019-09-08", "2019-09-09"};
        assertArrayEquals(expectedDateArr, actualDateArr);
    }

}