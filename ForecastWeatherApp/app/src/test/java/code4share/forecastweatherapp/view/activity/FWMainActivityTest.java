package code4share.forecastweatherapp.view.activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import code4share.forecastweatherapp.BuildConfig;
import code4share.forecastweatherapp.viewmodel.FWMainViewModel;

import static org.junit.Assert.*;

/**
 * Created by keyur.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class ,sdk = 23)
public class FWMainActivityTest {
    FWMainActivity mainActivity;

    @Before
    public void setup() {
        mainActivity = Robolectric.buildActivity(FWMainActivity.class).create().get();
    }

    @Test
    public void testWpWeatherMainViewModelConstructor() throws Exception {
        FWMainViewModel viewModel = new FWMainViewModel (mainActivity);
        assertNotNull(viewModel);
    }

}