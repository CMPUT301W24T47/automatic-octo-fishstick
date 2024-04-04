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

/**
 * Display user profiles in a RecyclerView
 * Binds data of the users name and profile image
 */
public class AdminProfileRecyclerViewAdapter extends RecyclerView.Adapter<AdminProfileRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<User> userList;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    // CollectionReference will store the collection of Users
    //  - Which then each user will store different information
    private final CollectionReference usersRef = db.collection("users");

    // Constructor
    // Receives array list of User objects and initializes the adapters data with this data
    public AdminProfileRecyclerViewAdapter(ArrayList<User> allUsers) {
        userList = allUsers;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AdminProfilesFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }


    // bind data to each item in the RecyclerView
    // User name, profile image, and check in
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //setting events text
        holder.EventForView = userList.get(position);
        holder.userName.setText(holder.EventForView.getUserName());
        String imageURL = holder.EventForView.getUserProfileImage();


        holder.EventForView = userList.get(position);
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

        Glide.with(holder.itemView.getContext())
                .load(imageURL)
                .into(holder.profileImage);

        // As a admin, want to remove profiles
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the position of the user in the adapter position
                int userPosition = holder.getAbsoluteAdapterPosition();

                if (userPosition != RecyclerView.NO_POSITION){
                    User deleteProfile = userList.get(userPosition);
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
//
            }
        });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Fields of the holders we want to view
        public final ImageView profileImage;
        public final ImageButton deleteButton;
        public final TextView nameOnImage;

        public final TextView userName;
        public User EventForView;


        // Objects are generated from the admin_profile xml
        public ViewHolder(AdminProfilesFragmentBinding binding) {
            super(binding.getRoot());
            profileImage = binding.adminProfileIcon;
            deleteButton = binding.adminDeletebtnProfiles;
            userName = binding.adminProfiles;
            nameOnImage = binding.nameOnImage;
        }

    }
}