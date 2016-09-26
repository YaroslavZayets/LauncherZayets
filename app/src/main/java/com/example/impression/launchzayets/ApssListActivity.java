package com.example.impression.launchzayets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApssListActivity extends AppCompatActivity {
    private static  final int APP_INFO = 1;
    private static  final int APP_DELETE = 2;

    private PackageManager manager;
    private List<AppDetail> apps;
    private ListView list;
    private MyArrayAdapter myArrayAdapter;
    public EditText inputSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apss_list);
        list = (ListView) findViewById(R.id.appsList);
        inputSearch = (EditText) findViewById(R.id.search);

        loadApps();

        myArrayAdapter = new MyArrayAdapter(this, R.layout.list_item, android.R.id.text1, apps);
        list.setAdapter(myArrayAdapter);
        list.setOnItemClickListener(myOnItemClickListener);
        registerForContextMenu(list);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString().toLowerCase();
                 List<AppDetail> search_app = new ArrayList<>();



                for (int i = 0; i<apps.size(); i++)
                {
                     String text = apps.get(i).name.toString().toLowerCase();
                    if (text.contains(s))
                    {
                        AppDetail ap = new AppDetail();
                        ap.label = apps.get(i).label;
                        ap.name = apps.get(i).name;
                        ap.icon = apps.get(i).icon;
                        ap.count = i;
                        search_app.add(ap);
                    }
                }
                myArrayAdapter = new MyArrayAdapter(ApssListActivity.this, R.layout.list_item, android.R.id.text1, search_app);
                list.setAdapter(myArrayAdapter);
                list.setOnItemClickListener(myOnItemClickListener);

            }

        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,APP_INFO, 0, "О приложении");
        menu.add(0,APP_DELETE, 0, "Удалить");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == APP_INFO)
        {
            AdapterContextMenuInfo chose = (AdapterContextMenuInfo) item.getMenuInfo();
//            Intent app = manager.getLaunchIntentForPackage(apps.get(chose.position).label.toString());
            String name = apps.get(chose.position).label.toString();
            Intent i = new Intent();
            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", name, null);
            i.setData(uri);
            startActivity(i);
            return true;

        }

        if (item.getItemId() == APP_DELETE)
        {
            AdapterContextMenuInfo chose1 = (AdapterContextMenuInfo) item.getMenuInfo();
            apps.remove(chose1.position);
            myArrayAdapter.notifyDataSetChanged();
            return true;

        }

        return super.onContextItemSelected(item);
    }

    private void loadApps(){
        manager = getPackageManager();
        apps = new ArrayList<AppDetail>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri:availableActivities){
            AppDetail app = new AppDetail();
            app.label = ri.activityInfo.packageName;
            app.name = ri.loadLabel(manager);
            app.icon = ri.activityInfo.loadIcon(manager);
            apps.add(app);
            app.count=+1;
        }
    }



    AdapterView.OnItemClickListener myOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            myArrayAdapter.toggleChecked(position);

        }
    };












    public class MyArrayAdapter extends ArrayAdapter<AppDetail> {

        private HashMap<Integer, Boolean> mCheckedMap = new HashMap<>();
        private List<AppDetail> apps_list;

        public MyArrayAdapter(Context context, int resource, int textViewResourceId, List<AppDetail> objects) {


            super(context, resource, textViewResourceId, objects);
            apps_list=objects;

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

        public List<Integer> getCheckedItemPositions() {
            List<Integer> checkedItemPositions = new ArrayList<>();

            for (int i = 0; i<mCheckedMap.size(); i++){
                if (mCheckedMap.get(i)){
                    (checkedItemPositions).add(i);
                }
            }
            return checkedItemPositions;
        }

        public List<String> getCheckedItems() {
            List<String> checkedItems = new ArrayList<>();

            for (int i = 0; i<mCheckedMap.size(); i++){
                if (mCheckedMap.get(i)){
                    (checkedItems).add((String) apps_list.get(i).name);
                }
            }
            return checkedItems;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null){
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.list_item, parent, false);
            }


            ImageView appIcon = (ImageView)row.findViewById(R.id.item_icon);
            appIcon.setImageDrawable(apps_list.get(position).icon);

            CheckedTextView checkedTextView = (CheckedTextView) row.findViewById(R.id.textView1);
            checkedTextView.setText(apps_list.get(position).name);

            Boolean checked = mCheckedMap.get(position);
            if (checked!=null){
                checkedTextView.setChecked(checked);
            }

            return row;
        }
    }


}
