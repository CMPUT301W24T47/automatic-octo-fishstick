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

/**
 * This fragment represents the detail view of an event when clicking details in the explore event page.
 * The fragment displays information of the event and allows users to sign ip for the event.
 */

public class ACTUALExploreEventDetsFragment extends Fragment {
    Event event; // Event being displayed
    FragmentManager fragmentManager; // Fragment manager handling the fragments
    String deviceid; // Device ID of the user
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference saveevent; // Collection reference for SaveEvents with Device Id
    private User user; // Current user
    private BottomNavigationView bottomnav;
    private List<Event> exploreevents; // List of events in the Explore Events page

    /**
     * Constructor for the ExploreEventDetails fragment.
     *
     * @param events Displaying the event
     * @param fragment FragmentManager handling fragments
     * @param deviceidi Device ID of the user
     * @param userr Current user
     * @param bottomNavigationView Bottom navigation bar
     * @param items List of events the explore page
     */
    public ACTUALExploreEventDetsFragment(Event events, FragmentManager fragment, String deviceidi, User userr, BottomNavigationView bottomNavigationView,List<Event> items){
        event = events;
        fragmentManager = fragment;
        deviceid = deviceidi;
        saveevent = db.collection("SavedEvents"+deviceid);
        user = userr;
        bottomnav = bottomNavigationView;
        exploreevents = items;

    }

    /**
     * Create the view structure of the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return The view of inflated layout
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_signup_qr, container, false);
        // Initialize the views
        ImageView detimage = v.findViewById(R.id.EventImageQR);
        TextView detname = v.findViewById(R.id.EventDetailNameQR);
        TextView detdate = v.findViewById(R.id.CalenderDateQR);
        TextView detloc = v.findViewById(R.id.LocationNameQR);
        TextView detdesc = v.findViewById(R.id.EventDescriptionQR);
        Button but = v.findViewById(R.id.QRSignUpQR);

        // Set event details of the corresponding view
        detname.setText(event.getEventName());
        detdate.setText(event.getEventDate());
        detloc.setText(event.getEventLocation());
        detdesc.setText(event.getEventDesription());
        //Picasso.get().load(event.getEventPoster()).into(detimage);

        // Show the event poster using glide
        Glide.with(v.getContext())
                .load(event.getEventPoster())
                .into(detimage);

        // onClickListener for the signup button
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic for signing up for an event
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
                        newuser.put("deviceId",user.getDeviceId());
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

        // onClickListener for share event button
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
