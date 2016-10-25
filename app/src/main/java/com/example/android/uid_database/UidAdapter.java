package com.example.android.uid_database;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AL on 2016/10/23.
 */

public class UidAdapter extends CursorAdapter {

    public UidAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    static class ViewHolder {
        @BindView(R.id.uid_date)
        TextView uid_date;
        @BindView(R.id.uid_val)
        TextView uid_val;
        @BindView(R.id.uid_pw)
        TextView uid_pw;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_uid, parent, false);

        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String Date = cursor.getString(WatchActivityFragment.COL_DATE);
        String Uid = cursor.getString(WatchActivityFragment.COL_UID).trim();
        String Pw = cursor.getString(WatchActivityFragment.COL_PW);
        viewHolder.uid_date.setText(Date);
        viewHolder.uid_val.setText(Uid);
        viewHolder.uid_pw.setText(Pw);
    }
}
