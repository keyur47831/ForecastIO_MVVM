package code4share.forecastweatherapp.manager;

import code4share.forecastweatherapp.model.Forecast;

/**
 * Created by keyur.
 */

public interface DataManager {

    void getCurrentWeatherData (String forecastApiKey, double longitude, double latitude);
    void getCurrentWeatherData (String forecastApiKey, double longitude, double latitude, Feedback feedback);

    Forecast getLastForecast ();

}
