package com.example.gita.listview;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PeopleDao {

    // Database fields
    private SQLiteDatabase database;
    private DataHelper dbHelper;
    private String[] allColumns = {
            DataHelper.COLUMN_ID,
           DataHelper.COLUMN_NAME,
            DataHelper.COLUMN_DESC,
            DataHelper.COLUMN_IMG,};

    public PeopleDao(Context context) {
        dbHelper = new DataHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public People addPeople(People newPeople) {
        ContentValues values = new ContentValues();
        values.put(DataHelper.COLUMN_NAME, newPeople.getName());
        values.put(DataHelper.COLUMN_DESC, newPeople.getDesc());
        values.put(DataHelper.COLUMN_IMG, newPeople.getImg());

        long insertId = database.insert(DataHelper.TABLE_PEOPLE, null,
                values);
        Cursor cursor = database.query(DataHelper.TABLE_PEOPLE,
                allColumns, DataHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        People nPeople = cursorToPeople(cursor);
        cursor.close();
        return nPeople;
    }

    public void deletePeople(People people) {
        int id = people.getId();
        database.delete(DataHelper.TABLE_PEOPLE, DataHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<People> getAllPeople() {
        List<People> peoples = new ArrayList<>();

        Cursor cursor = database.query(DataHelper.TABLE_PEOPLE,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            People people = cursorToPeople(cursor);
            peoples.add(people);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return peoples;
    }

    private People cursorToPeople(Cursor cursor) {
        People people = new People();
        people.setId(cursor.getInt(0));
        people.setName(cursor.getString(1));
        people.setDesc(cursor.getString(2));
        people.setImg(cursor.getInt(3));

        return people;
    }
}
