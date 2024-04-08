package com.example.eventapptest2;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OragnizersNotificationFragment extends Fragment {
    Event event;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public OragnizersNotificationFragment(Event events){
        event = events;



    }

    /**
     * Creates a view that allows organizers to send notifications to all in their attendee list
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
        View v = inflater.inflate(R.layout.organizer_event_notification, container, false);

        EditText detname = v.findViewById(R.id.OrganizerNotifyEditText);

        Button but = v.findViewById(R.id.OrganizerNotifyBtn);

        but.setOnClickListener(new View.OnClickListener() {
            /**
             * Sends notification that was typed by organizer
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                CollectionReference notifys = db.collection("Notify"+event.getEventid());
                Map<String, Object> note = new HashMap<>();
                note.put("note", detname.getText().toString());



                notifys.add(note);

                detname.setText("");

            }
        });





        //set the share button make it functional








        return v;
    }
}
