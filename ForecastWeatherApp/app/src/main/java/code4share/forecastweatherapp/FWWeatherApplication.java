package code4share.forecastweatherapp;

import android.app.Application;

import code4share.forecastweatherapp.manager.DataManager;
import code4share.forecastweatherapp.manager.DataManagerImpl;

/**
 * Created by keyur.
 */

public class FWWeatherApplication extends Application {

    private static FWWeatherApplication instance;
    private DataManager dataManager;

    public static FWWeatherApplication getInstance () {
        return FWWeatherApplication.instance;
    }

    @Override
    public void onCreate () {
        super.onCreate ();
        instance = this;
        dataManager = DataManagerImpl.getInstance (this);
    }

    public DataManager getDataManager () {
        return dataManager;
    }
}
