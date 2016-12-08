package code4share.forecastweatherapp.service;

import code4share.forecastweatherapp.model.Forecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by keyur.
 */

interface ApiInterface {

    @GET("{api_key}/{longitude},{latitude}")
    Call<Forecast> getWeatherDetails (@Path("api_key") String apiKey, @Path("longitude") double longitude, @Path("latitude") double latitude);
}
