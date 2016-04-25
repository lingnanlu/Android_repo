package lingnanlu.github.io.daggerdemo;

import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

public class DemoBaseActivity extends AppCompatActivity {

    @Inject
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DemoApplication) getApplication()).inject(this);
    }
}
