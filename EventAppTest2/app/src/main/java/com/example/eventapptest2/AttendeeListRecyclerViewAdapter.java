package com.example.eventapptest2;

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

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AttendeeListRecyclerViewAdapter extends RecyclerView.Adapter<AttendeeListRecyclerViewAdapter.ViewHolder> {
    // ArrayList user objects representing attendees
    private  ArrayList<User> AttendeeList;

    // Constructor initialize adapter with list of attendees
    public AttendeeListRecyclerViewAdapter(ArrayList<User> Attendees) {
        AttendeeList = Attendees;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AttendeeListFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    // bind data to each item in the RecyclerView
    // User name, profile image, and check in
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //setting events text
        int postini = position;
        holder.EventForView = AttendeeList.get(position);
        holder.userName.setText(holder.EventForView.getUserName());
        //String imageUrl = "https://firebasestorage.googleapis.com/v0/b/charlie-kim-fans.appspot.com/o/event_images%2F7aa31d9e-1539-49f9-bc21-4ec823cdbfdb?alt=media&token=7afc1c14-11f4-48c7-8ab1-99ee1e96eaa4";
//        Picasso.get().load(holder.EventForView.getUserProfileImage()).into(holder.Imageing);
        String imageUrl = holder.EventForView.getUserProfileImage();
         /////////might wanna delete picasso or glide casue it redundant to display twice

        // display image url
        //System.out.println("Image URL: " + imageUrl);
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.Imageing);

        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+holder.EventForView.getCheckInCount());

        if (Integer.parseInt( holder.EventForView.getCheckInCount()) == 0){
            holder.Status.setText("Not Signed-in");
        }else{holder.Status.setText("Signed-in");}


    }
//    public interface OnExploreButtonClickListener {
//        void onExploreButtonClick();
//    }
//
//
//
//    public void setOnExploreButtonClickListener(OnExploreButtonClickListener listener) {
//        mListener = listener;
//    }

    @Override
    public int getItemCount() {
        return AttendeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView userName;
        public final TextView Status;
        public final ImageView Imageing;
        public User EventForView;



        public ViewHolder(AttendeeListFragmentBinding binding) {
            super(binding.getRoot());
            userName = binding.attendeeNameList;
            Status = binding.CheckText;
            Imageing = binding.ProfilePicList;


        }


        @Override
        public String toString() {
            return super.toString() + " '" + userName.getText() + "'";
        }
    }
}