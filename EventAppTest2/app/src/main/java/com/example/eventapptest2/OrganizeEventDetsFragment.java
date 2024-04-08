package com.example.eventapptest2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class OrganizeEventDetsFragment extends Fragment {
    Event event;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    User user;

    /**
     *
     * @param events
     * @param userr
     */
    public OrganizeEventDetsFragment(Event events,User userr){
        event = events;
        user = userr;
    }

    /**
     * A fragment that shows organizers the details of their event. This is unique from the
     * attendee view- it allows for creation of QR codes and revokes the ability to check-in.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_detail, container, false);
        ImageView detimage = v.findViewById(R.id.EventImageOrgdet);
        TextView detname = v.findViewById(R.id.EventDetailNameOrgdet);
        TextView detdate = v.findViewById(R.id.CalenderDateOrgdet);
        TextView detloc = v.findViewById(R.id.LocationNameOrgdet);
        TextView detdesc = v.findViewById(R.id.EventDescriptionOrgdet);
        TextView detlim = v.findViewById(R.id.AttendeeLimitNumOrgdet);
        Switch geolocation = v.findViewById(R.id.GeoLocationButtonOrgdet);

        detlim.setText(event.getEventLimit());
        detname.setText(event.getEventName());
        detdate.setText(event.getEventDate());
        detloc.setText(event.getEventLocation());
        detdesc.setText(event.getEventDesription());
        //Picasso.get().load(event.getEventPoster()).into(detimage);



        Glide.with(v.getContext())
                .load(event.getEventPoster())
                .into(detimage);
        //set the share button make it functional
        ImageView checkinsharebut = v.findViewById(R.id.ShareQRIconOrgdet);



    if(event.getTracking() instanceof String){
        if (event.getTracking().equals("on")){
            geolocation.setChecked(true);
        }}



        geolocation.setOnClickListener(new View.OnClickListener() {
            /**
             * Upon checking geolocation, this will require users to also enable geolocation
             * in order to sign up for event.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if (geolocation.isChecked()){
                    event.setTracking("on");
                    //update explore,created, saved is delt with by explore
                    final CollectionReference eventsRef = db.collection("ExploreEvents");
                    final CollectionReference createeventsRef = db.collection("CreateEvents" + user.getDeviceId());
                    eventsRef.document(event.getEventid()).set(event);
                    createeventsRef.document(event.getEventid()).set(event);

                }
                else{
                    event.setTracking("off");
                    //update explore,created, saved is delt with by explore
                    final CollectionReference eventsRef = db.collection("ExploreEvents");
                    final CollectionReference createeventsRef = db.collection("CreateEvents" + user.getDeviceId());
                    eventsRef.document(event.getEventid()).set(event);
                    createeventsRef.document(event.getEventid()).set(event);
                }
            }
        });

        checkinsharebut.setOnClickListener(new View.OnClickListener() {
            /**
             * Allows organizer to share the QR code via other applications
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//// Example: content://com.google.android.apps.photos.contentprovider/...
//                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(event.getSignINQR()));
//                shareIntent.setType("image/jpeg");
//                startActivity(Intent.createChooser(shareIntent, null));


                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, event.getSignINQR());

// (Optional) Here you're setting the title of the content
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Introducing content previews");

// (Optional) Here you're passing a content URI to an image to be displayed
                sendIntent.setData(Uri.parse(event.getSignINQR()));
                sendIntent.setType("image/jpeg");
                sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

// Show the Sharesheet
                startActivity(Intent.createChooser(sendIntent, null));



            }
        });

        ImageView postersharebut = v.findViewById(R.id.ShareIconOrgdet);

        postersharebut.setOnClickListener(new View.OnClickListener() {
            /**
             * Allows organizer to share the event poster/details via other applications
             * @param v The view that was clicked.
             */
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
