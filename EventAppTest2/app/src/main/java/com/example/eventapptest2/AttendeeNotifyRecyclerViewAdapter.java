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
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AttendeeNotifyRecyclerViewAdapter extends RecyclerView.Adapter<AttendeeNotifyRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<String> AttendeeList;

    public AttendeeNotifyRecyclerViewAdapter(ArrayList<String> Attendees) {
        AttendeeList = Attendees;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AttendeeNotificationFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //setting events text
        int postini = position;

        System.out.println("testtttttttttt " + AttendeeList.get(postini));
        holder.userName.setText(AttendeeList.get(postini));


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
        public final ImageView Imageing;
        public String EventForView;



        public ViewHolder(AttendeeNotificationFragmentBinding binding) {
            super(binding.getRoot());
            userName = binding.attendeeEventNotify;
            Imageing = binding.attendeeEventIcon;


        }


        @Override
        public String toString() {
            return super.toString() + " '" + userName.getText() + "'";
        }
    }
}