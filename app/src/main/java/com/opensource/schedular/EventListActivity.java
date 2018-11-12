package com.opensource.schedular;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.function.Consumer;

import Adapters.EventListAdapter;
import Model.EventModel;
import util.DialogBox;
import util.FirebaseMethod;
import util.StringValidator;

public class EventListActivity extends AppCompatActivity implements View.OnClickListener ,InputDialog.InputDialogListener, EventListAdapter.EventListListener{

    private FirebaseMethod firebaseMethod;

    private String error;
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
         public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
             EventModel eventModel = dataSnapshot.getValue(EventModel.class);
             for (int i = 0; i < input.size(); i++) {
                 if (input.get(i).Id == eventModel.Id){
                     input.remove(i);
                     input.add(eventModel);
                 }
             }
             mAdapter = new EventListAdapter(input);
             recyclerView.setAdapter(mAdapter);

         }
         @Override
         public void onChildRemoved(DataSnapshot dataSnapshot) {

             EventModel eventModel = dataSnapshot.getValue(EventModel.class);
             for (int i = 0; i < input.size(); i++) {
                 if (input.get(i).Id == eventModel.Id){
                     input.remove(i);
                 }
             }
             mAdapter = new EventListAdapter(input);
             recyclerView.setAdapter(mAdapter);

         }

         @Override
         public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

         @Override
         public void onCancelled(DatabaseError databaseError) {}
             });

    }


    //field validator
    private boolean isStringNull(String string,int con){
        StringValidator sValidate = new StringValidator();
        switch (con){
            case 1:
                if(sValidate.isEmptyString(string)){
                    error = " Description";
                    return false;
                }
        }
        return  true;
    }

    @Override
    public void applyText(String eventDesc) {
        String DATE_ID = YEAR+"-"+MONTH+"-"+DAY;
        String TIME_ID = timePicker1.getCurrentHour().toString()+"-"+timePicker1.getCurrentMinute().toString();

        if(!isStringNull(eventDesc,1)){
            ViewDialogBox("Please Check",error);
            return;
        }

        DatabaseReference ref = databaseReference.child("user_details")
                .child(firebaseMethod.getUserID()).child("event")
                .child(DATE_ID).push();

                ref.setValue(new EventModel(ref.getKey(),DATE_ID,TIME_ID,eventDesc,YEAR,MONTH,DAY,timePicker1.getCurrentHour().toString(),timePicker1.getCurrentMinute().toString()));

    }

    @Override
    public void editText(String id, String year_id, String time_id, String description) {

        if(!isStringNull(description,1)){
            ViewDialogBox("Please Check",error);
            return;
        }

        DatabaseReference ref = databaseReference.child("user_details")
                .child(firebaseMethod.getUserID()).child("event")
                .child(year_id).child(id).child("description");

        ref.setValue(description);


    }

    public void ViewDialogBox( String title, String msg){
        Toast.makeText(this,"Check field "+msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void valuePass(EventModel event) {
        openEditDialog( event);
    }

    @Override
    public void deleteRecord(EventModel eventModel) {
        deleteTuple(eventModel);
    }

    public void deleteTuple(EventModel eventModel) {
        String DATE_ID = eventModel.Raw_Year+"-"+eventModel.Raw_Month+"-"+eventModel.Raw_Day;
        databaseReference.child("user_details")
                .child(firebaseMethod.getUserID()).child("event")
                .child(DATE_ID).child(eventModel.Id).removeValue();
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


    public void openEditDialog(EventModel event){
        InputDialog inputDialog = new InputDialog();
        Bundle args = new Bundle();
        String DATE_ID = event.Raw_Year+"-"+event.Raw_Month+"-"+event.Raw_Day;
        String TIME_ID = event.Raw_Hour+"-"+event.Raw_Min;

        args.putString("value",event.Raw_Year+"/"+event.Raw_Month+"/"+event.Raw_Day+"  "+event.Raw_Hour+":"+event.Raw_Min);
        args.putString("description",event.description);
        args.putString("Year_id",DATE_ID);
        args.putString("Time_id",TIME_ID);
        args.putString("id",event.Id);

        inputDialog.setArguments(args);
        inputDialog.show(getSupportFragmentManager(),"Edit View");
    }

}
