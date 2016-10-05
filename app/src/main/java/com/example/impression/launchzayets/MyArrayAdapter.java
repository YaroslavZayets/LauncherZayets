package com.example.impression.launchzayets;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<AppDetail> {
    private Context context;
    private List<AppDetail> appsList;

    private HashMap<Integer, Boolean> mCheckedMap = new HashMap<>();




    public MyArrayAdapter(Context context, int resource, int textViewResourceId, List<AppDetail> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.appsList = objects;

        for (int i = 0; i < objects.size(); i++) {
            mCheckedMap.put(i, false);
        }
    }

    public void toggleChecked(int position) {
        if (mCheckedMap.get(position)) {
            mCheckedMap.put(position, false);
        } else {
            mCheckedMap.put(position, true);
        }
        notifyDataSetChanged();
    }



    public List<String> getCheckedItems() {
        List<String> checkedItems = new ArrayList<String>();

        for (int i = 0; i<mCheckedMap.size(); i++){
            if (mCheckedMap.get(i)){
                (checkedItems).add((String) appsList.get(i).label);
            }
        }
        return checkedItems;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);
        }

        ImageView appIcon = (ImageView)row.findViewById(R.id.item_icon);
        appIcon.setImageDrawable(appsList.get(position).icon);

        CheckedTextView checkedTextView = (CheckedTextView) row.findViewById(R.id.textView1);
        checkedTextView.setText(appsList.get(position).name);

        Boolean checked = mCheckedMap.get(position);
        if (checked!=null){
            checkedTextView.setChecked(checked);
        }



        return row;
    }
}
