package com.rith.notetaker2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.rith.notetaker2.constants.UniversalConstants;
import com.rith.notetaker2.databinding.ActivityMainBinding;
import com.rith.notetaker2.views.BidLotCreateActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
        Intent intent = new Intent(this, BidLotCreateActivity.class);
        intent.putExtra(UniversalConstants.HELLO,"hello");
        startActivity(intent);
    }
}