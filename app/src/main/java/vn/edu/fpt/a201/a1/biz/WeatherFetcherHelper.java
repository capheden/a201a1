package vn.edu.fpt.a201.a1.biz;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import vn.edu.fpt.a201.a1.utils.logging.Logger;

/**
 * Created by minhnn on 10/9/16.
 */

public abstract class WeatherFetcherHelper {

    private Logger logger = Logger.getLogger(this.getClass());

    private JSONObject weatherData;

    public JSONObject getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(JSONObject weatherData) {
        this.weatherData = weatherData;
    }

    public void fetchWeatherData(Double lat, Double lon) {
        logger.debug("Fetching data for coordinate [%s, %s]", lat, lon);
        DataSync syncThread = new DataSync();
        syncThread.execute(lat, lon);
        logger.debug("Executed fetcher thread, waiting for callback...");
    }

    public abstract void onDataReceived(JSONObject data);

    /***
     * Sync weather data via GPS coordinates.<br />
     * <br />
     * (long) latitude = params[0]<br />
     * (long) longitude = params[1]<br />
     */
    private class DataSync extends AsyncTask<Double, Object, Void> {

        private Logger logger = Logger.getLogger(this.getClass());

        @Override
        protected Void doInBackground(Double... coords) {
            logger.debug("GPS Coords: [%s, %s]", coords[0], coords[1]);
            try {
                String apiUrl = new ApiBuilder().buildUrl(coords[0], coords[1]);
                logger.debug("API URL: %s", apiUrl);

                URL url = new URL(apiUrl);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuilder resp = new StringBuilder(1024);
                String tmp;
                while ((tmp=reader.readLine()) != null)
                    resp.append(tmp).append("\n");
                reader.close();

                logger.info("Response: %s", resp.toString());
                JSONObject data = new JSONObject(resp.toString());

                if (data.getInt("cod") != 200) {
                    logger.error("Api status code: %s", data.getInt("cod"));
                    return null;
                }

                onDataReceived(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.error("Exception caught. Return null.");
            return null;
        }
    }

    /**
     * Api Url Builder.
     */
    private class ApiBuilder {

        private Logger logger = Logger.getLogger(this.getClass());

        private final String SOURCE = "http://api.openweathermap.org/data/2.5/weather?";
        private final String PARAM_APPID = "APPID";
        private final String PARAM_FORM = "%s=%s&";
        private final String PARAM_API_LAT = "lat";
        private final String PARAM_API_LON = "lon";
        private final String PARAM_API_UNITS = "units";

        // TODO change your own api key idiots
        // MORE http://openweathermap.org/faq#error401
        private final String API_KEY = "4dd35130430b1ea62558df7aeb76d7a9";

        private String paramCustomUnits = "metric";

        /**
         * Build api url for a specific coordinate.
         *
         * @param lat latitude
         * @param lon longitude
         * @return string api url
         */
        public String buildUrl(Double lat, Double lon) {
            logger.debug("Api for GPS Coords: [%s, %s]", lat, lon);
            StringBuilder builder = new StringBuilder(SOURCE);
            String paramAppId = String.format(PARAM_FORM, PARAM_APPID, API_KEY);
            String paramLat = String.format(PARAM_FORM, PARAM_API_LAT, lat);
            String paramLon = String.format(PARAM_FORM, PARAM_API_LON, lon);
            String paramUnits = String.format(PARAM_FORM, PARAM_API_UNITS, paramCustomUnits);
            builder.append(paramAppId);
            builder.append(paramLat);
            builder.append(paramLon);
            builder.append(paramUnits);
            logger.debug("Built api: %s", builder.toString());
            return builder.toString();
        }
    }
}
