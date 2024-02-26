package com.abhip.feedfolks;


import android.app.Activity;

import androidx.appcompat.app.AlertDialog;

public class ProgressLoader {
    private Activity mActivity;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    public ProgressLoader(Activity activity){
        mActivity = activity;
    }
    public void showLoader(){
        builder = new AlertDialog.Builder(mActivity);
        builder.setCancelable(false);
        builder.setView(R.layout.custom_progressbar);
        dialog = builder.create();
        dialog.show();
    }
    public void dismissLoader(){
        dialog.dismiss();
    }
}