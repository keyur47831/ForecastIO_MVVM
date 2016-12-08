package code4share.forecastweatherapp.manager;

import android.content.Context;
import android.support.annotation.VisibleForTesting;

import code4share.forecastweatherapp.model.Forecast;
import code4share.forecastweatherapp.service.DownloadService;
import code4share.forecastweatherapp.service.ServiceCallBack;
import code4share.forecastweatherapp.utils.Constants;

/**
 * Created by keyur.
 */

public class DataManagerImpl implements DataManager, ServiceCallBack<Object> {
    @SuppressWarnings("unused")
    private Context mContext;
    private static DataManagerImpl instance;
    private Forecast mForecast;
    private static DownloadService downloadService;
    private Feedback feedback;

    @VisibleForTesting
    static DataManagerImpl getInstance (Context ctx, DownloadService ds) {
        if (instance == null) {
            instance = new DataManagerImpl (ctx);
        }
        downloadService = ds;
        return instance;
    }

    public static DataManagerImpl getInstance (Context ctx) {
        if (instance == null) {
            instance = new DataManagerImpl (ctx);
        }
        return instance;
    }

    private DataManagerImpl (Context context) {
        mContext = context;
        downloadService = DownloadService.getInstance (this);
    }

    @Override
    public void getCurrentWeatherData (String forecastApiKey, double latitude, double longitude) {
        downloadService.loadData (forecastApiKey, latitude, longitude);
    }

    @Override
    public void getCurrentWeatherData (String forecastApiKey, double latitude, double longitude, Feedback feedback) {
        this.feedback = feedback;
        downloadService.loadData (forecastApiKey, latitude, longitude);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onTaskCompletedSuccess (Object result) {
        if (result instanceof Forecast) {
            mForecast = (Forecast) result;
            if (feedback != null)
                feedback.success (mForecast);
        }

    }

    @Override
    public void onTaskCompletedFailure (Object result) {
        if (feedback != null)
            feedback.error (Constants.CONNECTION_FAILED);
    }

    public Forecast getLastForecast () {
        return mForecast;
    }
}
