package com.example.eventapptest2;


import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    ArrayList<Event> Explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomnav = findViewById(R.id.bottomNavView);
//        framelayout = findViewById(R.id.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,new UserProfileFragment());
        fragmentTransaction.commit();
        bottomnav.setSelectedItemId(R.id.UserProfileNav);

        bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.OrganizeEventNav){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout,new OrgainzersEventFragment());
                    fragmentTransaction.commit();
                    bottomnav.getMenu().clear();
                    bottomnav.inflateMenu(R.menu.organizer_nav_menu);
                    bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.OrginzersEventsnav), 100);
                }
                else if(itemId == R.id.UserProfileNav){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout,new UserProfileFragment());
                    fragmentTransaction.commit();
                }
                else if(itemId == R.id.ExploreEventNav){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout,new ExploreFragment());
                    fragmentTransaction.commit();
                }
                else if(itemId == R.id.SavedEventNav){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout,new SavedEventsFragment());
                    fragmentTransaction.commit();
                }
                else if(itemId == R.id.OldQrNav){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout,new OldQrsFragments());
                    fragmentTransaction.commit();
                }
                else if(itemId == R.id.AddEventnav){
                    //FragmentManager fragmentManager = getSupportFragmentManager();
                    AddEventFragment fragment = new AddEventFragment();
                    fragment.show(getSupportFragmentManager(), "add_event_fragment");
                }
                else if(itemId == R.id.OrginzersEventsnav){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout,new OrgainzersEventFragment());
                    fragmentTransaction.commit();
                }
                else if(itemId == R.id.GoBacknav){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout,new UserProfileFragment());
                    fragmentTransaction.commit();
                    bottomnav.getMenu().clear();
                    bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                    bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.UserProfileNav), 100);
                }
                else{FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout,new UserProfileFragment());
                    fragmentTransaction.commit();}




                return true;
            }
        });

    }
    public void switchToScanQRFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,new QRCameraScannerFragment());
        fragmentTransaction.commit();
    }
    public void switchToGenerateQRFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,new GeneratedQRFragment());
        fragmentTransaction.commit();
    }
}
