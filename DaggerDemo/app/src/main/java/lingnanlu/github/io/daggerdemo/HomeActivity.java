package lingnanlu.github.io.daggerdemo;

import android.location.LocationManager;
import android.os.Bundle;

import javax.inject.Inject;

/**
 * Created by rico on 4/10/2016.
 */
public class HomeActivity extends DemoBaseActivity{

    @Inject
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
