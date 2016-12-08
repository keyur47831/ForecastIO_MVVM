package code4share.forecastweatherapp.service;

/**
 * Created by keyur.
 */

public interface ServiceCallBack<T> {
    void onTaskCompletedSuccess (T result);

    void onTaskCompletedFailure (T result);
}
