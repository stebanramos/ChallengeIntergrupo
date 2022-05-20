package com.stebanramos.challengeintergrupo.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.stebanramos.challengeintergrupo.databinding.ActivitySplahBinding;
import com.stebanramos.challengeintergrupo.utilies.AsynResponse;
import com.stebanramos.challengeintergrupo.utilies.GetToken;

public class SplahActivity extends AppCompatActivity implements AsynResponse {
    private static final String TAG = "SplahActivity";
    boolean isReady = true;
    private ActivitySplahBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplahBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Log.i(TAG, "onCreate()");


        GetToken task = new GetToken(this);
        task.delegate = (AsynResponse) this;
        task.execute();
    }

    @Override
    public void processFinish(boolean isFinish) {
        Log.i(TAG, "onCreate() isFinish " + isFinish);

        if (isFinish) {
            Intent intent = new Intent(SplahActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}