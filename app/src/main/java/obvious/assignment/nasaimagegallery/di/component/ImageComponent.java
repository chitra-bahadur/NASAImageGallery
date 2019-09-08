package obvious.assignment.nasaimagegallery.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import obvious.assignment.nasaimagegallery.data.db.ImageDetailsDao;
import obvious.assignment.nasaimagegallery.data.db.ImageDetailsDb;
import obvious.assignment.nasaimagegallery.data.db.ImageRepository;
import obvious.assignment.nasaimagegallery.di.module.AppModule;
import obvious.assignment.nasaimagegallery.di.module.ImageDbModule;
import obvious.assignment.nasaimagegallery.ui.activities.MainActivity;
/*
@Singleton
@Component(dependencies = {}, modules = {AppModule.class, ImageDbModule.class})
public interface ImageComponent {
    void inject(MainActivity mainActivity);

    ImageDetailsDao imageDetailsDao();

    ImageDetailsDb imageDetailsDb();

    ImageRepository imageRepository();

    Application application();
}*/
