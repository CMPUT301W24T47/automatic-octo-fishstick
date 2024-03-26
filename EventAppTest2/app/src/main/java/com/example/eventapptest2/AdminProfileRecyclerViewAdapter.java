package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventapptest2.databinding.AdminImageFragmentBinding;
import com.example.eventapptest2.databinding.AdminProfilesFragmentBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdminProfileRecyclerViewAdapter extends RecyclerView.Adapter<AdminProfileRecyclerViewAdapter.ViewHolder> {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference profileRef = db.collection("users");
    private  ArrayList<User> allusers;

    public AdminProfileRecyclerViewAdapter(ArrayList<User> users) {
        allusers = users;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AdminProfilesFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //setting profiles text
        int postini = position;

        holder.profileForView = allusers.get(position);
        if(holder.profileForView.getUserName().equals("") || holder.profileForView.getUserName().equals(" ")) {
            holder.userName.setText(holder.profileForView.getDeviceId());
        }
        else {
            holder.userName.setText(holder.profileForView.getUserName());
        }

        //String imageUrl = "https://firebasestorage.googleapis.com/v0/b/charlie-kim-fans.appspot.com/o/event_images%2F7aa31d9e-1539-49f9-bc21-4ec823cdbfdb?alt=media&token=7afc1c14-11f4-48c7-8ab1-99ee1e96eaa4";
//        Picasso.get().load(holder.EventForView.getUserProfileImage()).into(holder.Imageing);
        String imageUrl = holder.profileForView.getUserProfileImage();
        /////////might wanna delete picasso or glide casue it redundant to display twice

        // display image url
        System.out.println("Image URL: " + imageUrl);
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.Imageing);


        holder.deletebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User delProfile = allusers.get(holder.getAbsoluteAdapterPosition());

                db.collection("users")
                        .document(delProfile.getDeviceId())
                        .update("userName", "",
                                "userEmail", "",
                                "userHomepage", "",
                                "userPhoneNumber", "",
                                "userProfileImage","");


                allusers.remove(delProfile);
                notifyItemRemoved(holder.getAbsoluteAdapterPosition());


            }
        });

    }


    @Override
    public int getItemCount() {
        return allusers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView Imageing;
        public final ImageView deletebut;
        public final TextView userName;
        public User profileForView;



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