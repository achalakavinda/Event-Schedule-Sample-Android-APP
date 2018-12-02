package com.opensource.schedular;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import util.DialogBox;
import util.StringValidator;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "LoginFragment";
    private  String error ="";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private Button loginButton;
    private View view;
    private ProgressBar progressBar;

    private EditText editTextUsername;
    private EditText editTextPassword;

    private TextView forgetpasswordTextView;

    public LoginFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextUsername =  view.findViewById(R.id.usernamelogin);
        editTextPassword =  view.findViewById(R.id.passwordlogin);
        loginButton =  view.findViewById(R.id.loginBtn);

        forgetpasswordTextView = view.findViewById(R.id.forgetpasswordTextView);
        forgetpasswordTextView.setOnClickListener(this);

        loginButton.setOnClickListener(this);

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

        mAuth = FirebaseAuth.getInstance();
        //
        return view;

    }


    @Override
    public void onClick(View view)
    {

        Toast.makeText(getActivity(),"Please Check Fields",Toast.LENGTH_SHORT);

        switch (view.getId()){
            case R.id.loginBtn:
                Log.d(TAG,"Onclick Login Button");
                loginFn();
                break;
            case R.id.forgetpasswordTextView:
                openDialog();
                break;
            default:
                break;
        }
    }

    public void openDialog(){
        InputDialog inputDialog = new InputDialog();
        Bundle args = new Bundle();
        args.putString("type","this not null");
        args.putString("value","Please Enter email to reset you're password");
        inputDialog.setArguments(args);
        inputDialog.show(getChildFragmentManager(),"");
    }

    private void pushHome(){
        Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    public void loginFn() {
        init();
    }

    private void init(){
        String email = editTextUsername.getText().toString();
        String password =  editTextPassword.getText().toString();
        if(!isStringNull(email,1) || !isStringNull(password,2)){
            Log.d(TAG,"yes this is call");
            new DialogBox().ViewDialogBox(view,"Please Check",error);
        }else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Log.d(TAG, "signInWithEmail:success");
                                pushHome();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                alert("signInWithEmail:failure");
                                DialogBox Dbox = new DialogBox();
                                Dbox.ViewDialogBox(view,"Login Fail",task.getException().getMessage().toString());
                            }

                        }
                    });
        }
    }

    //field validator
    private boolean isStringNull(String string,int con){
        StringValidator sValidate = new StringValidator();
        switch (con){
            case 1:
                if(sValidate.isEmptyString(string)){
                    error = error+"Email ,";
                    return false;
                }
                if(!sValidate.isValidEmailAddress(string)){
                    error = error+"Invalid Email,";
                    return false;
                }
                break;
            case 2:
                if(sValidate.isEmptyString(string)){
                    error = error+"Password.";
                    return false;
                }
                break;
        }
        return  true;
    }

    //alert method
    private void alert(String str){
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_LONG);
        toast.show();
    }

}
