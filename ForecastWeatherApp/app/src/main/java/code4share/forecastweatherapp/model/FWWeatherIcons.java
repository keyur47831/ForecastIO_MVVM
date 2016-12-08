package code4share.forecastweatherapp.model;

import code4share.forecastweatherapp.R;

/**
 * Created by keyur.
 */

public enum FWWeatherIcons {

    CLEAR_DAY ("clear-day", R.drawable.sunny),
    CLEAR_NIGHT ("clear-night", R.drawable.clear_night),
    PARTLY_CLOUDY_DAY ("partly-cloudy-day", R.drawable.partly_sunny),
    PARTLY_CLOUDY_NIGHT ("partly-cloudy-night", R.drawable.cloudy_night),
    RAIN ("rain", R.drawable.rain),
    SNOW ("snow", R.drawable.snow),
    WIND ("wind", R.drawable.windy),
    CLOUDY ("cloudy", R.drawable.cloudy),
    UNKNOWN ("unkonwn", R.drawable.unknown);

    public int resource;
    public String key;

    FWWeatherIcons (String key, int resource) {
        this.key = key;
        this.resource = resource;
    }

    public static FWWeatherIcons findIcon (String icon) {
        for (FWWeatherIcons w : FWWeatherIcons.values ()) {
            if (w.key.equalsIgnoreCase (icon)) {
                return w;
            }
        }
        return FWWeatherIcons.UNKNOWN;
    }
}

