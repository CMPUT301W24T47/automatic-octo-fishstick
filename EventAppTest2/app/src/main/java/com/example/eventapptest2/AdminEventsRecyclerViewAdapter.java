package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.AdminEventsFragmentsBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdminEventsRecyclerViewAdapter extends RecyclerView.Adapter<AdminEventsRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<Event> allevents;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdminEventsRecyclerViewAdapter(ArrayList<Event> alleventsinput) {
        allevents = alleventsinput;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AdminEventsFragmentsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //setting events text
//        int postini = position;
//
//        System.out.println("testtttttttttt " + allevents.get(postini));
//        holder.userName.setText(allevents.get(postini));
        holder.EventForView = allevents.get(position);
        holder.eventName.setText(holder.EventForView.getEventName());
        String imageURL = holder.EventForView.getEventPoster();

        Glide.with(holder.itemView.getContext())
                .load(imageURL)
                .into(holder.eventImage);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the event position in the recycler view when clicking delete
                int eventPosition = holder.getAbsoluteAdapterPosition();

                if(eventPosition != RecyclerView.NO_POSITION){
                    // When we click delete it gets those event detatils
                    Event deleteEvent = allevents.get(eventPosition);
                    String createEvents = "CreateEvents";
                    String ownerEvent = deleteEvent.getOwner();
//                    System.out.println(ownerEvent);

                    // Firebase deleting, delete from "ExploreEvents" and "CreateEvents
                    db.collection("ExploreEvents").document(deleteEvent.getEventid())
                                    .delete();
                    db.collection(createEvents+ownerEvent).document(deleteEvent.getEventid())
                                    .delete();
                    allevents.remove(deleteEvent);
                    notifyItemRemoved(eventPosition);

                    // From data base remove from ExploreEvents and CreatedEvents"deviceId" getting the owner ID
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return allevents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView eventName;
        public final ImageView eventImage;
        public final ImageView deleteButton;
        public Event EventForView;



        public ViewHolder(AdminEventsFragmentsBinding binding) {
            super(binding.getRoot());
            eventName = binding.adminEventsname;
            eventImage = binding.adminEventIcon;
            deleteButton = binding.adminDeletebtnEvents;

        }


        @Override
        public String toString() {
            return super.toString() + " '" + eventName.getText() + "'";
        }
    }
}