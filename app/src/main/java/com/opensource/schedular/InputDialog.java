package com.opensource.schedular;

import android.app.AlertDialog;
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
    private String Description = "";
    private String Year_id = "";
    private String Time_id = "";
    private String id = "";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        this.id = getArguments().getString("id");
        this.Year_id = getArguments().getString("Year_id");
        this.Time_id = getArguments().getString("Time_id");
        this.Title = getArguments().getString("value");
        this.Description = getArguments().getString("description");



        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);


        editTextEvent = view.findViewById(R.id.editTextEvent);
        editTextEvent.setText(this.Description);

        builder.setView(view).setTitle(Title).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(id == null){
                    inputDialogListener.applyText(editTextEvent.getText().toString());
                    System.out.println("add text call");
                }else {
                    inputDialogListener.editText(id,Year_id,Time_id,editTextEvent.getText().toString());
                    System.out.println("update text call");
                }
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
        void editText(String id, String year_id,String time_id,String description);
    }
}
