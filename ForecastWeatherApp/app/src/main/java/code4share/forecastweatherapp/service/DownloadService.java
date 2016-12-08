package code4share.forecastweatherapp.service;

import code4share.forecastweatherapp.model.Forecast;
import code4share.forecastweatherapp.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by keyur.
 */

public class DownloadService {
    private static final String TAG = DownloadService.class.getName ();
    private ServiceCallBack<Object> caller;

    public static DownloadService getInstance (ServiceCallBack<Object> caller) {
        return new DownloadService (caller);
    }

    private DownloadService (ServiceCallBack<Object> caller) {
        this.caller = caller;
    }

    public void loadData (String key, double latitude, double longitude) {
        ApiInterface apiInterface = ApiClient.getClient ().create (ApiInterface.class);
        Call<Forecast> call = apiInterface.getWeatherDetails (key, latitude, longitude);
        call.enqueue (new Callback<Forecast> () {
            @Override
            public void onResponse (Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful ())
                    caller.onTaskCompletedSuccess (response.body ());
                else {
                    caller.onTaskCompletedFailure (Constants.DOWNLOAD_FAILED);
                }
            }

            @Override
            public void onFailure (Call<Forecast> call, Throwable t) {
                caller.onTaskCompletedFailure (Constants.CONNECTION_FAILED);
            }
        });
    }
}
