package code4share.forecastweatherapp.manager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import code4share.forecastweatherapp.model.Forecast;
import code4share.forecastweatherapp.service.DownloadService;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by keyur.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DataManagerImplTest {

    @Mock
    private DownloadService mockedDownloadService;


    private DataManagerImpl dataManager;

    @Before
    public void setup () {
        MockitoAnnotations.initMocks (this);
        dataManager = DataManagerImpl.getInstance (RuntimeEnvironment.application.getApplicationContext (),
                mockedDownloadService);
    }

    @Test
    public void testGetCurrentWeatherData () {
        dataManager.getCurrentWeatherData ("abcd", -11d, -22d);
        verify (mockedDownloadService, times (1)).loadData (any (String.class), any (double.class), any (double.class));

    }

    @Test
    public void testOnTaskCompletedSuccessWithForecast () {
        Forecast mockedForecast = mock (Forecast.class);
        dataManager.onTaskCompletedSuccess (mockedForecast);
        assertEquals (dataManager.getLastForecast (), mockedForecast);
    }

    @Test
    public void testOnTaskCompletedFailureWithForecast () {
        dataManager.onTaskCompletedFailure (null);
        assertEquals (dataManager.getLastForecast (), null);
    }

}
