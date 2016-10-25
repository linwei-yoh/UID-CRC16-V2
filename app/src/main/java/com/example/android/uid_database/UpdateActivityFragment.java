package com.example.android.uid_database;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.uid_database.uidprovider.UidContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class UpdateActivityFragment extends Fragment {

    public UpdateActivityFragment() {
    }

    @BindView(R.id.UidList)
    EditText UidList;

    @OnClick(R.id.update)
    public void btnClick() {

        String allUid = UidList.getText().toString().trim();
        if(allUid.equals(""))
            return;
        String[] UidList = allUid.split("\n");

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        String date = sDateFormat.format(new java.util.Date());

        String pattern = "(.*?)\\s+(\\d+)\\b";
        Pattern r = Pattern.compile(pattern);

        ArrayList<ContentValues> mAC = new ArrayList<>();
        for (int i = 0; i < UidList.length; i++) {
            String item = UidList[i].trim();
            if (item.equals(""))
                continue;
            Matcher m = r.matcher(item);
            if (m.find()) {
                ContentValues cv = new ContentValues();
                cv.put(UidContract.UidStore.COLUMN_UID, m.group(1));
                cv.put(UidContract.UidStore.COLUMN_DATE, date);
                cv.put(UidContract.UidStore.COLUMN_PW, m.group(2));
                mAC.add(cv);
            }
        }
        ContentValues[] UidContents = new ContentValues[mAC.size()];
        mAC.toArray(UidContents);

        getContext().getContentResolver().bulkInsert( UidContract.UidStore.CONTENT_URI, UidContents);
        Toast.makeText(getContext(), "UID插入完成",Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_update, container, false);
        ButterKnife.bind(this, rootView);

        UidList.setTransformationMethod(new AllCapTransformationMethod());
        return rootView;
    }

}
