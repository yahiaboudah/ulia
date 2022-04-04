package com.bouda.ulia.permissions;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.RequiresApi;

import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class UliaPermissions {

    public static final String[] ALL_PERMISSIONS = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO
    };

    public static ActivityResultCallback<Map<String, Boolean>> onResultCallBack(Context context){
        return result -> result.forEach((k, v) -> Log.i("PERMISSIONS", k + ":" + v));
    }

}
