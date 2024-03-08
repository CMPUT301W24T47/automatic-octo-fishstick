package com.example.eventapptest2;

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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ACTUALExploreEventDetsFragment extends Fragment {
    Event event;
    FragmentManager fragmentManager;
    String deviceid;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference saveevent;
    private User user;
    public ACTUALExploreEventDetsFragment(Event events, FragmentManager fragment,String deviceidi,User userr){
        event = events;
        fragmentManager = fragment;
        deviceid = deviceidi;
        saveevent = db.collection("SavedEvents"+deviceid);
        user = userr;

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
                ArrayList<Event> newlist =  user.getSavedEvents();
                newlist.add(event);
                user.setSavedEvents(newlist);
                saveevent.add(event);
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.framelayout,new QRCameraScannerFragment());
//                fragmentTransaction.commit();

            }
        });







        return v;
    }
}
