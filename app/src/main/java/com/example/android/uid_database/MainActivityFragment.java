package com.example.android.uid_database;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.uid_database.uidprovider.UidContract;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.android.uid_database.R.id.result;
import static java.security.AccessController.getContext;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private QueryHandler mQueryHandler;
    private static final int QUERY_TOKEN = 1;
    private static final int INSERT_TOKEN = 2;
    private static final int UPDATE_TOKEN = 3;

    private static final String[] UID_COLUMNS = {
            UidContract.UidStore.TABLE_NAME + "." + UidContract.UidStore._ID,
            UidContract.UidStore.COLUMN_UID,
            UidContract.UidStore.COLUMN_PW
    };

    static final int COL_UID = 1;
    static final int COL_PW = 2;

    private String Enc_val = "";

    public MainActivityFragment() {
    }

    @BindView(R.id.Input_Uid)
    EditText Input_Uid;
    @BindView(R.id.resultTitle)
    TextView resultTitle;
    @BindView(result)
    EditText Output_result;
    @BindView(R.id.clipboard)
    EditText Clipboard;
    @BindView(R.id.calculate)
    Button Btncal;
    @OnClick(R.id.calculate)
    public void btnClick(){
        String strCal;
        String input_uid = Input_Uid.getText().toString().trim();

        if(input_uid.equals("")){
            Output_result.setText("");
            Clipboard.setText("");
            return;
        }
        if(input_uid.length() != 16){
            strCal = Utility.addZeroForNum(input_uid,16);
        }
        strCal = "wz" + input_uid.substring(2,input_uid.length()-2) + "yk";
        strCal = input_uid.toUpperCase();

        int result = Utility.CRC16(strCal.getBytes());
        Output_result.setText(String.valueOf(result));
        Enc_val = String.valueOf(Utility.Enc_fun(result));
        Clipboard.setText(input_uid.trim() + "  " + Enc_val);
    }

    @OnClick(R.id.add_db)
    public void btnAddClick() {
        if (Input_Uid.getText().toString().trim().equals(""))
            return;
        if (Clipboard.getText().toString().equals(""))
            return;

        String UidVal = Input_Uid.getText().toString();

        String sUidStoreWithUidSel =
                UidContract.UidStore.TABLE_NAME + "." + UidContract.UidStore.COLUMN_UID + " = ? ";

        Cursor cs = getContext().getContentResolver().query(
                UidContract.UidStore.CONTENT_URI,
                UID_COLUMNS,
                sUidStoreWithUidSel,
                new String[]{UidVal},
                null);

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        String date = sDateFormat.format(new java.util.Date());

        ContentValues cv = new ContentValues();
        if (cs != null && cs.moveToFirst()) {
            //UID已经存在则用update
            cv.put(UidContract.UidStore.COLUMN_DATE, date);
            cv.put(UidContract.UidStore.COLUMN_PW, Enc_val);
            mQueryHandler.startUpdate(UPDATE_TOKEN, null
                    , UidContract.UidStore.CONTENT_URI,
                    cv,
                    sUidStoreWithUidSel,
                    new String[]{UidVal});
        } else {
            //不存在则用insert
            cv.put(UidContract.UidStore.COLUMN_DATE, date);
            cv.put(UidContract.UidStore.COLUMN_UID, Input_Uid.getText().toString());
            cv.put(UidContract.UidStore.COLUMN_PW, Enc_val);
            mQueryHandler.startInsert(INSERT_TOKEN, null, UidContract.UidStore.CONTENT_URI, cv);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mQueryHandler = new QueryHandler(getContext().getContentResolver());
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,rootView);

        Input_Uid.setTransformationMethod(new AllCapTransformationMethod());
        Input_Uid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Output_result.setText("");
                Clipboard.setText("");
                Enc_val = "";
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_mainfrag, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.updata_Page) {
            startActivity(new Intent(getContext(), UpdateActivity.class));
            return true;
        }
        else if (id == R.id.watch_Page) {
            startActivity(new Intent(getContext(), WatchActivity.class));
            return true;
        }
        else if (id == R.id.switch_sta){
            if(item.isChecked()){
                resultTitle.setVisibility(View.GONE);
                Output_result.setVisibility(View.GONE);
            }
            else{
                resultTitle.setVisibility(View.VISIBLE);
                Output_result.setVisibility(View.VISIBLE);
            }
            item.setChecked(!item.isChecked());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class QueryHandler extends AsyncQueryHandler {
        public QueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            super.onInsertComplete(token, cookie, uri);
            Toast.makeText(getContext(), "UID插入完成", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onUpdateComplete(int token, Object cookie, int result) {
            super.onUpdateComplete(token, cookie, result);
            Toast.makeText(getContext(), "UID更新完成", Toast.LENGTH_SHORT).show();
        }
    }
}
