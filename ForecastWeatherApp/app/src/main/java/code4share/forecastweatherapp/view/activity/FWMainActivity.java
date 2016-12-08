package code4share.forecastweatherapp.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import code4share.forecastweatherapp.R;
import code4share.forecastweatherapp.databinding.ActivityForecastWeatherMainBinding;
import code4share.forecastweatherapp.view.adapters.PeriodicForecastAdapter;
import code4share.forecastweatherapp.view.interfaces.IMainActivityContract;
import code4share.forecastweatherapp.viewmodel.FWMainViewModel;
import code4share.forecastweatherapp.viewmodel.HourlyListData;

public class FWMainActivity extends BaseActivity<FWMainViewModel> implements IMainActivityContract {

    ActivityForecastWeatherMainBinding binding;
    FWMainViewModel viewModel;
    RecyclerView recyclerView;
    PeriodicForecastAdapter<HourlyListData> adapter;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView (this, R.layout.activity_forecast_weather_main);
        recyclerView = binding.rcHourly;
        setSupportActionBar (binding.toolbar);
        viewModel = new FWMainViewModel (this);
        setVeiwModel (viewModel);
        binding.setFwViewModel (viewModel);
        super.onCreate (savedInstanceState);


    }

    @Override
    public void ShowSnackBar (String msg) {
        Snackbar.make (binding.lyRoot, msg, Snackbar.LENGTH_LONG).show ();
    }

    @Override
    public void listDataLoaded () {
        adapter = new PeriodicForecastAdapter<> (getVeiwModel ().getHourlyListData ());
        recyclerView.setNestedScrollingEnabled (false);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));
        recyclerView.setAdapter (adapter);
    }


}
