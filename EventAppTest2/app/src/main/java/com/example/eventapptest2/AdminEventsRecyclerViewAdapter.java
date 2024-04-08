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
 * A RecyclerView Adapter for displaying all current events the Admin can view.
 * The Adapter binds the event information to the corresponding views in the RecyclerView.
 */
public class AdminEventsRecyclerViewAdapter extends RecyclerView.Adapter<AdminEventsRecyclerViewAdapter.ViewHolder> {
    private  ArrayList<Event> allevents; // List of all current events
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Constructor for the AdminEventsRecyclerViewAdapter.
     *
     * @param alleventsinput List of all the current events to display
     */
    public AdminEventsRecyclerViewAdapter(ArrayList<Event> alleventsinput) {
        allevents = alleventsinput;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AdminEventsFragmentsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Bind the event details to the views in the ViewHolder
        holder.EventForView = allevents.get(position);
        holder.eventName.setText(holder.EventForView.getEventName());
        String imageURL = holder.EventForView.getEventPoster();

        // Load event image using Glide
        Glide.with(holder.itemView.getContext())
                .load(imageURL)
                .into(holder.eventImage);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the event position in the Adapter
                int eventPosition = holder.getAbsoluteAdapterPosition();

                if(eventPosition != RecyclerView.NO_POSITION){
                    // When we click delete it gets those event details
                    Event deleteEvent = allevents.get(eventPosition);
                    String createEvents = "CreateEvents";
                    String ownerEvent = deleteEvent.getOwner();

                    // Firebase deleting, delete from "ExploreEvents" and "CreateEvents
                    db.collection("ExploreEvents").document(deleteEvent.getEventid())
                            .delete();
                    db.collection(createEvents+ownerEvent).document(deleteEvent.getEventid())
                            .delete();

                    // Remove the event from the list and notify the adapter
                    allevents.remove(deleteEvent);
                    notifyItemRemoved(eventPosition);

                    // In the database remove from ExploreEvents and CreatedEvents"deviceId" getting the owner ID
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return allevents.size();
    }

    /**
     * ViewHolder class for holding the views associated with the event.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView eventName;
        public final ImageView eventImage;
        public final ImageView deleteButton;
        public Event EventForView;

        /**
         * Constructor for the ViewHolder class.
         *
         * @param binding Binding object contains the views for each item
         */
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