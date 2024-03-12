package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.FragmentOldQrsBinding;
import com.example.eventapptest2.databinding.FragmentOrgainzersEventBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OldEventsRecyclerViewAdapter extends RecyclerView.Adapter<OldEventsRecyclerViewAdapter.ViewHolder> {

    private final List<Event> events;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String id;
    public OldEventsRecyclerViewAdapter(List<Event> items,String did) {
        events = items;
        id = did;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOldQrsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.EventForView = events.get(position);
        holder.ExploreEventName.setText(events.get(position).getEventName());
        holder.Eventdate.setText(events.get(position).getEventDate());
        holder.Eventlocation.setText(events.get(position).getEventLocation());
        //String imageUrl = "https://firebasestorage.googleapis.com/v0/b/charlie-kim-fans.appspot.com/o/event_images%2F7aa31d9e-1539-49f9-bc21-4ec823cdbfdb?alt=media&token=7afc1c14-11f4-48c7-8ab1-99ee1e96eaa4";
        Picasso.get().load(events.get(position).getEventPoster()).into(holder.Imageing);
        String imageUrl = events.get(position).getEventPoster();
        System.out.println("Image URL: " + imageUrl);
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.Imageing);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we need a way to get a new date... rn our code in main will just not care abt this so we need change the date



                final CollectionReference createeventsRef = db.collection("CreateEvents" + id);
                final CollectionReference OldQreventsRef = db.collection("OldQrsList" + id);
                // well need to do something like we did in main to get data for the attende list and check if length is < the length of limit
                createeventsRef.document(holder.EventForView.getEventid()).set(holder.EventForView);
                OldQreventsRef.document(holder.EventForView.getEventid()).delete();
                //events.remove(position);
            }
        });

    }

    @Override
    public int getItemCount() {

        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView ExploreEventName;
        public final TextView Eventlocation;
        public final TextView Eventdate;
        public final ImageView Imageing;
        public Event EventForView;
        public Button button;


        public ViewHolder(@NonNull FragmentOldQrsBinding binding) {
            super(binding.getRoot());
            ExploreEventName = binding.ExploreEventTitlee;
            Eventlocation = binding.ExploreEventlocatione;
            Eventdate = binding.ExploreEventDatee;
            Imageing = binding.ExploreuserImagee;
            button = binding.ExploreEventDetialsButtone;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + Eventdate.getText() + "'";
        }
    }
}