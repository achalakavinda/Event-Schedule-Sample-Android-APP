package util;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseMethod {
    private static final String TAG = "FirebaseMethods";

    FirebaseMethod.User user =null;

    @IgnoreExtraProperties
    public static class User {

        public String name;
        public String email;
        public String imgUrl;
        public String contact;
        public String Nic;

        public User(String name, String email,String imgUrl, String contact,String Nic) {
            this.name = name;
            this.email = email;
            this.imgUrl = imgUrl;
            this.contact = contact;
            this.Nic = Nic;
        }

    }


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;
    private String userID;

    //vars
    private Context mContext;
    private double mPhotoUploadProgress = 0;


    public FirebaseMethod(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }



    public void userRegister(FirebaseMethod.User us){
        myRef.child("user_details").child(userID).setValue(us);
    }

    public void userRegister(String userID,FirebaseMethod.User us){
        myRef.child("user_details").child(userID).setValue(us);
    }

}
