package com.example.eventapptest2;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.AttendeeListFragmentBinding;
import com.example.eventapptest2.databinding.FragmentExploreBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * RecyclerView Adapter for displaying a list of users attending the event.
 * Adapter binds attendee information to the ViewHolder.
 */
public class AttendeeListRecyclerViewAdapter extends RecyclerView.Adapter<AttendeeListRecyclerViewAdapter.ViewHolder> {
    private  ArrayList<User> AttendeeList; // List of the attendees

    /**
     * Constructor for the AttendeeListRecyclerViewAdapter.
     *
     * @param Attendees List of attendees in the event to be displayed.
     */
    public AttendeeListRecyclerViewAdapter(ArrayList<User> Attendees) {
        AttendeeList = Attendees;
    }

    /**
     * Called when RecyclerView needs a ViewHolder from the given type to represent the item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a AttendeeListFragmentBinding.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AttendeeListFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    /**
     * Called by RecyclerView to display the data of the attendee at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int postini = position;
        holder.EventForView = AttendeeList.get(position);
        holder.userName.setText(holder.EventForView.getUserName()); // Set attendee name
        String imageUrl = holder.EventForView.getUserProfileImage();

        holder.EventForView = AttendeeList.get(position);
        if(holder.EventForView.getUserName().equals("") || holder.EventForView.getUserName().equals(" ")) {
            holder.userName.setText(holder.EventForView.getDeviceId());
        }
        else {
            holder.userName.setText(holder.EventForView.getUserName());
        }

        // when there is name is not empty and image is empty
        if (!Objects.equals(holder.EventForView.getUserName(), "") &&(Objects.equals(holder.Imageing.getBackground(),null))&& ((Objects.equals(imageUrl, ""))||(Objects.equals(imageUrl, null)))){
            holder.adminNameOnImage.setText(holder.EventForView.getUserName());
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.Imageing.setBackgroundColor(color);
        }

        // Display attendee profile image with Glide
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.Imageing);

        if (Integer.parseInt( holder.EventForView.getCheckInCount()) == 0){
            holder.Status.setText("Not Signed-in");
            holder.count.setText("Check-in Count: 0");
        }else{holder.Status.setText("Signed-in");
            holder.count.setText("Check-in Count: "+holder.EventForView.getCheckInCount());}


    }

    /**
     *
     * @return The total number of attendees in the event.
     */
    @Override
    public int getItemCount() {
        return AttendeeList.size();
    }

    /**
     * ViewHolder class for holding the views of the attendee list.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView userName;
        public final TextView Status;
        public final TextView count;
        public final ImageView Imageing;
        public User EventForView;
        public final TextView adminNameOnImage;

        /**
         * Constructor for the ViewHolder.
         *
         * @param binding The AttendeeListFragmentBinding instance for the ViewHolder's layout.
         */
        public ViewHolder(AttendeeListFragmentBinding binding) {
            super(binding.getRoot());
            userName = binding.attendeeNameList;
            Status = binding.CheckText;
            Imageing = binding.ProfilePicList;
            count = binding.CheckInCountList;
            adminNameOnImage = binding.nameOnImageAttendee;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + userName.getText() + "'";
        }
    }
}