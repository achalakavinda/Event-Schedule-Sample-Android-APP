package com.opensource.schedular;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import Adapters.EventListAdapter;
import Model.EventModel;
import util.FirebaseMethod;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUperName;
    private TextView textView2UperEmail;
    private TextView textView7name;
    private TextView textView9id;
    private TextView textView11email;

    private FirebaseMethod firebaseMethod;

    private String error;
    private DatabaseReference databaseReference;

    public class User{
        public String Nic;
        public String name;
        public String email;

        public User(String Nic, String name, String email){
            this.Nic = Nic;
            this.name = name;
            this.email = email;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewUperName = findViewById(R.id.textView);
        textView2UperEmail = findViewById(R.id.textView2);
        textView7name = findViewById(R.id.textView7);
        textView9id = findViewById(R.id.textView9);
        textView11email = findViewById(R.id.textView11);

        textViewUperName.setText("");
        textView2UperEmail.setText("");
        textView7name.setText("");
        textView9id.setText("");
        textView11email.setText("");

        firebaseMethod = new FirebaseMethod(this);

        databaseReference = firebaseMethod.getMyRef();

        databaseReference.child("user_details")
                .child(firebaseMethod.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                textViewUperName.setText(dataSnapshot.child("name").getValue().toString());
                textView7name.setText(dataSnapshot.child("name").getValue().toString());
                textView2UperEmail.setText(dataSnapshot.child("email").getValue().toString());
                textView11email.setText(dataSnapshot.child("email").getValue().toString());
                textView9id.setText(dataSnapshot.child("Nic").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
