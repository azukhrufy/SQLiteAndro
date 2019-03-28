package com.example.gita.listview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private ArrayList<People> lPeople;
    private Context context;
    private TypedArray image;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text1;
        private TextView text2;
        private ImageView imgPeople;
        private ImageView delete;

        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            imgPeople = itemView.findViewById(R.id.car);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    public PeopleAdapter(ArrayList<People> lPeople, Context context, TypedArray image) {
        this.lPeople = lPeople;
        this.context = context;
        this.image = image;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.model, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.text1.setText(lPeople.get(i).getName());
        viewHolder.text2.setText(lPeople.get(i).getDesc());
        viewHolder.imgPeople.setImageDrawable(image.getDrawable(lPeople.get(i).getImg()));
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(i);
            }
        });
    }
        @Override
    public int getItemCount () {
            return lPeople.size();
    }

    private void delete(int position){
        PeopleDao datasource;
        datasource = new PeopleDao(context);

        datasource.open();

        datasource.deletePeople(lPeople.get(position));

        datasource.close();

        lPeople.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position,lPeople.size());
    }

}

