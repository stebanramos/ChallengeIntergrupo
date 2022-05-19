package com.stebanramos.challengeintergrupo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.stebanramos.challengeintergrupo.databinding.ActivityMainBinding;
import com.stebanramos.challengeintergrupo.models.Country;
import com.stebanramos.challengeintergrupo.utilies.CountriesAdapter;
import com.stebanramos.challengeintergrupo.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Log.i(TAG, "onCreate()");

        initComponents();
        binding.etSearch.clearFocus();
    }

    private void initComponents(){
        Log.i(TAG, "initComponents()");

        initRecyclerView();

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        Observer<List<Country>> observer = new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                if (countries != null && countries.size() > 0){
                    binding.progressCircular.setVisibility(View.GONE);
                }

                setupRecyclerView(countries);
            }
        };

        mainViewModel.getData(this).observe(this, observer);
    }

    private void initRecyclerView(){
        Log.i(TAG, "initRecyclerView()");

        binding.recyclerContries.setHasFixedSize(true);
        binding.recyclerContries.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupRecyclerView(List<Country> countryList){
        Log.i(TAG, "setupRecyclerView() countryList " + countryList);

        try {
            CountriesAdapter countriesAdapter = new CountriesAdapter(countryList,MainActivity.this);
            binding.recyclerContries.setAdapter(countriesAdapter);

            countriesAdapter.setOnItemClickListener(new CountriesAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("country_name", countryList.get(position).getNombre());
                    startActivity(intent);
                }
            });

            binding.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    countriesAdapter.filter(charSequence.toString());

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ocultarTeclado();
        return true;
    }

    private void ocultarTeclado() {
        View vieww = getCurrentFocus();

        if (vieww != null) {
            vieww.clearFocus(); //*Agregar!
            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(vieww.getWindowToken(), 0);
        }
    }
}