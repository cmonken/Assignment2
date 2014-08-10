package com.group1.assignment2;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RunActivity extends MainActivity implements SensorEventListener {

    TextView axisX;
    TextView axisY;
    TextView axisZ;


    Button stopBtn;
    EditText etID;
    EditText etAge;
    EditText etName;
    RadioGroup gender;
    int rGroup;
    int temp;

    String id;
    String age;
    String name;
    String sex;
    ObjectEventData thisEventData = new ObjectEventData();

    private DatabaseHandler myHelper;
    private long lastUpdate = 0;
    private float last_x = 0, last_y = 0, last_z = 0;
    //    private static final int SHAKE_THRESHOLD = 600;
    private float[] values = new float[12];
    private String[] verlables = new String[] { "20", "10", "0", "10", "20" };
    private String[] horlables = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11" };
    private GraphView graphViewX, graphViewY, graphViewZ;
    private LinearLayout graphX, graphY, graphZ;
    private boolean runnable = false;

    private SensorManager mySensorManager;
    private Sensor myAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_run);

        // invoke and register accelerometer sensor
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mySensorManager.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        // get the patient data passed through the intent
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        age = intent.getStringExtra("age");
        name = intent.getStringExtra("name");
        sex = intent.getStringExtra("sex");
        rGroup = intent.getIntExtra("radioGroup1", -1);

        // displaying data as text view instead of graph
        axisX = (TextView) findViewById(R.id.textView4);
        axisY = (TextView) findViewById(R.id.textView5);
        axisZ = (TextView) findViewById(R.id.textView6);


        // make the patient's data visible in fragment_run
/*        graphX = (LinearLayout) findViewById(R.id.myGraphViewX);
        graphY = (LinearLayout) findViewById(R.id.myGraphViewY);
        graphZ = (LinearLayout) findViewById(R.id.myGraphViewZ);
        graphViewX = new GraphView(this.getApplicationContext(), values, "X Values", null, null, GraphView.LINE);
        graphViewY = new GraphView(this.getApplicationContext(), values, "Y Values", null, null, GraphView.LINE);
        graphViewZ = new GraphView(this.getApplicationContext(), values, "Z Values", null, null, GraphView.LINE);
        graphX.addView(graphViewX);
        graphY.addView(graphViewY);
        graphZ.addView(graphViewZ); */


        etID = (EditText) findViewById(R.id.editText1);
        etAge = (EditText) findViewById(R.id.editText2);
        etName = (EditText) findViewById(R.id.editText3);
        gender = (RadioGroup) findViewById(R.id.radioGroup1);
        stopBtn = (Button) findViewById(R.id.button3 );
        etID.setText(id);
        etAge.setText(age);
        etName.setText(name);
        gender.check(rGroup);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStopIntent();
            }
        });

        // set the table name to the patient data
        String tableName = name + "_" + id + "_" + age + "_" + sex;
        setTableName(tableName);

        try {
            myHelper = new DatabaseHandler(this);
            myHelper.open();
        } catch (Throwable t) {
            Toast.makeText(this.getApplicationContext(), "Table not created.", Toast.LENGTH_SHORT).show();
            callStopIntent();
        }

//        startDraw.start();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long currentTime = System.currentTimeMillis();

            if ((currentTime - lastUpdate) > 1000) {
//                long diffTime = currentTime - lastUpdate;
                lastUpdate = currentTime;

                thisEventData.setX(x);
                thisEventData.setY(y);
                thisEventData.setZ(z);
                thisEventData.setTimeStamp(currentTime);

                axisX.setText(""+x);
                axisY.setText(""+y);
                axisZ.setText(""+z);

                myHelper.insertEventData(thisEventData);
//                float speed = Math.abs( x + y + z - last_x - last_y - last_z )/ diffTime * 10000;

//                if ( speed > SHAKE_THRESHOLD) {

//                }
//                last_x = x;
//                last_y = y;
//                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.run, menu);
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

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();;
        runnable = false;
    }

    public void callStopIntent(){
        getFragmentManager().popBackStackImmediate();
//        Intent intMain = new Intent(this,MainActivity.class);
//        startActivity(intMain);
    }

    public void setTableName (String tableName){
        MySQLiteHelper.TABLE_PATIENT = tableName;
    }

    public void setGraph(int data){
        for( int i = 0; i < values.length - 1; i++){
            values[i] = values[i+1];
        }

        values[values.length-1] = (float) data;
        graphX.removeView(graphViewX);
        graphY.removeView(graphViewY);
        graphZ.removeView(graphViewZ);
        graphX.addView(graphViewX);
        graphY.addView(graphViewY);
        graphZ.addView(graphViewZ);
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
            View rootView = inflater.inflate(R.layout.fragment_run, container, false);
            return rootView;
        }
    }

}