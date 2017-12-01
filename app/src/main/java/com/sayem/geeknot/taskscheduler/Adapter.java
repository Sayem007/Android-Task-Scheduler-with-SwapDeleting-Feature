package com.sayem.geeknot.taskscheduler;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sayem.geeknot.taskscheduler.database.Data;


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