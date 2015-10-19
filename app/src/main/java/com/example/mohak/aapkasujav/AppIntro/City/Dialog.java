package com.example.mohak.aapkasujav.AppIntro.City;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


/**
 * Created by mohak on 24/8/15.
 */
public class Dialog extends DialogFragment {


    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle("Complain Registered Successfully")
                .setMessage("You will be contacted soon by the concerned department." +
                        "If no action " +
                        "is taken within 24 hours then email us at blah@gmail.com")
                .setPositiveButton("OK", null)
                .create();
    }
}
