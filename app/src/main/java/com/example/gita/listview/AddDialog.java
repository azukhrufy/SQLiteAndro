package com.example.gita.listview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddDialog extends DialogFragment {
    private static final String TAG = "AddDialog";
    private PeopleDao datasource;
    
    private static Drawable[] drawables = new Drawable[3];
    private ImageView imageView;
    private Spinner spinner;
    private EditText editText;
    private int currentPosition;
    String[] PeopleNames;
    String[] PeopleDesc;
    
    NoticeDialogListener listener;
    
    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        listener = (NoticeDialogListener) context;
    }

   public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment fragment,People people);
    }

    static AddDialog newInstance(String title){
        AddDialog add = new AddDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        add.setArguments(args);
        return add;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_view_dialog,null);
        builder.setView(view);
        
//        belum ditambahkan pada fragmentnya
        spinner = view.findViewById(R.id.spinner);
        editText = view.findViewById(R.id.editText);
        
        imageView = view.findViewById(R.id.imageView);
        
        PeopleNames = getResources().getStringArray(R.array.p_name);
        PeopleDesc = getResources().getStringArray(R.array.desc);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, PeopleNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinner.setAdapter(adapter);
        
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Drawable drawable = AddDialog.makePeople(getResources(),position);
                imageView.setImageDrawable(drawable);
                currentPosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = PeopleNames[currentPosition];
                String desc = editText.getText().toString();

                if(TextUtils.isEmpty(desc)){
                    desc = PeopleDesc[currentPosition];;
                }
                //pertanyaan
                Drawable img = AddDialog.makePeople(getResources(), currentPosition);

                People newPeople = new People(name, desc, currentPosition);
                Toast.makeText(getActivity(), "add New People Succes", Toast.LENGTH_LONG).show();

                listener.onDialogPositiveClick(AddDialog.this, newPeople);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();

            }
        });

        return  builder.create();
    }

    private static Drawable makePeople(Resources resources, int index) {
        if (drawables[index]==null){
            TypedArray images = resources.obtainTypedArray(R.array.p_img);
            Drawable drawable = images.getDrawable(index);
            drawables[index]=drawable;
        }
        return drawables[index];
    }
}
