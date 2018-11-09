package com.opensource.schedular;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Adapters.EventListAdapter;
import Model.EventModel;
import util.FirebaseMethod;

public class EventListActivity extends AppCompatActivity implements View.OnClickListener ,InputDialog.InputDialogListener{

    private FirebaseMethod firebaseMethod;

    private DatabaseReference databaseReference;

    private Intent intent;
    private TimePicker timePicker1;
    private TextView time;
    private Calendar calendar;
    private String format = "";

    private String FinalTime="";

    private FloatingActionButton floatingActionButton;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView datetimeTextView;

    List<EventModel> input = new ArrayList<>();


    String YEAR = "";
    String MONTH = "";
    String DAY = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        firebaseMethod = new FirebaseMethod(this);

        databaseReference = firebaseMethod.getMyRef();

        intent = getIntent();

        YEAR = intent.getStringExtra("year");
        MONTH = intent.getStringExtra("month");
        DAY = intent.getStringExtra("day");

        //components
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        calendar = Calendar.getInstance();

        datetimeTextView = findViewById(R.id.datetime);

        datetimeTextView.setText(YEAR+"/"+MONTH+"/"+DAY);


        //recycle view
        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView.setLayoutManager(layoutManager);


        //buttons
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);


        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);

        String DATE_ID = YEAR+"-"+MONTH+"-"+DAY;



     databaseReference.child("user_details")
             .child(firebaseMethod.getUserID()).child("event").child(DATE_ID)
             .addChildEventListener(new ChildEventListener() {
         @Override
         public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
             EventModel eventModel = dataSnapshot.getValue(EventModel.class);
             input.add(eventModel);
             mAdapter = new EventListAdapter(input);
             recyclerView.setAdapter(mAdapter);
         }

         @Override
         public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

         @Override
         public void onChildRemoved(DataSnapshot dataSnapshot) {}

         @Override
         public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

         @Override
         public void onCancelled(DatabaseError databaseError) {}
     });




    }



    @Override
    public void applyText(String eventDesc) {
        String DATE_ID = YEAR+"-"+MONTH+"-"+DAY;
        String TIME_ID = timePicker1.getCurrentHour().toString()+"-"+timePicker1.getCurrentMinute().toString();



        DatabaseReference ref = databaseReference.child("user_details")
                .child(firebaseMethod.getUserID()).child("event")
                .child(DATE_ID).push();

                ref.setValue(new EventModel(DATE_ID,TIME_ID,eventDesc,YEAR,MONTH,DAY,timePicker1.getCurrentHour().toString(),timePicker1.getCurrentMinute().toString()));

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

//        FinalTime = new StringBuilder().append(hour).append(" : ").append(min).append(" ").append(format).toString();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.fab:
                openDialog();
                break;
                default:
                return;
        }

    }

    public void openDialog(){
        InputDialog inputDialog = new InputDialog();
        Bundle args = new Bundle();
        args.putString("value",YEAR+"/"+MONTH+"/"+DAY+"  "+timePicker1.getCurrentHour().toString()+":"+timePicker1.getCurrentMinute().toString());
        inputDialog.setArguments(args);
        inputDialog.show(getSupportFragmentManager(),"");
    }
}
