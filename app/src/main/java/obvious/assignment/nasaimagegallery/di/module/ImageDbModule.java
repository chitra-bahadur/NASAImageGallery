package obvious.assignment.nasaimagegallery.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import obvious.assignment.nasaimagegallery.data.db.ImageDetailsDao;
import obvious.assignment.nasaimagegallery.data.db.ImageDetailsDb;
import obvious.assignment.nasaimagegallery.data.db.ImageRepository;

@Module
public class ImageDbModule {

    private ImageDetailsDb db;
    private Application app;

    public ImageDbModule (Application application) {
        app = application;
        db = Room.databaseBuilder(application ,
                ImageDetailsDb.class, "")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    ImageDetailsDb provideImageDatabase() {
        return db;
    }

    @Singleton
    @Provides
    public ImageDetailsDao provideImageDao(ImageDetailsDb database) {
        return database.imageDetailsDao();
    }

    @Singleton
    @Provides
    ImageRepository provideImageRepository(ImageDetailsDao dao) {
        return null;//new ImageRepository(app, dao);
    }
}
