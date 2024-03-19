package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapptest2.databinding.AdminEventsBinding;
import com.example.eventapptest2.databinding.AdminEventsFragmentsBinding;
import com.example.eventapptest2.databinding.AttendeeNotificationFragmentBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdminEventsRecyclerViewAdapter extends RecyclerView.Adapter<AdminEventsRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<Event> allevents;

    public AdminEventsRecyclerViewAdapter(ArrayList<Event> alleventsinput) {
        allevents = alleventsinput;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AdminEventsFragmentsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //setting events text
//        int postini = position;
//
//        System.out.println("testtttttttttt " + allevents.get(postini));
//        holder.userName.setText(allevents.get(postini));


    }


    @Override
    public int getItemCount() {
        return allevents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView Textname;
        public final ImageView Imageing;
        public final ImageView deletebut;
        public Event EventForView;



        public ViewHolder(AdminEventsFragmentsBinding binding) {
            super(binding.getRoot());
            Textname = binding.adminEventsname;
            Imageing = binding.adminEventIcon;
            deletebut = binding.adminDeletebtnEvents;

        }


        @Override
        public String toString() {
            return super.toString() + " '" + Textname.getText() + "'";
        }
    }
}