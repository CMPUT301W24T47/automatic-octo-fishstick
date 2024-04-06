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
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AttendeeListRecyclerViewAdapter extends RecyclerView.Adapter<AttendeeListRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<User> AttendeeList;

    public AttendeeListRecyclerViewAdapter(ArrayList<User> Attendees) {
        AttendeeList = Attendees;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AttendeeListFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

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

        holder.EventForView = AttendeeList.get(position);
        if(holder.EventForView.getUserName().equals("") || holder.EventForView.getUserName().equals(" ")) {
            holder.userName.setText(holder.EventForView.getDeviceId());
        }
        else {
            holder.userName.setText(holder.EventForView.getUserName());
        }

        //uncomment for color

        // when there is name is not empty and image is empty
        if (!Objects.equals(holder.EventForView.getUserName(), "") &&(Objects.equals(holder.Imageing.getBackground(),null))&& ((Objects.equals(imageUrl, ""))||(Objects.equals(imageUrl, null)))){
            holder.adminNameOnImage.setText(holder.EventForView.getUserName());
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.Imageing.setBackgroundColor(color);
        }

        // display image url
        //System.out.println("Image URL: " + imageUrl);
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.Imageing);

        //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+holder.EventForView.getCheckInCount());

        if (Integer.parseInt( holder.EventForView.getCheckInCount()) == 0){
            holder.Status.setText("Not Signed-in");
            holder.count.setText("Check-in Count: 0");
        }else{holder.Status.setText("Signed-in");
            holder.count.setText("Check-in Count: "+holder.EventForView.getCheckInCount());}


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

        public final TextView count;
        public final ImageView Imageing;
        public User EventForView;

        public final TextView adminNameOnImage;




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