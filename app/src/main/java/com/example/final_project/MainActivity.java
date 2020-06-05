package com.example.final_project;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<videoResponse> messages = request();
    }

    private List<videoResponse> request(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        List<videoResponse> messages = new ArrayList<>();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<videoResponse>> videoCall = apiService.getArticles();
        videoCall.enqueue(new Callback<List<videoResponse>>(){
            @Override
            public void onResponse(Call<List<videoResponse>> call, Response<List<videoResponse>> response) {
                List<videoResponse> articles = response.body();
                messages.addAll(articles);
                setAdapt(messages);
            }

            @Override
            public void onFailure(Call<List<videoResponse>> call, Throwable t) {
                videoResponse a = new videoResponse();
                messages.add(a);
            }
        });
        return messages;
    }

    private void setAdapt(List<videoResponse> messages) {
        RecyclerView recyclerview = findViewById(R.id.list_view);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        try {
             recyclerview.setAdapter(new videoAdapt(messages,this));
        } catch (Exception e) {
             e.printStackTrace();
        }
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }
}
