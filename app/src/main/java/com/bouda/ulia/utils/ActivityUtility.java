package com.bouda.ulia.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ActivityUtility {

    public static void setFragment(FragmentManager fragmentManager, Fragment fragment, int frameID){
        fragmentManager.beginTransaction().replace(frameID, fragment).commit();
    }
}
