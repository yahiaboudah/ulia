package com.bouda.ulia.utils;

import android.content.Context;
import android.widget.Toast;

public class Misc {

    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
