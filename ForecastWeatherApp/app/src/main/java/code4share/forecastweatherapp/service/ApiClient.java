package code4share.forecastweatherapp.service;

import java.util.concurrent.TimeUnit;

import code4share.forecastweatherapp.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by keyur.
 */

class ApiClient {


    private static Retrofit retrofit = null;

    private static OkHttpClient client = new OkHttpClient.Builder ()
            .connectTimeout (10, TimeUnit.SECONDS)
            .readTimeout (20, TimeUnit.SECONDS)
            .writeTimeout (20, TimeUnit.SECONDS)
            .build ();

    static Retrofit getClient () {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder ()
                    .baseUrl (Constants.BASE_URL)
                    .client (client)
                    .addConverterFactory (GsonConverterFactory.create ())
                    .build ();
        }
        return retrofit;
    }
}
