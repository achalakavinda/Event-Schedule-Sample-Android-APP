package com.opensource.schedular;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CalendarView calendarView;
    private Context avtivityContext;
    private Intent intent;
    private CardView cardViewProfileBtn;
    private CardView cardViewAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        avtivityContext = getApplicationContext();
        calendarView = findViewById(R.id.calendarView);

        cardViewAddBtn = findViewById(R.id.addCardBtn);
        cardViewProfileBtn = findViewById(R.id.profileCardBtn);

        cardViewProfileBtn.setOnClickListener(this);
        cardViewAddBtn.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,int dayOfMonth) {
                EventListView(year,month,dayOfMonth);
            }
        });
    }

    //on click event
    @Override
    public void onClick(View v) {
        System.out.println("item click");
        switch (v.getId()){
            case R.id.addCardBtn:

                break;
            case R.id.profileCardBtn:
                intent = new Intent(this.avtivityContext,ProfileActivity.class);
                startActivity(intent);
                break;
                default:
                    return;
        }
    }

    private void EventListView(int year, int month, int dayOfMonth){
            intent = new Intent(this.avtivityContext,EventListActivity.class);
            intent.putExtra("year",String.valueOf(year));
            intent.putExtra("month",String.valueOf(month));
            intent.putExtra("day",String.valueOf(dayOfMonth));
            startActivity(intent);
    }
}
