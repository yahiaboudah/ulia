package com.bouda.ulia.activites.navigators;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bouda.ulia.R;
import com.bouda.ulia.activites.sensors.LocationProvider;
import com.bouda.ulia.permissions.UliaPermissions;
import com.bouda.ulia.uielements.UliaNavBar;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements
        LocationProvider.LocationCallback
{

    private final String TAG = "MainActivity";
    private LocationProvider locationProvider;
    private TextView txt;

    private void requestPermissions(){
        registerForActivityResult( new ActivityResultContracts.RequestMultiplePermissions(),
                UliaPermissions.onResultCallBack(MainActivity.this)
        ).launch(UliaPermissions.ALL_PERMISSIONS);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        requestPermissions();
        locationProvider = new LocationProvider(this, this);

        setContentView(R.layout.activity_main);
        new UliaNavBar(findViewById(R.id.bnb));

        TextView txt = findViewById(R.id.text);
    }

    @Override
    protected void onResume(){
        super.onResume();
        locationProvider.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationProvider.disconnect();
    }

    @Override
    public void onNewLocation(Location location) {
        txt = findViewById(R.id.text);
        Log.i("SPECIAL", location.toString());
        txt.setText(String.format("%s, %s", location.getLatitude(), location.getLongitude()));
    }
}