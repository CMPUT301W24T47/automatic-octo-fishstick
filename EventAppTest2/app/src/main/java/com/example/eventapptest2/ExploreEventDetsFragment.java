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

import com.squareup.picasso.Picasso;

/**
 * a {@link Fragment} subclass
 * {@link ExploreEventDetsFragment} displays a fragment showing
 * the event details (name, location, description, etc.)
 */
public class ExploreEventDetsFragment extends Fragment {
    Event event;
    FragmentManager fragmentManager;

    /**
     * initializes the fragment to display the event made
     * @param events
     * @param fragment
     */
    public ExploreEventDetsFragment(Event events,FragmentManager fragment){
        event = events;
        fragmentManager = fragment;
    }

    /**
     * creates a view based on the information given by the event
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the view of the event to display
     */
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
                fragmentTransaction.replace(R.id.framelayout,new QRCameraScannerFragment());
                fragmentTransaction.commit();

            }
        });







        return v;
    }
}
