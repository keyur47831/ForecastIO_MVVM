package code4share.forecastweatherapp.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import code4share.forecastweatherapp.FWWeatherApplication;
import code4share.forecastweatherapp.R;
import code4share.forecastweatherapp.manager.DataManager;
import code4share.forecastweatherapp.manager.Feedback;
import code4share.forecastweatherapp.manager.LocationManager;
import code4share.forecastweatherapp.model.DataPointModel;
import code4share.forecastweatherapp.model.FWWeatherIcons;
import code4share.forecastweatherapp.model.Forecast;
import code4share.forecastweatherapp.utils.Constants;
import code4share.forecastweatherapp.utils.GenericParcelable;
import code4share.forecastweatherapp.view.activity.FWMainActivity;
import code4share.forecastweatherapp.view.interfaces.IMainActivityContract;

/**
 * Created by keyur.
 */

public class FWMainViewModel extends BaseViewModel implements LocationListener {
    private String temperatureRange;
    private String summary;
    private String icon;
    private String timezone;
    private boolean showProgressDialog;
    private IMainActivityContract callback;
    private Forecast modelData;
    private Context context;

    private LocationManager locationManager;
    private DataManager dataManager = FWWeatherApplication.getInstance ().getDataManager ();
    private List<HourlyListData> hourlyListData = new ArrayList<> ();

    public FWMainViewModel (Context activityContract) {
        callback = ((FWMainActivity) activityContract);
        showProgressDialog = false;
        context = activityContract;
        locationManager = new LocationManager (context, this);
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            GenericParcelable<Forecast> item = savedInstanceState.getParcelable (FWMainViewModel.class.getSimpleName ());
            if (item != null) {
                parseData (item.getValue ());
            } else
                loadData ();
        } else
            loadData ();
    }

    @Override
    public void onResume () {
        requestGpsPermission ();
    }

    private void requestGpsPermission () {
        locationManager.requestLocationSetting (new ResultCallback<LocationSettingsResult> () {
            @Override
            public void onResult (@NonNull LocationSettingsResult result) {
                final com.google.android.gms.common.api.Status status = result.getStatus ();
                switch (status.getStatusCode ()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult ((FWMainActivity) context, Constants.PERMISSION_ACCESS_COARSE_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            callback.ShowSnackBar (context.getString (R.string.location_sydney));

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        context.startActivity (new Intent (Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        break;
                    default:
                        callback.ShowSnackBar (context.getString (R.string.location_sydney));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == Constants.PERMISSION_ACCESS_COARSE_LOCATION)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationManager.connectLocation ();
            }
    }

    private void loadData () {
        showProgressDialog = true;
        dataManager.getCurrentWeatherData (Constants.API_KEY, -33.8688, 151.2093, new Feedback<Forecast> () {

            @Override
            public void success (Forecast model) {
                parseData (model);
            }

            @Override
            public void error (String errReason) {
                showProgressDialog = false;
                callback.ShowSnackBar (errReason);
            }
        });
    }

    @Override
    public void onPause () {
        showProgressDialog = false;
        if (locationManager != null)
            locationManager.disconnect ();
    }

    private void parseData (Forecast data) {
        if (data != null)
            modelData = data;
        setTemperatureRange (getTemperatureFormatted (modelData.getCurrently ().getTemperature ()));
        setSummary (modelData.getCurrently ().getSummary ());
        setTimezone (modelData.getTimezone ());
        setIcon (modelData.getCurrently ().getIcon ());
        if (hourlyListData != null && hourlyListData.size () > 0)
            hourlyListData.clear ();
        SimpleDateFormat sdf = new SimpleDateFormat ("EEE, d MMM HH:mm a", Locale.ENGLISH);
        for (DataPointModel item : modelData.getHourly ().getData ()) {
            HourlyListData hourData = new HourlyListData ();
            hourData.setIcon (item.getIcon ());
            hourData.setTemperature (getTemperatureFormatted (item.getTemperature ()));
            Calendar time = Calendar.getInstance ();
            time.setTimeInMillis (item.getTime () * 1000L);
            hourData.setTime (sdf.format (time.getTime ()));
            hourlyListData.add (hourData);

        }
        callback.listDataLoaded ();
        showProgressDialog = false;

    }

    private String getTemperatureFormatted (double data) {
        return String.format (Locale.ENGLISH, "%.1f %cF", data, (char) 0x00B0);

    }

    public void onSaveInstanceState (Bundle savedInstanceState) {
        if (modelData != null)
            savedInstanceState.putParcelable (FWMainViewModel.class.getSimpleName (), new GenericParcelable<> (modelData));
    }

    public String getTemperatureRange () {
        return temperatureRange;
    }

    private void setTemperatureRange (String temperatureRange) {
        this.temperatureRange = temperatureRange;
        notifyChange ();
    }

    public String getSummary () {
        return summary;
    }

    public void setSummary (String summary) {
        this.summary = summary;
        notifyChange ();
    }

    public String getIcon () {
        return icon;
    }

    public void setIcon (String icon) {
        this.icon = icon;
        notifyChange ();
    }

    public String getTimezone () {
        return timezone;
    }

    private void setTimezone (String timezone) {
        this.timezone = timezone;
        notifyChange ();
    }

    @SuppressWarnings("unused")
    @BindingAdapter({"app:imageUrl"})
    public static void loadImage (ImageView view, String url) {
        FWWeatherIcons icon = FWWeatherIcons.findIcon (url);
        view.setImageDrawable (ContextCompat.getDrawable (view.getContext (), icon.resource));
    }

    public boolean isShowProgressDialog () {
        return showProgressDialog;
    }

    @SuppressWarnings("unused")
    public void setShowProgressDialog (boolean showProgressDialog) {
        this.showProgressDialog = showProgressDialog;
    }

    public List<HourlyListData> getHourlyListData () {
        return hourlyListData;
    }

    @SuppressWarnings("unused")
    public void setHourlyListData (List<HourlyListData> hourlyListData) {
        this.hourlyListData = hourlyListData;
    }

    @Override
    public void onLocationChanged (Location location) {
        locationManager.disconnect ();

        dataManager.getCurrentWeatherData (Constants.API_KEY,
                location.getLatitude (), location.getLongitude (),
                new Feedback<Forecast> () {

                    @Override
                    public void success (Forecast model) {
                        parseData (model);
                    }

                    @Override
                    public void error (String errReason) {
                        showProgressDialog=false;
                        callback.ShowSnackBar (errReason);
                    }
                });


    }

}
