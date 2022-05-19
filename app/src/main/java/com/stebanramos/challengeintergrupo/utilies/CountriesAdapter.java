package com.stebanramos.challengeintergrupo.utilies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stebanramos.challengeintergrupo.databinding.CountryCardBinding;
import com.stebanramos.challengeintergrupo.models.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder>{

    private final String TAG = "CountriesAdapter";

    private List<Country> dataSet;
    private Context context;
    private static ClickListener mClickListener;
    private ArrayList<Country> arraylist;

    public CountriesAdapter(List<Country> dataSet, Context context) {
        Log.i(TAG, "CountriesAdapter() ");
        this.dataSet = dataSet;
        this.context = context;
        this.arraylist = new ArrayList<Country>();
        this.arraylist.addAll(dataSet);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CountryCardBinding binding = CountryCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        String nombre = dataSet.get(position).getNombre();
        Log.i(TAG, "onBindViewHolder() name " + nombre);

        holder.tvCountryName.setText(nombre);

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView ivGo;
        private TextView tvCountryName;

        public ViewHolder(@NonNull CountryCardBinding binding) {
            super(binding.getRoot());

            ivGo = binding.ivGo;
            tvCountryName = binding.tvCountry;

            ivGo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener listener){
        CountriesAdapter.mClickListener = listener;
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        dataSet.clear();
        if (charText.length() == 0) {
            dataSet.addAll(arraylist);
        } else {
            for (Country country : arraylist) {
                if (country.getNombre().toLowerCase(Locale.getDefault()).contains(charText)) {
                    dataSet.add(country);
                }
            }
        }
        notifyDataSetChanged();
    }
}
