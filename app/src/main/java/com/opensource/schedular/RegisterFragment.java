package com.opensource.schedular;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import util.DialogBox;
import util.FirebaseMethod;
import util.StringValidator;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "RegisterFragment";
    private String error = null;

    private FirebaseAuth mAuth;

    private View view = null;

    private ProgressBar progressBar;


    private EditText editTextName;
    private EditText editTextDeviceID;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonRegister;


    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        view = inflater.inflate(R.layout.fragment_register, container, false);


        editTextName = (EditText) view.findViewById(R.id.rgname);
        editTextDeviceID = (EditText) view.findViewById(R.id.rgdevice_id);
        editTextEmail = (EditText) view.findViewById(R.id.rgemail);
        editTextPassword = (EditText) view.findViewById(R.id.rgpassword);
        editTextConfirmPassword = (EditText) view.findViewById(R.id.rgcommfirmpassword);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        // set password hint when no txt
        editTextPassword.setHint("PASSWORD");
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (editTextPassword.getText().length() > 0)
                    editTextPassword.setHint("");
                else
                    editTextPassword.setHint("PASSWORD");
            }
        });

        // set confirm password hint when no txt
        editTextConfirmPassword.setHint("CONFIRM PASSWORD");
        editTextConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (editTextConfirmPassword.getText().length() > 0)
                    editTextConfirmPassword.setHint("");
                else
                    editTextConfirmPassword.setHint("CONFIRM PASSWORD");
            }
        });
        //

        buttonRegister = (Button) view.findViewById(R.id.registerBtn);
        buttonRegister.setOnClickListener(this);


        return  view;
    }

    @Override
    public void onClick(View view)
    {
       switch (view.getId()){

            case R.id.registerBtn:
                if(validator()){
                    register(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim());
                }else{
                    DialogBox dBox =  new DialogBox();
                    dBox.ViewDialogBox(view,"Please Check !",this.error);
                }
                break;
            default:
                break;
        }
    }


    public boolean validator(){
        boolean valiate = true;
        this.error ="";
        String name = this.editTextName.getText().toString().trim();
        String id = this.editTextDeviceID.getText().toString().trim();
        String email = this.editTextEmail.getText().toString().trim();
        String password = this.editTextPassword.getText().toString().trim();
        String comfirmPassword = this.editTextConfirmPassword.getText().toString().trim();


        StringValidator sValidator = new StringValidator();

        if(sValidator.isEmptyString(name)){
            valiate = false;
            this.error = this.error+"Name,";
        }

        if(sValidator.isEmptyString(id)){
            valiate = false;
            this.error = this.error+" ID,";
        }

        if(sValidator.isEmptyString(email)){
            valiate = false;
            this.error = this.error+" Email,";
        }else {
            if(!sValidator.isValidEmailAddress(email)){
                this.error = this.error +" Invalid Email,";
            }
        }

        if(sValidator.isEmptyString(password)){
            valiate = false;
            this.error = this.error+" Password,";
        }

        if(sValidator.isEmptyString(comfirmPassword)){
            valiate = false;
            this.error = this.error+" Confirm Password,";
        }else{
            if(!password.equals(comfirmPassword)) {
                valiate = false;
                this.error = this.error + " Invalid Confirmation Password";
            }
        }
        return valiate;
    }


    public void register(String email,String password){

        Log.d(TAG,"Register Function call");

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(getActivity().getApplicationContext(),"User Create",Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            insertUSerData(user.getUid());
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            DialogBox dBox = new DialogBox();
                            dBox.ViewDialogBox(view,"Wrong!",task.getException().getMessage());
                        }

                    }
                });
    }


    public void insertUSerData(String uui){
        FirebaseMethod.User us = new FirebaseMethod.User(editTextName.getText().toString(),editTextEmail.getText().toString(),"","000-000 000",editTextDeviceID.getText().toString());
        FirebaseMethod firebaseMethods = new FirebaseMethod(getActivity().getApplicationContext());
        firebaseMethods.userRegister(uui,us);
        progressBar.setVisibility(View.GONE);
    }


}
