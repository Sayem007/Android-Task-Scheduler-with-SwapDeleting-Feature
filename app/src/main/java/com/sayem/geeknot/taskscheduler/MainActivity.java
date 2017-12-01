package com.sayem.geeknot.taskscheduler;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.sayem.geeknot.taskscheduler.database.Data;
import com.sayem.geeknot.taskscheduler.database.DataHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bdate;

    private int day, mnth, yr;

    private Adapter mAdapter;
    private SQLiteDatabase mDb;
    private EditText mNewNameEditText;
    private EditText mNewDate;
    private EditText mNewSaved;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bdate = (Button) findViewById(R.id.bdate);
        bdate.setOnClickListener(this);

        RecyclerView taskRecyclerView;
        taskRecyclerView = (RecyclerView) this.findViewById(R.id.lv_list);
        mNewNameEditText = (EditText) this.findViewById(R.id.ename);
        mNewDate = (EditText) this.findViewById(R.id.edate);

        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DataHelper dbHelper = new DataHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = getAllTasks();
        mAdapter = new Adapter(this, cursor);
        taskRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                long id = (long) viewHolder.itemView.getTag();

                removeTask(id);

                mAdapter.swapCursor(getAllTasks());
            }

        }).attachToRecyclerView(taskRecyclerView);

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd MMMM, yyyy  |  hh:mm a", Locale.getDefault());
        Date datestamp = new Date();
        return dateFormat.format(datestamp);
    }

    public void addToTasklist(View view) {
        if (mNewNameEditText.getText().length() == 0 || mNewDate.getText().length() == 0) {
            return;
        }

        String date = new String();
        try {
            date = new String(mNewDate.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
        }

        addNewName(mNewNameEditText.getText().toString(), date);

        mAdapter.swapCursor(getAllTasks());
        mNewDate.clearFocus();
        mNewNameEditText.getText().clear();
        mNewDate.getText().clear();
    }

    private Cursor getAllTasks() {
        return mDb.query(
                Data.DataEntry.TABLE,
                null,
                null,
                null,
                null,
                null,
                Data.DataEntry.COL_TIMESTAMP
        );
    }

    private long addNewName(String name, String date) {
        ContentValues cv = new ContentValues();
        cv.put(Data.DataEntry.COL_TASK_TITLE, name);
        cv.put(Data.DataEntry.COL_TASK_DATE, date);
        cv.put(Data.DataEntry.COL_TIMESTAMP, getDateTime());
        return mDb.insert(Data.DataEntry.TABLE, null, cv);
    }

    private boolean removeTask(long id) {

        return mDb.delete(Data.DataEntry.TABLE, Data.DataEntry._ID + "=" + id, null) > 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v==bdate){
            final Calendar c= Calendar.getInstance();
            day= c.get(Calendar.DAY_OF_MONTH);
            mnth= c.get(Calendar.MONTH);
            yr= c.get(Calendar.YEAR);
            String myFormat = "dd/MMMM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                    mNewDate.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
                }
            }
                    ,day,mnth,yr);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }

    }

}