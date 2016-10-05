package com.example.impression.launchzayets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApssListActivity extends AppCompatActivity {
    private static  final int APP_INFO = 1;
    private static  final int APP_DELETE = 2;
    private final static String APP_FOR_MAIN = "Check_app";

    private PackageManager manager;
    private List<AppDetail> apps;
    private Set<String> checked_app = new HashSet<String>();
    private ListView list;
    private MyArrayAdapter myArrayAdapter;
    public EditText inputSearch;
    public SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apss_list);
        list = (ListView) findViewById(R.id.appsList);
        inputSearch = (EditText) findViewById(R.id.search);
        sPref = getSharedPreferences(APP_FOR_MAIN, Context.MODE_PRIVATE);
        loadApps();



        myArrayAdapter = new MyArrayAdapter(this, R.layout.list_item, android.R.id.text1, apps);
        list.setAdapter(myArrayAdapter);
        list.setOnItemClickListener(myOnItemClickListener);
        registerForContextMenu(list);

        Button ok_button = (Button) findViewById(R.id.okButton);


        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                List<String> resultList = myArrayAdapter.getCheckedItems();

                for (int i = 0; i < resultList.size(); i++) {
                    checked_app.add(String.valueOf(resultList.get(i)));
                    result += String.valueOf(resultList.get(i)) + "\n";
                }
                SharedPreferences.Editor editor = sPref.edit();
                editor.putStringSet(APP_FOR_MAIN, checked_app);
                editor.apply();

            }
        });



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


                for (int i = 0; i < apps.size(); i++) {
                    String text = apps.get(i).name.toString().toLowerCase();
                    if (text.contains(s)) {
                        AppDetail ap = new AppDetail(apps.get(i).name,
                                apps.get(i).label,
                        apps.get(i).icon, i);

                        search_app.add(ap);
                    }
                }
                myArrayAdapter = new MyArrayAdapter(ApssListActivity.this, R.layout.list_item, android.R.id.text1, search_app);
                list.setAdapter(myArrayAdapter);
                list.setOnItemClickListener(myOnItemClickListener);



            }

        });
    }




    public void loadApps(){
        manager = getPackageManager();
        apps = new ArrayList<AppDetail>();
        int count = 0;

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri:availableActivities){
            AppDetail app = new AppDetail(ri.loadLabel(manager),
                    ri.activityInfo.packageName,
                    ri.activityInfo.loadIcon(manager), count);

            apps.add(app);
            count=+1;

        }
    }



    AdapterView.OnItemClickListener myOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            myArrayAdapter.toggleChecked(position);

        }
    };






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





}


