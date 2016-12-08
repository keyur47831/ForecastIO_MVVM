package code4share.forecastweatherapp.view.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import code4share.forecastweatherapp.BR;
import code4share.forecastweatherapp.R;
import code4share.forecastweatherapp.viewmodel.HourlyListData;

/**
 * Created by keyur.
 */

public class PeriodicForecastAdapter<T extends HourlyListData> extends RecyclerView.Adapter<PeriodicForecastAdapter.FWViewHolder> {

    private List<T> dataList;

    class FWViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        FWViewHolder (View view) {
            super (view);
            binding = DataBindingUtil.bind (view);
        }

        ViewDataBinding getBinding () {
            return binding;
        }
    }

    public PeriodicForecastAdapter (List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public PeriodicForecastAdapter.FWViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.item_hourly, parent, false);
        return new FWViewHolder (v);
    }

    @Override
    public void onBindViewHolder (PeriodicForecastAdapter.FWViewHolder holder, int position) {
        T data = dataList.get (position);
        holder.getBinding ().setVariable (BR.hourlyViewModel, data);

        holder.getBinding ().executePendingBindings ();
    }

    @Override
    public int getItemCount () {
        return dataList.size ();
    }
}
