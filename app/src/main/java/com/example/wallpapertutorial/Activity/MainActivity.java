package com.example.wallpapertutorial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wallpapertutorial.Adapter.Adapter;
import com.example.wallpapertutorial.Models.ImageModel;
import com.example.wallpapertutorial.R;
import com.example.wallpapertutorial.Service.ApiUtilities;
import com.example.wallpapertutorial.Models.SesrchModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ImageModel> models;
    private RecyclerView recyclerView;
    Adapter adapter;
    CardView mnatur,mbus,mcar,mtrain,mtrending;
    EditText editText;
    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        mnatur=findViewById(R.id.bus);
        mbus=findViewById(R.id.nature);
        mcar=findViewById(R.id.car);
        mtrain=findViewById(R.id.train);
        mtrending=findViewById(R.id.trending);
        editText=findViewById(R.id.edtSearch);
        search = findViewById(R.id.search);

        models = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        adapter = new Adapter(getApplicationContext(),models);
        recyclerView.setAdapter(adapter);
        findPhotos();
        mnatur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="nature";
                getsearchimage(query);
            }
        });

        mbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="bus";
                getsearchimage(query);
            }
        });

        mcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="car";
                getsearchimage(query);
            }
        });

        mtrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="train";
                getsearchimage(query);
            }
        });
        mtrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            findPhotos();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query= editText.getText().toString().trim().toLowerCase();
                if(query.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter Something", Toast.LENGTH_SHORT).show();
                }
                else {
                    getsearchimage(query);
                }
            }
        });
    }

    private void getsearchimage(String query) {
        ApiUtilities.getApiInterface().getSearchImage(query,1,80).enqueue(new Callback<SesrchModel>() {
            @Override
            public void onResponse(Call<SesrchModel> call, Response<SesrchModel> response) {
                models.clear();
                if(response.isSuccessful()){
                    models.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(MainActivity.this, "Not able to get", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SesrchModel> call, Throwable t) {

            }
        });
    }

    private void findPhotos() {
        ApiUtilities.getApiInterface().getImage(1,80).enqueue(new Callback<SesrchModel>() {
            @Override
            public void onResponse(Call<SesrchModel> call, Response<SesrchModel> response) {
                if(response.isSuccessful()){
                    models.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();

                    }else {
                    Toast.makeText(getApplicationContext(), "Not able to get", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SesrchModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}