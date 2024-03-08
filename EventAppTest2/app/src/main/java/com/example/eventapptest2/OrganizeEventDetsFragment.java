package com.example.eventapptest2;

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

public class OrganizeEventDetsFragment extends Fragment {
    Event event;

    public OrganizeEventDetsFragment(Event events){
        event = events;
    }
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
        detlim.setText(event.getEventLimit());
        detname.setText(event.getEventName());
        detdate.setText(event.getEventDate());
        detloc.setText(event.getEventLocation());
        detdesc.setText(event.getEventDesription());
        Picasso.get().load(event.getEventPoster()).into(detimage);
        //set the share button make it functional








        return v;
    }
}
