package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.AdminImageFragmentBinding;
import com.example.eventapptest2.databinding.AdminProfilesFragmentBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdminProfileRecyclerViewAdapter extends RecyclerView.Adapter<AdminProfileRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<User> userList;

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
//        int postini = position;
//
//        System.out.println("testtttttttttt " + allusers.get(postini));
//        holder.userName.setText(allusers.get(postini));
//        holder.EventForView = userList.get(position);
        holder.EventForView = userList.get(position);
        holder.userName.setText(holder.EventForView.getUserName());
        String imageURL = holder.EventForView.getUserProfileImage();

        Glide.with(holder.itemView.getContext())
                .load(imageURL)
                .into(holder.Imageing);
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        
        public final ImageView Imageing;
        public final ImageView deletebut;

        public final TextView userName;
        public User EventForView;



        public ViewHolder(AdminProfilesFragmentBinding binding) {
            super(binding.getRoot());
            Imageing = binding.adminProfileIcon;
            deletebut = binding.adminDeletebtnProfiles;
            userName = binding.adminProfiles;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + userName.getText() + "'";
        }

    }
}