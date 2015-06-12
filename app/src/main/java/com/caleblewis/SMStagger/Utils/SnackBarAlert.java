package com.caleblewis.SMStagger.Utils;

import android.app.Activity;
import android.graphics.Color;

import com.rey.material.widget.SnackBar;

public class SnackBarAlert {
    Activity activity;

    public SnackBarAlert(Activity activity) {
        this.activity = activity;
    }

    public void show(String message){
        new SnackBar(activity)
                .textColor(Color.WHITE)
                .verticalPadding(35)
                .textSize(15)
                .horizontalPadding(40)
                .text(message)
                .singleLine(true)
                .duration(4000)
                .show(activity);
    }
}
