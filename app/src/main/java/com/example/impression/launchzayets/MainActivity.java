package com.example.impression.launchzayets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.widget.RelativeLayout.*;

public class MainActivity extends ApssListActivity {

    Button buttonCall;
    Button buttonAppsList;
    Button buttonMessage;
    GridLayout gridLayout;
    SharedPreferences sPref;
    private final static String APP_FOR_MAIN = "Check_app";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sPref = getSharedPreferences(APP_FOR_MAIN, Context.MODE_PRIVATE);

        buttonCall = (Button) findViewById(R.id.buttonCall);
        buttonAppsList = (Button) findViewById(R.id.buttonAppsList);
        buttonMessage = (Button) findViewById(R.id.buttonMessage);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);



        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonCall:
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        startActivity(callIntent);
                        break;
                    case R.id.buttonAppsList:
                        Intent appsListIntent = new Intent(MainActivity.this, ApssListActivity.class);
                        startActivity(appsListIntent);
                        break;
                    case R.id.buttonMessage:
                        Intent messIntent = new Intent(Intent.ACTION_VIEW);
                        messIntent.setType("vnd.android-dir/mms-sms");
                        startActivity(messIntent);
                        break;
                }

            }
        };

        buttonCall.setOnClickListener(onClickListener);
        buttonAppsList.setOnClickListener(onClickListener);
        buttonMessage.setOnClickListener(onClickListener);


    }





    @Override
    protected void onResume() {
        super.onResume();

        Drawable icon = null;
        ApplicationInfo applicationInfo;
        CharSequence label = null;

        ArrayList<AppDetail> appsOnDesktops = new ArrayList<>();

        AppDetail appsInfo;
        int count= 0;


        Set<String> checked_app = sPref.getStringSet(APP_FOR_MAIN, new HashSet<String>());
        for (String name:checked_app){
            try {
                icon = MainActivity.this.getPackageManager().getApplicationIcon(name);
                applicationInfo = MainActivity.this.getPackageManager().getApplicationInfo(name,0);
                label = MainActivity.this.getPackageManager().getApplicationLabel(applicationInfo);
                

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            appsInfo = new AppDetail(name,label,icon,count);
            showApps(appsInfo);
            appsOnDesktops.add(appsInfo);
            count=+1;

        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        gridLayout.removeAllViews();
    }

    public void showApps (AppDetail app){

        GridLayout.LayoutParams gridPar = new GridLayout.LayoutParams();
        gridPar.setMargins(10,10,10,10);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;


        ImageView icon = new ImageView(this);
        TextView name = new TextView(this);
        AppClickListener clickListener = new AppClickListener(app.getName().toString());




        icon.setImageDrawable(app.getIcon());
        name.setText(app.getLabel().toString());

        icon.setOnClickListener(clickListener);

        linearLayout.addView(icon, layoutParams);
        linearLayout.addView(name, layoutParams);



        gridLayout.addView(linearLayout, gridPar);






    }





}


    class AppClickListener implements View.OnClickListener{

        private String appName;

        public AppClickListener(String appName) {
            this.appName = appName;
        }

        @Override
        public void onClick(View v) {
            PackageManager manager = v.getContext().getPackageManager();
            Intent intent = manager.getLaunchIntentForPackage(appName);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
            v.getContext().startActivity(intent);
        }

    }