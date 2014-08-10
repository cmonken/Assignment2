package com.group1.assignment2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


public class MainActivity extends Activity {

    Button runBtn;
    EditText etID;
    EditText etAge;
    EditText etName;
    RadioGroup gender;

    Intent intRun;

    String Name = null;
    String ID = null;
    String Age = null;
    String Sex = null;
    String tableName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        runBtn  = (Button) findViewById(R.id.button1);
        etID = (EditText) findViewById(R.id.editText1);
        etAge = (EditText) findViewById(R.id.editText2);
        etName = (EditText) findViewById(R.id.editText3);
        gender = (RadioGroup) findViewById(R.id.radioGroup1);

        runBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callRunIntent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void callRunIntent(){
        intRun = new Intent(this, RunActivity.class);
        intRun.putExtra("ID", etID.getText().toString());
        intRun.putExtra("age", etAge.getText().toString());
        intRun.putExtra("name", etName.getText().toString());
        intRun.putExtra("sex", Sex);
        onCheckedChanged(gender, gender.getCheckedRadioButtonId());

        startActivity(intRun);
    }

    /*Called when the RunActivity is invoked. When the selection is cleared,
    checkedId is -1.*/
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.radioButton1:
                //intRun.putExtra("radioGroup1", "male");
                intRun.putExtra("radioGroup1", group.getCheckedRadioButtonId());
                Sex = "male";
                break;
            case R.id.radioButton2:
                //intRun.putExtra("radioGroup1", "female");
                intRun.putExtra("radioGroup1", group.getCheckedRadioButtonId());
                Sex = "female";
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}