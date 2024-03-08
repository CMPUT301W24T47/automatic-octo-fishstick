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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrganizeEventDetsFragment} factory method to
 * create an instance of this fragment.
 */
public class OrganizeEventDetsFragment extends Fragment {
    Event event;

    /**
     * constructor
     * @param events
     */
    public OrganizeEventDetsFragment(Event events){
        event = events;
    }

    /**
     *
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
