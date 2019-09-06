package obvious.assignment.nasaimagegallery.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import obvious.assignment.nasaimagegallery.data.model.ImageDetails;

@Database(entities = {ImageDetails.class}, version = 1, exportSchema = false)
public abstract class ImageDetailsDb  extends RoomDatabase {

    private static volatile ImageDetailsDb INSTANCE;

    public abstract ImageDetailsDao imageDetailsDao();

    public static ImageDetailsDb getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ImageDetailsDb.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ImageDetailsDb.class, "images_db").build();
            }
        }

        return INSTANCE;
    }
}
