package com.example.eventapptest2;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class ExploreEventDetsFragment extends Fragment {
    Event event;
    public ExploreEventDetsFragment(Event events){
        event = events;
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
        detname.setText(event.getEventName());
        detdate.setText(event.getEventDate());
        detloc.setText(event.getEventLocation());
        detdesc.setText(event.getEventDesription());
        Picasso.get().load(event.getEventPoster()).into(detimage);
        //set the share button make it functional








        return v;
    }
}
