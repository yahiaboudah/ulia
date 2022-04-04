
package com.bouda.ulia.activites_ulia.flame;
import android.util.Log;

import com.bouda.ulia.entities.WObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
* Flame maker is the context provider, it is what collects context
* The flame is sort of like a Kronot, it comes from the outside instead of the inside
* of the room. (It is basically the context in which in action shall or must be executed!)
* */

public class FlameMaker {

    private final String TAG = this.getClass().getSimpleName();
    private double budget;

    public FlameMaker(){

    }

    private Date getTime(){
        Log.i(TAG, String.valueOf(Calendar.getInstance().getTime()));
        return Calendar.getInstance().getTime();
    }

    private double getBudget(){
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    private static List<WObject> ownedWobjects = new ArrayList<>();

    public static void addWobject(WObject wObject){
        ownedWobjects.add(wObject);
    }

    public static List<WObject> getOwnedWobjects() {
        return ownedWobjects;
    }

}