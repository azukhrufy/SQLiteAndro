package com.example.gita.listview;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
//import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddDialog.NoticeDialogListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton add;
    private ArrayList<People> lPeople;
    private PeopleDao datasource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new PeopleDao(this);
        datasource.open();

        //floating action button
        add = findViewById(R.id.FAB);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Memanggil Fragment untuk dan Add Dialog
                AddDialog addList = AddDialog.newInstance("AddDialog");
                addList.show(getSupportFragmentManager(), "AddDialog");

            }
        });

        // Mengakses Isi Dari Array XML
        String[] peopleName = getResources().getStringArray(R.array.p_name);
        String[] desc = getResources().getStringArray(R.array.desc);
        TypedArray img = getResources().obtainTypedArray(R.array.p_img);

        //Inisialisasi Arraylist
        lPeople = new ArrayList<>();
        lPeople = (ArrayList<People>) datasource.getAllPeople();
        //Add Satu Object Ke Array List
//        lPeople.add(new People(peopleName[0], desc[0], img.getDrawable(0)));


        //recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PeopleAdapter(lPeople,this,img);
        recyclerView.setAdapter(mAdapter);

    }



    @Override
    public void onDialogPositiveClick(DialogFragment fragment, People people) {
        People newPeople = datasource.addPeople(people);
        lPeople.add(newPeople);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        super.onResume();
        datasource.open();
    }

    @Override
    protected void onStop(){
        super.onStop();
        datasource.close();
    }

}



