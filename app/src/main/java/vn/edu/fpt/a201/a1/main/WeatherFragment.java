package vn.edu.fpt.a201.a1.main;

import java.util.Date;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.edu.fpt.a201.a1.R;
import vn.edu.fpt.a201.a1.biz.WeatherFetcherHelper;
import vn.edu.fpt.a201.a1.utils.logging.Logger;

import static android.content.Context.LOCATION_SERVICE;

public class WeatherFragment extends Fragment implements LocationListener {

    private Logger logger = Logger.getLogger(this.getClass());

    private Context context;

    private LocationManager locationManager;

    private Double[] currentLocation = new Double[] {0d, 0d};

    private Typeface weatherFont;

    private TextView cityField;
    private TextView updatedField;
    private TextView detailsField;
    private TextView currentTemperatureField;
    private TextView weatherIcon;

    private WeatherFetcherHelper weatherFetcherHelper = new WeatherFetcherHelper() {
        @Override
        public void onDataReceived(JSONObject data) {
            renderWeather(data);
        }
    };

    Handler handler;

    public WeatherFragment(){
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        context = inflater.getContext();
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);
        
        weatherIcon.setTypeface(weatherFont);
        updateLocation();
        return rootView;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "weather.ttf");
    }
    
    private void updateWeatherData(){
        weatherFetcherHelper.fetchWeatherData(currentLocation[0], currentLocation[1]);
    }
    
    private void renderWeather(JSONObject json){
        try {
            // TODO tu lam nhe

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
            case 2 : icon = getActivity().getString(R.string.weather_thunder);
                      break;
            case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                      break;
            case 7 : icon = getActivity().getString(R.string.weather_foggy);
                     break;
            case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                      break;
            case 6 : icon = getActivity().getString(R.string.weather_snowy);
                      break;
            case 5 : icon = getActivity().getString(R.string.weather_rainy);
                     break;
            }
        }
        weatherIcon.setText(icon);
    }

    private void updateLocation() {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLocation[0] = location.getLatitude();
            logger.info("Location Manager: latitude = %s", currentLocation[0]);
            currentLocation[1] = location.getLongitude();
            logger.info("Location Manager: longitude = %s", currentLocation[1]);
            updateWeatherData();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        logger.debug("Status changed: %s", s);
    }

    @Override
    public void onProviderEnabled(String s) {
        logger.debug("Provider enabled: %s", s);
    }

    @Override
    public void onProviderDisabled(String s) {
        logger.debug("Provider disabled: %s", s);
    }

    public void updateData() {
        updateLocation();
    }
}
