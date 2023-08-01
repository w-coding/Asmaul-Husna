package com.dya.asmaulhusna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView namesRecyclerView;
    SQLiteDatabase db;
    DatabaseConnect databaseConnectClass;
    List<NamesItem> AllahName ;

    EditText searchEditText ;
    FloatingActionButton floatingActionButton;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch changeMode;
    boolean nightMod;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    NamesRecyclerViewAdapter adapter;

    String vName="";

    // Get the package manager
    PackageManager packageManager;
    // Get the package info for the current app
    PackageInfo packageInfo = null;

    HijrahDate hijrahDate;
    DateTimeFormatter formatter;
    String formatted;

    TextView txtHijrahDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namesRecyclerView = findViewById(R.id.namesRecyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        searchEditText = findViewById(R.id.searchEditText);
        changeMode = findViewById(R.id.changeMode);
        txtHijrahDate = findViewById(R.id.txtHijrahDate);


        txtHijrahDate.setOnClickListener(v -> {

           /** Intent intent = new Intent(MainActivity.this,SuraAlKahf.class);
            startActivity(intent);
            */
        });

        databaseConnectClass = new DatabaseConnect(this);
        databaseConnectClass.StartWork();
        db=databaseConnectClass.getWritableDatabase();
        AllahName = new ArrayList<>();
        StoreDataInArrayList();
        adapter = new NamesRecyclerViewAdapter(this,AllahName);
        namesRecyclerView.setAdapter(adapter);
        namesRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        namesRecyclerView.setHasFixedSize(true);

        hijrahDate = HijrahDate.now();
        formatter = DateTimeFormatter.ofPattern("dd/MMMM/yyyy"); /** Day / Month / Years  */
        /**
        formatterDay = DateTimeFormatter.ofPattern("dd"); // Day
        formatterMonth = DateTimeFormatter.ofPattern("MMMM"); // Month
        formatterYears = DateTimeFormatter.ofPattern("yyyy"); // Years

        formattedDay = formatterDay.format(hijrahDate); // Day
        formattedMonth = formatterMonth.format(hijrahDate); // Month
        formattedYears = formatterYears.format(hijrahDate); // Years
       */
        formatted = formatter.format(hijrahDate);

        txtHijrahDate.setText(formatted);
        packageManager = getPackageManager();


        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        vName  = "Version : "+packageInfo.versionName;

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMod = sharedPreferences.getBoolean("nightMod",false);

        if (nightMod){
            changeMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        changeMode.setOnClickListener(v -> {
            if (nightMod){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor = sharedPreferences.edit();
                editor.putBoolean("nightMod",false);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor = sharedPreferences.edit();
                editor.putBoolean("nightMod",true);
            }
            editor.apply();

        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        floatingActionButton.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this,CompassActivity.class);
            startActivity(intent);

        });


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemIds = item.getItemId();
            if (itemIds == R.id.homeItem) {


                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            } else if (itemIds == R.id.about) {

                AboutBottomSheetDialog();
            } else if (itemIds == R.id.share) {

                // Share app

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareSubject = "Asmaul Husna Application  Download now";
                String shareBode = " Asmaul Husna  \n Download now \n";
                String shareBode1 = "https://play.google.com/store/apps/details?id=com.dya.asmaulhusna";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBode + "\n" + shareBode1);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                startActivity(Intent.createChooser(shareIntent, "share using"));

            }
            else if (itemIds == R.id.Star) {
                // review and rating
                try {

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));

                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.googl.com/store/apps/details?id=" + getPackageName())));
                }
            }


            return true;
        });


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    void StoreDataInArrayList(){

        Cursor cursor = databaseConnectClass.readAllNamesData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "no data ", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){

                AllahName.add(new NamesItem(cursor.getString(0),cursor.getString(1)
                        ,cursor.getString(2),cursor.getString(3)
                        ,cursor.getString(4),cursor.getString(5)
                        ,cursor.getString(6),cursor.getString(7)
                        ,cursor.getString(8),cursor.getString(9)
                        ,cursor.getString(10),cursor.getString(11)
                        ,cursor.getString(12),cursor.getString(13)
                ));

            }
        }
    }


    void AboutBottomSheetDialog(){

        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetStyle);
        dialog.setContentView(R.layout.bottom_sheet_dialog_layout_about);
        TextView txtCode= dialog.findViewById(R.id.txtCode);
        TextView txtAbout= dialog.findViewById(R.id.txtAbout);
        TextView txtVersion= dialog.findViewById(R.id.txtVersion);


        assert txtAbout != null;
        txtAbout.setText("Asmaul Husna\n\nit was an islamic open sours application");
        assert txtVersion != null;
        txtVersion.setText(vName);

        assert txtCode != null;
        txtCode.setOnClickListener(v -> getTelegramInt(MainActivity.this));
        txtCode.setPaintFlags(txtCode.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        dialog.show();


    }

    void getTelegramInt(Context context) {
        Intent intent;
        try {
            try { // check for telegram app
                context.getPackageManager().getPackageInfo("org.telegram.messenger", 0);
            } catch (PackageManager.NameNotFoundException e) {
                // check for telegram X app
                context.getPackageManager().getPackageInfo("org.thunderdog.challegram", 0);
            }
            // set app Uri
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain={wallacoding}"));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            // set browser URI
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.telegram.me/wallacoding"));
            startActivity(intent);
        }
    }


}