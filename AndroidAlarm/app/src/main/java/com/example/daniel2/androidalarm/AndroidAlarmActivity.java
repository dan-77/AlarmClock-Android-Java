package com.example.daniel2.androidalarm;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View.OnClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ToggleButton;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.Timer;
import java.util.TimerTask;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


public class AndroidAlarmActivity extends Activity {
    // Declare reference variables for our widgets
    private EditText hourEditText1;
    private EditText hourEditText2;
    private EditText hourEditText3;
    private EditText minuteEditText1;
    private EditText minuteEditText2;
    private EditText minuteEditText3;
    private TextView separatorLabel1;
    private TextView separatorLabel2;
    private TextView separatorLabel3;
    private TextView currentTime;
    private Button addButton1;
    private Button addButton2;
    private Button addButton3;
    private Button removeButton1;
    private Button removeButton2;
    private Button removeButton3;
    private Button offButton;
    private Spinner soundComboBox;
    private Spinner backgroundComboBox;
    private Spinner ampmComboBox1;
    private Spinner ampmComboBox2;
    private Spinner ampmComboBox3;
    private ToggleButton toggleButton1;
    private ToggleButton toggleButton2;
    private ToggleButton toggleButton3;
    private SharedPreferences savedPrefs;
    private int sound;
    private boolean stop;
    private boolean alarm1;
    private boolean alarm2;
    private boolean alarm3;
    private int time1;
    private int time2;
    private int time3;
    private boolean B1;
    private boolean B2;
    private boolean B3;
    private boolean soundOn;
    private MediaPlayer mp1;
    private MediaPlayer mp2;
    private MediaPlayer mp3;
    private MediaPlayer mp4;
    private String hourText1;
    private String hourText2;
    private String hourText3;
    private String minuteText1;
    private String minuteText2;
    private String minuteText3;
    private Context context;
    private Handler handler;
    private Timer timer;
    private int firstAlarm;
    private int secondAlarm;
    private int thirdAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_alarm);

        //Initialize our widget reference variables
        hourEditText1 = findViewById(R.id.hourEditText1);
        hourEditText2 = findViewById(R.id.hourEditText2);
        hourEditText3 = findViewById(R.id.hourEditText3);
        minuteEditText1 = findViewById(R.id.minuteEditText1);
        minuteEditText2 = findViewById(R.id.minuteEditText2);
        minuteEditText3 = findViewById(R.id.minuteEditText3);
        separatorLabel1 = findViewById(R.id.separatorLabel1);
        separatorLabel2 = findViewById(R.id.separatorLabel2);
        separatorLabel3 = findViewById(R.id.separatorLabel3);
        currentTime = findViewById(R.id.currentTime);
        addButton1 = findViewById(R.id.addButton1);
        addButton2 = findViewById(R.id.addButton2);
        addButton3 = findViewById(R.id.addButton3);
        removeButton1 = findViewById(R.id.removeButton1);
        removeButton2 = findViewById(R.id.removeButton2);
        removeButton3 = findViewById(R.id.removeButton3);
        offButton = findViewById(R.id.offButton);
        soundComboBox = findViewById(R.id.soundComboBox);
        backgroundComboBox = findViewById(R.id.backgroundComboBox);
        ampmComboBox1 = findViewById(R.id.ampmComboBox1);
        ampmComboBox2 = findViewById(R.id.ampmComboBox2);
        ampmComboBox3 = findViewById(R.id.ampmComboBox3);
        toggleButton1 = findViewById(R.id.toggleButton1);
        toggleButton2 = findViewById(R.id.toggleButton2);
        toggleButton3 = findViewById(R.id.toggleButton3);
        this.context = this;

        //these buttons make sure the alarms are not in use when hidden
        B1 = true;
        B2 = true;
        B3 = true;
        //set up the code to refresh the timer
        handler = new Handler();
        timer = new Timer();
        //Set up variables to stop the alarm
        stop = false;
        soundOn = false;
        //Initialize alarm sounds here so that they do not repeatedly initialize in the loop
        mp1 = MediaPlayer.create(context, R.raw.alert);
        mp2 = MediaPlayer.create(context, R.raw.beep);
        mp3 = MediaPlayer.create(context, R.raw.classic);
        mp4 = MediaPlayer.create(context, R.raw.old);

        hourText1 = "01";
        hourText2 = "01";
        hourText3 = "01";
        minuteText1 = "00";
        minuteText2 = "00";
        minuteText3 = "00";
        firstAlarm = 0;
        secondAlarm = 0;
        thirdAlarm = 0;

        // get SharedPreferences object to make the app memorize the data
        savedPrefs = getSharedPreferences( "TipCalcPrefs", MODE_PRIVATE );

        // Create ArrayAdapters for our spinners
        ArrayAdapter<CharSequence> spinnerAdapter1 = ArrayAdapter.createFromResource(this, R.array.spinner_options1, android.R.layout.simple_spinner_item);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> spinnerAdapter2 = ArrayAdapter.createFromResource(this, R.array.spinner_options2, android.R.layout.simple_spinner_item);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> spinnerAdapter3 = ArrayAdapter.createFromResource(this, R.array.spinner_options3, android.R.layout.simple_spinner_item);
        spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> spinnerAdapter4 = ArrayAdapter.createFromResource(this, R.array.spinner_options4, android.R.layout.simple_spinner_item);
        spinnerAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        OnClickListener buttonEventLister = new ButtonListener();

        //Set the listeners for the buttons
        addButton1.setOnClickListener(buttonEventLister);
        addButton2.setOnClickListener(buttonEventLister);
        addButton3.setOnClickListener(buttonEventLister);
        removeButton1.setOnClickListener(buttonEventLister);
        removeButton2.setOnClickListener(buttonEventLister);
        removeButton3.setOnClickListener(buttonEventLister);
        offButton.setOnClickListener(buttonEventLister);

        // Set the adapters and listeners for the spinners
        soundComboBox.setAdapter(spinnerAdapter2);
        soundComboBox.setOnItemSelectedListener(new SpinnerListener());
        backgroundComboBox.setAdapter(spinnerAdapter3);
        backgroundComboBox.setOnItemSelectedListener(new SpinnerListener());
        ampmComboBox1.setAdapter(spinnerAdapter4);
        ampmComboBox1.setOnItemSelectedListener(new SpinnerListener());
        ampmComboBox2.setAdapter(spinnerAdapter4);
        ampmComboBox2.setOnItemSelectedListener(new SpinnerListener());
        ampmComboBox3.setAdapter(spinnerAdapter4);
        ampmComboBox3.setOnItemSelectedListener(new SpinnerListener());

        //Set the listeners for the editText
        hourEditText1.setOnEditorActionListener(new EditTextListener());
        hourEditText2.setOnEditorActionListener(new EditTextListener());
        hourEditText3.setOnEditorActionListener(new EditTextListener());
        minuteEditText1.setOnEditorActionListener(new EditTextListener());
        minuteEditText2.setOnEditorActionListener(new EditTextListener());
        minuteEditText3.setOnEditorActionListener(new EditTextListener());


        //sets the toggleButton listeners and changes a variable that we use to check if the alarm is on or not
        toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    alarm1 = true;
                }
                else {
                    alarm1 = false;
                }
            }
        });
        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    alarm2 = true;
                }
                else {
                    alarm2 = false;
                }
            }
        });

        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    alarm3 = true;
                }
                else {
                    alarm3 = false;
                }
            }
        });

        //Keeps the app refreshing to check for the alarm
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        alarmCheck();
                    }
                });
            }
        }, 0, 20);
    }


    private void alarmCheck(){
        //Updates the time
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        currentTime.setText(currentDateTimeString);
        //Compares time with alarm
        Calendar cal = Calendar.getInstance();
        String zero = Integer.toString(0);
        String hour;
        //makes sure the hours stay at 2 digits
        if (cal.get(Calendar.HOUR) == 0) {
            hour = Integer.toString(12);
        }
        else if (cal.get(Calendar.HOUR) < 10 && cal.get(Calendar.HOUR)!= 0) {
            hour = (zero + Integer.toString(cal.get(Calendar.HOUR)));
        }
        else {
            hour = Integer.toString(cal.get(Calendar.HOUR));
        }
        String minute;
        //makes sure the minutes stay at 2 digits
        if (cal.get(Calendar.MINUTE) < 10) {
            minute = (zero + Integer.toString(cal.get(Calendar.MINUTE)));
        }
        else {
            minute = Integer.toString(cal.get(Calendar.MINUTE));
        }
        String ampm = DateUtils.getAMPMString(cal.get(Calendar.AM_PM));
        //creates the time that we will compare editText with
        String time = (hour + minute);
        //Sets the alarms up to ring. If the editText matches up with time, the alarm button is on, alarm is not hidden and the AM/PM matches up then the alarms ring
        if ((time.equals(hourEditText1.getText().toString()+minuteEditText1.getText().toString())) && alarm1 && B1 &&
                ((ampm.equals("AM") && time1 == 0) || (ampm.equals("PM") && time1 == 1) )) {
            Log.d("1111", String.valueOf(stop));
            if (firstAlarm == 0) {
                alarmRing();
                firstAlarm = 1;
            }
            else if (stop) {
                alarmExit();
                stop = false;
            }
        }
        if ((time.equals(hourEditText2.getText().toString()+minuteEditText2.getText().toString()))&& alarm2 && B2 &&
                        ((ampm.equals("AM") && time2 == 0) || (ampm.equals("PM") && time2 == 1) )){
            Log.d("2222", String.valueOf(stop));

            if (secondAlarm == 0) {
                Log.d("2ABC", String.valueOf(secondAlarm));
                alarmRing();
                secondAlarm = 1;
            }
            else if (stop) {
                alarmExit();
                stop = false;
            }
        }
        if ((time.equals(hourEditText3.getText().toString()+minuteEditText3.getText().toString()))&& alarm3 && B3 &&
                        ((ampm.equals("AM") && time3 == 0) || (ampm.equals("PM") && time3 == 1) )) {
            Log.d("3333", String.valueOf(stop));
            if (thirdAlarm == 0) {
                alarmRing();
                thirdAlarm = 1;
            }
            else if (stop) {
                alarmExit();
                stop = false;
            }
        }
    }

    private void alarmRing() {
        if (sound == 0 && (!mp1.isPlaying() && !mp2.isPlaying() && !mp3.isPlaying() && !mp4.isPlaying()))  {
            mp1.start();
        } else if (sound == 1 && (!mp1.isPlaying() && !mp2.isPlaying() && !mp3.isPlaying() && !mp4.isPlaying())) {
            mp2.start();
        } else if (sound == 2 && (!mp1.isPlaying() && !mp2.isPlaying() && !mp3.isPlaying() && !mp4.isPlaying())) {
            mp3.start();
        } else if (sound == 3 && (!mp1.isPlaying() && !mp2.isPlaying() && !mp3.isPlaying() && !mp4.isPlaying())) {
            mp4.start();
        }
    }
    private void alarmExit() {
        mp1.stop();
        mp2.stop();
        mp3.stop();
        mp4.stop();
    }

    //Listeners for the spinners
    class SpinnerListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            ConstraintLayout co = findViewById(R.id.ConstraintLayout);
            //changes different variables that we use to choose the alarm sound
            if (parent.getId() == R.id.soundComboBox) {
                if (position == 0) {
                    sound = 0;
                }
                if (position == 1) {
                    sound = 1;
                }
                if (position == 2) {
                    sound = 2;
                }
                if (position == 3) {
                    sound = 3;
                }
            }
            //changes different variables that we use to choose the background
            if (parent.getId() == R.id.backgroundComboBox) {
                if (position == 0) {
                    co.setBackgroundResource(R.drawable.grass);
                }
                if (position == 1) {
                    co.setBackgroundResource(R.drawable.ocean);
                }
                if (position == 2) {
                    co.setBackgroundResource(R.drawable.forest);
                }
                if (position == 3) {
                    co.setBackgroundResource(R.drawable.dunes);
                }
            }
            //changes the variable that tells alarmCheck() if each alarm is set to AM or PM
            if (parent.getId() == R.id.ampmComboBox1) {
                if (position == 0) {
                    time1 = 0;
                    }
                if (position == 1) {
                    time1 = 1;
                }
            }
            if (parent.getId() == R.id.ampmComboBox2) {
                if (position == 0) {
                    time2 = 0;
                }
                if (position == 1) {
                    time2 = 1;
                }
            }
            if (parent.getId() == R.id.ampmComboBox3) {
                if (position == 0) {
                    time3 = 0;
                }
                if (position == 1) {
                    time3 = 1;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Not Used / Implemented
        }
    }

    //Button listener
    class ButtonListener implements OnClickListener {
        @Override
        public void onClick( View v ) {
            //enables all widgets in the alarm and enables the remove button depending on which button you press
            if (v.getId() == R.id.addButton1) {
                hourEditText1.setText("01");
                minuteEditText1.setText("00");
                separatorLabel1.setText(":");
                hourEditText1.setEnabled(true);
                minuteEditText1.setEnabled(true);
                ampmComboBox1.setEnabled(true);
                toggleButton1.setEnabled(true);
                addButton1.setEnabled(false);
                removeButton1.setEnabled(true);
                B1 = true;
            }
            else if (v.getId() == R.id.addButton2) {
                hourEditText2.setText("01");
                minuteEditText2.setText("00");
                separatorLabel2.setText(":");
                hourEditText2.setEnabled(true);
                minuteEditText2.setEnabled(true);
                ampmComboBox2.setEnabled(true);
                toggleButton2.setEnabled(true);
                addButton2.setEnabled(false);
                removeButton2.setEnabled(true);
                B2 = true;
            }
            else if (v.getId() == R.id.addButton3){
                (hourEditText3).setText("01");
                minuteEditText3.setText("00");
                separatorLabel3.setText(":");
                hourEditText3.setEnabled(true);
                minuteEditText3.setEnabled(true);
                ampmComboBox3.setEnabled(true);
                toggleButton3.setEnabled(true);
                addButton3.setEnabled(false);
                removeButton3.setEnabled(true);
                B3 = true;
            }
            //disables all the widgets in the alarm and enables the add button depending on which button you press
            else if (v.getId() == R.id.removeButton1){
                hourEditText1.setText("");
                hourEditText1.setEnabled(false);
                minuteEditText1.setText("");
                minuteEditText1.setEnabled(false);
                separatorLabel1.setText("");
                ampmComboBox1.setEnabled(false);
                toggleButton1.setEnabled(false);
                addButton1.setEnabled(true);
                removeButton1.setEnabled(false);
                B1 = false;
            }
            else if (v.getId() == R.id.removeButton2){
                hourEditText2.setText("");
                hourEditText2.setEnabled(false);
                minuteEditText2.setText("");
                minuteEditText2.setEnabled(false);
                separatorLabel2.setText("");
                ampmComboBox2.setEnabled(false);
                toggleButton2.setEnabled(false);
                addButton2.setEnabled(true);
                removeButton2.setEnabled(false);
                B2 = false;
            }
            else if (v.getId() == R.id.removeButton3){
                hourEditText3.setText("");
                hourEditText3.setEnabled(false);
                minuteEditText3.setText("");
                minuteEditText3.setEnabled(false);
                separatorLabel3.setText("");
                ampmComboBox3.setEnabled(false);
                toggleButton3.setEnabled(false);
                addButton3.setEnabled(true);
                removeButton3.setEnabled(false);
                B3 = false;
            }
            //If the user clicks the off button, it sets variable stop to true, we then use this variable to stop the ringing
            else if (v.getId() == R.id.offButton){
                stop = true;
                Log.d("BUTTON", String.valueOf(stop));
            }
        }
    }

    class EditTextListener implements OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            //makes sure the user does not enter hours over 12 and under 1
            if (v.getId() == R.id.hourEditText1){
                String shour1 = hourEditText1.getText().toString();
                int hour1 = Integer.parseInt(shour1);
                if (hour1 >= 12 || hour1 < 1){
                     hourEditText1.setText("01");
                }
            }
            else if (v.getId() == R.id.hourEditText2){
                String shour2 = hourEditText2.getText().toString();
                int hour2 = Integer.parseInt(shour2);
                if (hour2 >= 12 || hour2 < 1){
                    hourEditText2.setText("01");
                }
            }
            else if (v.getId() == R.id.hourEditText3){
                String shour3 = hourEditText3.getText().toString();
                int hour3 = Integer.parseInt(shour3);
                if (hour3 > 12 || hour3 < 1){
                    hourEditText3.setText("01");
                }
            }
            //makes sure the user does not enter minutes over 59
            else if (v.getId() == R.id.minuteEditText1){
                String sminute1 = minuteEditText1.getText().toString();
                int minute3 = Integer.parseInt(sminute1);
                if (minute3 > 59){
                    minuteEditText1.setText("00");
                }
            }
            else if (v.getId() == R.id.minuteEditText2){
                String sminute2 = minuteEditText2.getText().toString();
                int minute2 = Integer.parseInt(sminute2);
                if (minute2 > 59){
                    minuteEditText2.setText("00");
                }
            }
            else if (v.getId() == R.id.minuteEditText3){
                String sminute3 = minuteEditText3.getText().toString();
                int minute3 = Integer.parseInt(sminute3);
                if (minute3 > 59){
                    minuteEditText3.setText("00");
                }
            }
            if ( actionId == EditorInfo.IME_ACTION_DONE ) {
            }
            return false;
        }
    }

    @Override
    public void onResume() {
        //sets the text back to what the user input
        super.onResume();
        hourEditText1.setText(hourText1);
        hourEditText2.setText(hourText2);
        hourEditText3.setText(hourText3);
        minuteEditText1.setText(minuteText1);
        minuteEditText2.setText(minuteText2);
        minuteEditText3.setText(minuteText3);
    }

    @Override
    public void onPause() {
        // Save what the user input
        // Calling the parent onPause() must be done LAST
        super.onPause();
        hourText1 = hourEditText1.getText().toString();
        hourText2 = hourEditText2.getText().toString();
        hourText3 = hourEditText2.getText().toString();
        minuteText1 = minuteEditText1.getText().toString();
        minuteText2 = minuteEditText2.getText().toString();
        minuteText3 = minuteEditText3.getText().toString();
    }
}

