package code4share.forecastweatherapp.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by keyur.
 */

public class Constants {

    public static String BASE_URL = "https://api.darksky.net/forecast/";
    public static int PERMISSION_ACCESS_COARSE_LOCATION = 100;
    public static final String PROVIDER_GPS = "gps";
    public static final String PROVIDER_NETWORK = "network";
    public static final long LOCATION_UPDATE_INTERVAL_MILLISECONDS = TimeUnit.SECONDS.toMillis (10);
    public static final long LOCATION_MAX_UPDATE_INTERVAL_MILLISECONDS = TimeUnit.SECONDS.toMillis (5);
    public static String DOWNLOAD_FAILED = "error download";
    public static String CONNECTION_FAILED = "failed to connect server";
    public static String API_KEY = "6ba2796f17afd5fe09ba8e7027860f63";
}
