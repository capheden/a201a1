package vn.edu.fpt.a201.a1.main;

import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import vn.edu.fpt.a201.a1.R;
import vn.edu.fpt.a201.a1.utils.logging.Logger;

public class MainActivity extends ActionBarActivity {

    private Logger logger = Logger.getLogger(this.getClass());

    private WeatherFragment childFragment;

    static {
        AppConfig.configure();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        childFragment = new WeatherFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, childFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                childFragment.updateData();
                break;
        }
        return false;
    }
}
