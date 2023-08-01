package com.dya.asmaulhusna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SuraAlKahf extends AppCompatActivity {

    TextView txtKahf ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sura_al_kahf);

        txtKahf = findViewById(R.id.txtKahf);

    }
}