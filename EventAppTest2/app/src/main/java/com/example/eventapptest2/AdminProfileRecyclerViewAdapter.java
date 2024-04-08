package com.example.eventapptest2;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.AdminProfilesFragmentBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * RecyclerView Adapter for displaying user profiles the Admin can view.
 * Adapter bind data of user names and user profile images.
 */
public class AdminProfileRecyclerViewAdapter extends RecyclerView.Adapter<AdminProfileRecyclerViewAdapter.ViewHolder> {
    private  ArrayList<User> userList; // List of all user profiles

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // CollectionReference will store the collection of Users
    //  - Which then each user will store different information
    private final CollectionReference usersRef = db.collection("users");

    /**
     * Constructs a new AdminProfileRecyclerViewAdapter with the provided inputed list of user profiles.
     *
     * @param allUsers Receives list of user profiles to be displayed
     */
    public AdminProfileRecyclerViewAdapter(ArrayList<User> allUsers) {
        userList = allUsers;
    }

    /**
     * Called when RecyclerView needs a ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A ViewHolder that holds a a View of the given view type.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AdminProfilesFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    /**
     * Called by RecyclerView to display data at specified position
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Set user profile name
        holder.EventForView = userList.get(position);
        holder.userName.setText(holder.EventForView.getUserName());

        String imageURL = holder.EventForView.getUserProfileImage();
        holder.EventForView = userList.get(position);

        // Set user name on image when user profile image is empty
        if(holder.EventForView.getUserName().equals("") || holder.EventForView.getUserName().equals(" ")) {
            holder.userName.setText(holder.EventForView.getDeviceId());
        }
        else {
            holder.userName.setText(holder.EventForView.getUserName());
        }

        // when there is name is not empty and image is empty
        if (!Objects.equals(holder.EventForView.getUserName(), "") &&(Objects.equals(holder.profileImage.getBackground(),null))&& ((Objects.equals(imageURL, ""))||(Objects.equals(imageURL, null)))){
            holder.nameOnImage.setText(holder.EventForView.getUserName());
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.profileImage.setBackgroundColor(color);
        }

        // Set user profile image with Glide
        Glide.with(holder.itemView.getContext())
                .load(imageURL)
                .into(holder.profileImage);

        // As a admin, want to remove profiles
        // Remove user profile when delete button is clicked
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the position of the user in the adapter position
                int userPosition = holder.getAbsoluteAdapterPosition();

                if (userPosition != RecyclerView.NO_POSITION){
                    User deleteProfile = userList.get(userPosition);
                    // Update user profile fields to empty in Firebase
                    db.collection("users").document(deleteProfile.getDeviceId())
                            .update("userEmail","",
                                    "userHomepage","",
                                    "userName","",
                                    "userPhoneNumber","",
                                    "userProfileImage","");

                    userList.remove(deleteProfile);
                    holder.nameOnImage.setText("");
                    holder.nameOnImage.setBackgroundColor(255);
                    notifyItemRemoved(userPosition);
                }
            }
        });
    }

    /**
     * Returns the total number of items in each set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return userList.size();
    }

    /**
     * This ViewHolder class holds the views for each item in the RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Fields of the holders we want to view
        public final ImageView profileImage;
        public final ImageButton deleteButton;
        public final TextView nameOnImage;
        public final TextView userName;
        public User EventForView;


        /**
         * Constructs new ViewHolder.
         *
         * @param binding Binds all the views
         */
        public ViewHolder(AdminProfilesFragmentBinding binding) {
            super(binding.getRoot());
            profileImage = binding.adminProfileIcon;
            deleteButton = binding.adminDeletebtnProfiles;
            userName = binding.adminProfiles;
            nameOnImage = binding.nameOnImage;
        }
    }
}