package com.opensource.schedular;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;



/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    final MainActivity mainActivity = (MainActivity) getActivity();
    private static final String TAG = "LoginFragment";
    private String error = null;




    private View view = null;

    private EditText editTextName;
    private EditText editTextDeviceID;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonRegister;


    @Override
    public void onStart() {
        super.onStart();
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
        //        new

//
       switch (view.getId()){

            case R.id.registerBtn:
                // new
                registerFn();
                break;
            default:
                break;
        }
    }


    public void registerFn() {

    }

}
