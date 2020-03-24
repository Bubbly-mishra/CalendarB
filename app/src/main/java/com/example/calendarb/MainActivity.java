package com.example.calendarb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.time.MonthDay;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private mySQLiteHandler handler;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private CalendarView calendarView;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.firstline);
        editText2 = findViewById(R.id.secondline);
        editText3 = findViewById(R.id.thirdline);
        calendarView = findViewById(R.id.calender);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                ReadDatabase(view);
            }
        });
        try {
            handler = new mySQLiteHandler(this, "CalendarDatabase", null, 1);
            sqLiteDatabase =  handler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalendar(Date TEXT,Event TEXT)");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void InsertDatabase(View view){
        ContentValues contentValues= new ContentValues();
        contentValues.put("Date",selectedDate);
        contentValues.put("Event",editText1.getText().toString());
        contentValues.put("Event",editText2.getText().toString());
        contentValues.put("Event",editText3.getText().toString());
        sqLiteDatabase.insert("EventCalendar", null,contentValues);

    }
    public void ReadDatabase (View view){
        String query = "Select Event From EventCalendar where Date =" + selectedDate;
        try {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            editText1.setText(cursor.getString(0));
            editText2.setText(cursor.getString(0));
            editText3.setText(cursor.getString(0));
        }
        catch (Exception e){
            e.printStackTrace();
            editText1.setText("");
            editText2.setText("");
            editText3.setText("");
        }
    }
}
