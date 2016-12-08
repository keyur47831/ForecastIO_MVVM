package code4share.forecastweatherapp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import code4share.forecastweatherapp.viewmodel.BaseViewModel;

/**
 * Created by keyur.
 */

public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {

    private T veiwModel;

    @NonNull
    public T getVeiwModel () {
        return veiwModel;
    }


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        veiwModel.onCreate (savedInstanceState);


    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState (outState);
        veiwModel.onSaveInstanceState (outState);
    }

    @Override
    public void onStart () {
        super.onStart ();
        veiwModel.onStart ();
    }

    @Override
    public void onResume () {
        super.onResume ();
        veiwModel.onResume ();
    }

    @Override
    public void onPause () {

        super.onPause ();
        veiwModel.onPause ();
    }

    @Override
    public void onStop () {
        super.onStop ();
        veiwModel.onStop ();
    }

    @Override
    public void onDestroy () {

        super.onDestroy ();
        veiwModel.onDestroy ();

    }

    @Override
    public void onBackPressed () {

        super.onBackPressed ();
        veiwModel.onBackPressed ();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        veiwModel.onOptionsItemSelected (item);
        return super.onOptionsItemSelected (item);
    }

    public void nextActivity (Class<? extends Activity> activityClass, Bundle args) {
        Intent intent = new Intent (getApplication (), activityClass);
        if (args != null) {
            intent.putExtras (args);
        }
        startActivity (intent);
    }

    public void setVeiwModel (T data) {
        veiwModel = data;
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        veiwModel.onRequestPermissionsResult (requestCode, permissions, grantResults);

    }
}

