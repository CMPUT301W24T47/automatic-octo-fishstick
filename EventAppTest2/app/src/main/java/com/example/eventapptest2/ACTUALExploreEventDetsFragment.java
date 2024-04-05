package com.example.eventapptest2;
import com.bumptech.glide.Glide;
import com.example.eventapptest2.Event;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        //Picasso.get().load(event.getEventPoster()).into(detimage);


        Glide.with(v.getContext())
                .load(event.getEventPoster())
                .into(detimage);


        //set the share button make it functional

        //make the limit work


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (event.getEventLimit().equals("")) {

                    final CollectionReference attendelistfb = db.collection("AttendeeList" + event.getEventid());

                    ArrayList<Event> newlist = user.getSavedEvents();

                    saveevent.document(event.getEventid()).set(event);

                    Map<String, Object> newuser = new HashMap<>();
                    newuser.put("userName", user.getUserName());
                    newuser.put("userProfileImage", user.getUserProfileImage());
                    newuser.put("CheckInCount", "0");
                    newuser.put("userLongitude", "a");
                    newuser.put("userLatitude", "a");


                    attendelistfb.document(user.getDeviceId()).set(newuser);

                    exploreevents.remove(event);
                    bottomnav.getMenu().clear();
                    bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                    bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.ExploreEventNav), 100);

                }
                else{
                    if ((event.getAttendeList().size() + 1 <= Integer.parseInt(event.getEventLimit()))){

                        final CollectionReference attendelistfb = db.collection("AttendeeList" + event.getEventid());

                        ArrayList<Event> newlist = user.getSavedEvents();

                        saveevent.document(event.getEventid()).set(event);

                        Map<String, Object> newuser = new HashMap<>();
                        newuser.put("userName", user.getUserName());
                        newuser.put("userProfileImage", user.getUserProfileImage());
                        newuser.put("CheckInCount", "0");
                        newuser.put("userLongitude", "a");
                        newuser.put("userLatitude", "a");

                        attendelistfb.document(user.getDeviceId()).set(newuser);

                        exploreevents.remove(event);
                        bottomnav.getMenu().clear();
                        bottomnav.inflateMenu(R.menu.bottom_nav_menu);
                        bottomnav.postDelayed(() -> bottomnav.setSelectedItemId(R.id.ExploreEventNav), 100);}

                    else {

                        Toast.makeText(getActivity().getApplicationContext(), "The Event is Full", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });



        ImageView postersharebut = v.findViewById(R.id.ShareIconQR);

        postersharebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//// Example: content://com.google.android.apps.photos.contentprovider/...
//                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(event.getSignINQR()));
//                shareIntent.setType("image/jpeg");
//                startActivity(Intent.createChooser(shareIntent, null));


                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, event.getQrUrl());

// (Optional) Here you're setting the title of the content
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Introducing content previews");

// (Optional) Here you're passing a content URI to an image to be displayed
                sendIntent.setData(Uri.parse(event.getQrUrl()));
                sendIntent.setType("image/jpeg");
                sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

// Show the Sharesheet
                startActivity(Intent.createChooser(sendIntent, null));



            }
        });






        return v;
    }
}
