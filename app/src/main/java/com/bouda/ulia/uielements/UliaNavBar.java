package com.bouda.ulia.uielements;

import static com.bouda.ulia.utils.Misc.toast;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UliaNavBar {

    private final BottomNavigationView bottomNav;

    private final NavigationBarView.OnItemSelectedListener listener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            toast(bottomNav.getContext(), "navigating to: " + item.getTitle());
            Fragment selFragment = null;
            if(selFragment != null) {
                // ActivityUtility.setFragment(fragmentManager, selFragment , R.id.fragment_container);
                return true;
            }
            return false;
        }
    };

    public UliaNavBar(BottomNavigationView bottomNavigationView){
        this.bottomNav = bottomNavigationView;
        this.setListener(this.listener);
        this.bottomNav.setBackground(null);
        this.bottomNav.getMenu().getItem(2).setEnabled(false);
    }

    private void setListener(NavigationBarView.OnItemSelectedListener listener) {
        this.bottomNav.setOnItemSelectedListener(listener);
    }
}