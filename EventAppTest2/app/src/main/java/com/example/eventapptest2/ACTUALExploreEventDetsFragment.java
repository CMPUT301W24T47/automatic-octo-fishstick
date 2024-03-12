package com.example.eventapptest2;
import com.example.eventapptest2.Event;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ACTUALExploreEventDetsFragment extends Fragment {
    Event event;
    FragmentManager fragmentManager;
    String deviceid;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference saveevent;
    private User user;
    private BottomNavigationView bottomnav;
    private List<Event> exploreevents;
    public ACTUALExploreEventDetsFragment(Event events, FragmentManager fragment, String deviceidi, User userr, BottomNavigationView bottomNavigationView,List<Event> items){
        event = events;
        fragmentManager = fragment;
        deviceid = deviceidi;
        saveevent = db.collection("SavedEvents"+deviceid);
        user = userr;
        bottomnav = bottomNavigationView;
        exploreevents = items;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_signup_qr, container, false);
        ImageView detimage = v.findViewById(R.id.EventImageQR);
        TextView detname = v.findViewById(R.id.EventDetailNameQR);
        TextView detdate = v.findViewById(R.id.CalenderDateQR);
        TextView detloc = v.findViewById(R.id.LocationNameQR);
        TextView detdesc = v.findViewById(R.id.EventDescriptionQR);
        Button but = v.findViewById(R.id.QRSignUpQR);

        detname.setText(event.getEventName());
        detdate.setText(event.getEventDate());
        detloc.setText(event.getEventLocation());
        detdesc.setText(event.getEventDesription());
        Picasso.get().load(event.getEventPoster()).into(detimage);

        //set the share button make it functional
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CollectionReference attendelistfb = db.collection("AttendeeList" + event.getEventid());
                //we should check if the event is full or not first // or this can be another filter on main where explore list has no full events in it

                ArrayList<Event> newlist =  user.getSavedEvents();
                //newlist.add(event); //we should instead create a attendee specfic event which only stores notfications
                //user.setSavedEvents(newlist);
                // remove the event from the useres explore list as well not with db tho

                saveevent.document(event.getEventid()).set(event); // we should be adding a attendee version of event here so no attendee lists only a notfication list


                Map<String, Object> newuser = new HashMap<>();
                newuser.put("userName", user.getUserName());
                newuser.put("userProfileImage", user.getUserProfileImage());
                newuser.put("CheckInCount", "0");


                attendelistfb.document(user.getDeviceId()).set(newuser); //databse is being funny should directly create keys



                exploreevents.remove(event);
                bottomnav.getMenu().clear();
                bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.ExploreEventNav), 100);

                //we should change the fragment to savedEvents after as well remove the event from the users explore list
                //I think if handled in main it will work automatically

            }
        });







        return v;
    }
}
