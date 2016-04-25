package lingnanlu.github.io.daggerdemo;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rico on 4/10/2016.
 */
@Module(library = true)
public class AndroidModule {
    private final DemoApplication application;

    public AndroidModule(DemoApplication application) {
        this.application = application;
    }

    @Singleton
    @ForApplication
    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    LocationManager provideLocationManager() {
        return (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
    }
}
