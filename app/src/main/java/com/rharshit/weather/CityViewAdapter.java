package com.rharshit.weather;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CityViewAdapter extends BaseAdapter {

    private List<City> cities;
    private Context mContext;

    public CityViewAdapter(Context mContext, List<City> cities){
        this.mContext = mContext;
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(mContext);
        tv.setText(cities.get(position).title);
        return tv;
    }
}
