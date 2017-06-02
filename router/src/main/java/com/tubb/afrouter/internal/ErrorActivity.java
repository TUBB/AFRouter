package com.tubb.afrouter.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tubb.afrouter.R;

/**
 * Created by tubingbing on 17/5/26.
 */

public class ErrorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afrouter_activity_not_found);
    }
}
