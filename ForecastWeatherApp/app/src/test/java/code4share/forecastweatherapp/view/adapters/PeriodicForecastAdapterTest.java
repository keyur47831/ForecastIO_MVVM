package code4share.forecastweatherapp.view.adapters;

import android.databinding.ViewDataBinding;
import android.widget.LinearLayout;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import code4share.forecastweatherapp.BuildConfig;
import code4share.forecastweatherapp.viewmodel.HourlyListData;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by keyur.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class ,sdk = 23)
public class PeriodicForecastAdapterTest {

    @Test
    public void testPeriodicForecastAdapterCostructor() throws Exception {
        List<HourlyListData> mockedList = mock(ArrayList.class);
        PeriodicForecastAdapter pfa = new PeriodicForecastAdapter<>(mockedList);
        pfa.getItemCount();
        verify(mockedList, times(1)).size();
    }

    @Ignore("Because Robolectric doesn't support Data Binding yet")
    @Test
    public void testOnCreateViewHolderFunctionality() throws Exception {
        List<HourlyListData> mockedList = mock(ArrayList.class);
        PeriodicForecastAdapter pfa = new PeriodicForecastAdapter<>(mockedList);

        LinearLayout mockedParent = mock(LinearLayout.class);
        when(mockedParent.getContext()).thenReturn(RuntimeEnvironment.application.getApplicationContext());
        assertNotNull(pfa.onCreateViewHolder(mockedParent, 0));
    }

    @Test
    public void testOnBindViewHolderFunctionality() throws Exception {
        List<HourlyListData> dataList = new ArrayList<>();
        dataList.add(mock(HourlyListData.class));
        dataList.add(mock(HourlyListData.class));
        PeriodicForecastAdapter pfa = new PeriodicForecastAdapter<>(dataList);

        PeriodicForecastAdapter.FWViewHolder mockedHolder = mock(PeriodicForecastAdapter.FWViewHolder.class);
        ViewDataBinding mockedVDB = mock(ViewDataBinding.class);
        when(mockedHolder.getBinding()).thenReturn(mockedVDB);
        pfa.onBindViewHolder(mockedHolder, 1);
        verify(mockedVDB, times(1)).setVariable(any(Integer.class), any());
        verify(mockedVDB, times(1)).executePendingBindings();
    }

    @Test
    public void testGetItemCountForCodeCoverage() throws Exception {
        List<HourlyListData> dataList = new ArrayList<>();
        dataList.add(mock(HourlyListData.class));
        dataList.add(mock(HourlyListData.class));
        PeriodicForecastAdapter pfa = new PeriodicForecastAdapter<>(dataList);

        assertEquals(2, pfa.getItemCount());

    }

}