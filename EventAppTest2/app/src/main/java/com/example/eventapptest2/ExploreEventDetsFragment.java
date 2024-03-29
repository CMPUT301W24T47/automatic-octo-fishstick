package com.example.eventapptest2;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class ExploreEventDetsFragment extends Fragment {
    Event event;
    FragmentManager fragmentManager;
    User user;
    BottomNavigationView bottomnavi;
    public ExploreEventDetsFragment(Event events,FragmentManager fragment,User usere,BottomNavigationView bottomnav){
        event = events;
        fragmentManager = fragment;
        user = usere;
        bottomnavi = bottomnav;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_signup, container, false);
        ImageView detimage = v.findViewById(R.id.EventImageSignup);
        TextView detname = v.findViewById(R.id.EventDetailNameSignup);
        TextView detdate = v.findViewById(R.id.CalenderDateUp);
        TextView detloc = v.findViewById(R.id.LocationNameUp);
        TextView detdesc = v.findViewById(R.id.EventDescriptionUp);
        Button but = v.findViewById(R.id.QRSignUpbut);

        detname.setText(event.getEventName());
        detdate.setText(event.getEventDate());
        detloc.setText(event.getEventLocation());
        detdesc.setText(event.getEventDesription());
        Picasso.get().load(event.getEventPoster()).into(detimage);

        //set the share button make it functional
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,new QRCameraScannerFragment(event,user,fragmentManager,bottomnavi));
                fragmentTransaction.commit();

            }
        });







        return v;
    }
}
