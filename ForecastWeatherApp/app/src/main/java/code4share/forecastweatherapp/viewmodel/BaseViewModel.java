package code4share.forecastweatherapp.viewmodel;

import android.databinding.BaseObservable;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Created by keyur.
 */

public abstract class BaseViewModel extends BaseObservable {

    public abstract void onCreate (Bundle savedInstanceState);

    public abstract void onSaveInstanceState (Bundle savedInstanceState);

    public void onDestroy () {
    }

    public void onStart () {
    }

    public void onResume () {
    }

    public void onPause () {
    }

    public void onStop () {
    }

    public void onBackPressed () {
    }

    public void onOptionsItemSelected (MenuItem item) {

    }

    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {

    }
}

