package code4share.forecastweatherapp.viewmodel;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import code4share.forecastweatherapp.model.FWWeatherIcons;

/**
 * Created by keyur.
 */

public class HourlyListData {
    private String time;
    private String icon;
    private String temperature;

    public String getTime () {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }

    public String getIcon () {
        return icon;
    }

    public void setIcon (String icon) {
        this.icon = icon;
    }

    public String getTemperature () {
        return temperature;
    }


    void setTemperature (String temperature) {
        this.temperature = temperature;
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage (ImageView view, String url) {
        FWWeatherIcons icon = FWWeatherIcons.findIcon (url);
        view.setImageDrawable (ContextCompat.getDrawable (view.getContext (), icon.resource));
    }


}
