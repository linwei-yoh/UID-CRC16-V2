package com.example.android.uid_database;

import android.content.Intent;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.android.uid_database.R.id.result;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

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
        Clipboard.setText(input_uid.trim() + "  " + String.valueOf(Utility.Enc_fun(result)));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
}
