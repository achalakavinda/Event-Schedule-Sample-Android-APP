package com.opensource.schedular;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class InputDialog extends AppCompatDialogFragment {

    private EditText editTextEvent;
    private InputDialogListener inputDialogListener;

    private String Title = "";

   public InputDialog(){}


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        this.Title = getArguments().getString("value");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        editTextEvent = view.findViewById(R.id.editTextEvent);

        builder.setView(view).setTitle(Title).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputDialogListener.applyText(editTextEvent.getText().toString());
            }
        });



        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            inputDialogListener = (InputDialogListener) context;
        }catch (ClassCastException e){
            System.out.println(e.getMessage());
        }
    }

    public interface InputDialogListener{
        void applyText(String eventDesc);
    }
}
