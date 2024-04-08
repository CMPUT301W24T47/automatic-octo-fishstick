package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.AttendeeListFragmentBinding;
import com.example.eventapptest2.databinding.AttendeeNotificationFragmentBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * RecyclerView Adapter for displaying notifications to attendees.
 * This adapter binds data to ViewHolder, which displays the attendee notifications.
 *
 */
public class AttendeeNotifyRecyclerViewAdapter extends RecyclerView.Adapter<AttendeeNotifyRecyclerViewAdapter.ViewHolder> {
    private  ArrayList<String> NotficationList; // List of notifications
    private String EventImage;

    /**
     * Constructor for AttendeeNotifyRecyclerViewAdapter.
     *
     * @param Attendees List of attendees to display notifications too.
     * @param ei The image associated with the event.
     */
    public AttendeeNotifyRecyclerViewAdapter(ArrayList<String> Attendees,String ei) {
        NotficationList = Attendees;
        EventImage = ei;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(AttendeeNotificationFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    /**
     * Called by RecyclerView to display the data at specified positions.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Set notification text
        if(!NotficationList.isEmpty()){
            int postini = position;

            // Show the event image using Glide
            Glide.with(holder.itemView.getContext())
                    .load(EventImage)
                    .into(holder.Imageing);

            holder.userName.setText(NotficationList.get(postini));}


    }

    /**
     *
     * @return The number of notifications in the adapter.
     */
    @Override
    public int getItemCount() {
        return NotficationList.size();
    }

    /**
     * ViewHolder class that holds references to views within each item of the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView userName;
        public final ImageView Imageing;
        public String EventForView;

        public ViewHolder(AttendeeNotificationFragmentBinding binding) {
            super(binding.getRoot());
            userName = binding.attendeeEventNotify;
            Imageing = binding.attendeeEventIcon;
        }

        /**
         * @return A string of the ViewHolder object.
         */
        @Override
        public String toString() {
            return super.toString() + " '" + userName.getText() + "'";
        }
    }
}