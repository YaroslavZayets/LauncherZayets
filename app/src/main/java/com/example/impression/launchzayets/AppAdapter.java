package com.example.impression.launchzayets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

public class AppAdapter extends BaseAdapter {
    private Context context;
    private List<String> appMain;

    public AppAdapter (Context context, List<String> appMain){
        this.context = context;
        this.appMain = appMain;
    }


    @Override
    public int getCount() {
        return appMain.size();
    }

    @Override
    public Object getItem(int position) {
        return appMain.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null){
            button = new Button(context);
            button.setText(appMain.get(position));
        } else {
            button = (Button) convertView;
        }

        button.setId(position);
        return button;
    }
}
