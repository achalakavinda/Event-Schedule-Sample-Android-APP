package com.opensource.schedular;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EventListActivity extends Activity implements View.OnClickListener{

    private TimePicker timePicker1;
    private TextView time;
    private Calendar calendar;
    private String format = "";
    private Button buttonSet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

        time = (TextView) findViewById(R.id.textView1);
        calendar = Calendar.getInstance();

        buttonSet = findViewById(R.id.set_button);
        buttonSet.setOnClickListener(this);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.set_button:
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                showTime(hour, min);
                break;
                default:
                return;
        }

    }
}
