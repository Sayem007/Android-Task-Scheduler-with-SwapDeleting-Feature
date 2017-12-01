# Task-Scheduler-with-SwapDeleting-Feature
A sample android SQL based Task Scheduler which is allowed to add new task entry and delete them. It also shows the saved date of every item in the recyclerview. The items can easily be deleted by swapping left or right.
## Preview

![pr3](https://user-images.githubusercontent.com/29102285/33495025-31ac7b38-d6ef-11e7-9d51-4865b0279f75.gif)

## Installation & Usage
* Edit your build.gradle(Module: app) file with this

  compile 'com.android.support:design:25.3.1'
  
  compile 'com.android.support:recyclerview-v7:25.3.1'


* Go to your strings.xml file and edit

<resources>

    <string name="app_name">Task Scheduler</string>
    <string name="task_name">Task Name</string>
    <string name="task_date">Task Date</string>
    <string name="saved_date">Saved Date</string> 
    <string name="select_date">Select Date</string>
    <string name="add">Add To Schedule</string>
    <string name="enter_task_name">Enter Task Name</string>
    <string name="date_format">DD/MM/YYYY</string>
</resources>

* Add a resource layout file named 'task_item_format.xml' and make task items format

  <?xml version="1.0" encoding="utf-8"?>
  <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">


        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal">


                <TextView
                    android:layout_width="110dp"
                    android:layout_height="wrap_content"
                    android:paddingLeft="10dp"
                    android:text="@string/task_name"
                    android:textColor="#000000"
                    android:textSize="17sp"/>

                <TextView
                    android:id="@+id/name_text_view"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:paddingLeft="10dp"
                    android:textColor="#000000"
                    android:textSize="17sp"/>
            </LinearLayout>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal">

                <TextView
                    android:layout_width="110dp"
                    android:layout_height="wrap_content"
                    android:paddingLeft="10dp"
                    android:text="@string/task_date"
                    android:textColor="#000000"
                    android:textSize="17sp"/>

                <TextView
                    android:id="@+id/date_text_view"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:paddingLeft="10dp"
                    android:textColor="#000000"
                    android:textSize="17sp"/>
            </LinearLayout>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="horizontal">

                <TextView
                    android:layout_width="110dp"
                    android:layout_height="wrap_content"
                    android:paddingLeft="10dp"
                    android:text="@string/saved_date"
                    android:textColor="#000000"
                    android:textSize="17sp"/>

                <TextView
                    android:id="@+id/timeStamp_text_view"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:paddingLeft="10dp"
                    android:textColor="#000000"
                    android:textSize="17sp"/>
            </LinearLayout>

        </LinearLayout>
</LinearLayout>

* Let's roll in to activity_main.xml. Here we use the Coordinate/Linear layout as parent layout of that activity.

Then add an editable field(edittext) for entering Task Name, 

        <EditText
            android:id="@+id/ename"
            android:hint="@string/enter_task_name"
            android:textColorHint="#838383"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textColor="#000000"
            android:textSize="17sp"
            android:layout_margin="5dp"/>

Now in another horizontal Linear layout add a button and text field for entering date of the task,

          <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">


            <Button
                android:id="@+id/bdate"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/select_date"
                android:textAlignment="gravity"
                android:textColor="#000000"
                android:textSize="10sp" />


            <EditText
                android:id="@+id/edate"
                android:layout_weight="1"
                android:layout_margin="10dp"
                android:hint="@string/date_format"
                android:editable="false"
                android:textColorHint="#838383"
                android:layout_width="0sp"
                android:layout_height="wrap_content"
                android:textColor="#000000"
                android:textSize="17sp"/>

        </LinearLayout>

Add another button for adding tasks in RecyclerView. 

      <Button
            android:id="@+id/btn_Add"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="@string/add"
            android:textAlignment="gravity"
            android:textColor="#000000"
            android:textSize="13sp"
            android:onClick="addToTasklist"/>

        <android.support.v7.widget.RecyclerView
            android:id="@+id/lv_list"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingTop="5dp"
            android:paddingRight="5dp"
            android:paddingLeft="5dp"
            android:paddingEnd="15dp"/>
        
* For creating database I use 2 files called 'Data.java' and 'DataHelper.java'. 
Data.java defines the property of database.

      public class Data {
      public static final String DB_NAME = "com.sayem.geeknot.taskscheduler.database.db";
      public static final int DB_VERSION= 1;

      public class DataEntry implements BaseColumns{
        public static final String TABLE="TaskSched";
        public static final String COL_TASK_TITLE="title";
        public static final String COL_TASK_DATE = "date";
        public static final String COL_TIMESTAMP = "timestamp";

         }
      }
      
DataHelper.java created the table and defines the property of the attributes 

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + Data.DataEntry.TABLE + " (" +
                Data.DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Data.DataEntry.COL_TASK_TITLE + " TEXT NOT NULL," +
                Data.DataEntry.COL_TASK_DATE + " INTEGER NOT NULL," +
                Data.DataEntry.COL_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Data.DataEntry.TABLE);
        onCreate(db);
    }

* For handeling the data entry through SQL I use an Adapter file using Cursor


            public class Adapter extends RecyclerView.Adapter<Adapter.TaskViewHolder> {

            private Cursor mCursor;
            private Context mContext;


            public Adapter(Context context, Cursor cursor) {
                this.mContext = context;
                this.mCursor = cursor;
            }

            @Override
            public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View view = inflater.inflate(R.layout.task_item_format, parent, false);
                return new TaskViewHolder(view);
            }

            @Override
            public void onBindViewHolder(TaskViewHolder holder, int position) {
                if (!mCursor.moveToPosition(position))
                    return;

                String name = mCursor.getString(mCursor.getColumnIndex(Data.DataEntry.COL_TASK_TITLE));
                String date = mCursor.getString(mCursor.getColumnIndex(Data.DataEntry.COL_TASK_DATE));
                String timestamp = mCursor.getString(mCursor.getColumnIndex(Data.DataEntry.COL_TIMESTAMP));
                long id = mCursor.getLong(mCursor.getColumnIndex(Data.DataEntry._ID));

                holder.nameTextView.setText(name);
                holder.dateTextView.setText(String.valueOf(date));
                holder.timeStampTextView.setText(String.valueOf(timestamp));
                holder.itemView.setTag(id);
            }




            @Override
            public int getItemCount() {
                return mCursor.getCount();
            }


            public void swapCursor(Cursor newCursor) {
                if (mCursor != null) mCursor.close();
                mCursor = newCursor;
                if (newCursor != null) {
                    this.notifyDataSetChanged();
                }
            }


            class TaskViewHolder extends RecyclerView.ViewHolder {

                TextView nameTextView;
                TextView dateTextView;

                TextView timeStampTextView;


                public TaskViewHolder(View itemView) {
                    super(itemView);
                    nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
                    dateTextView = (TextView) itemView.findViewById(R.id.date_text_view);
                    timeStampTextView = (TextView) itemView.findViewById(R.id.timeStamp_text_view);
                }

              }
            }

* Now the final part. MainActivity.java.

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
