package com.example.eventapptest2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapptest2.databinding.AdminEventsFragmentsBinding;
import com.example.eventapptest2.databinding.AdminImageFragmentBinding;
import com.example.eventapptest2.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AdminImageRecyclerViewAdapter extends RecyclerView.Adapter<AdminImageRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<Event> allevents;

    public AdminImageRecyclerViewAdapter(ArrayList<Event> alleventsinput) {
        allevents = alleventsinput;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(AdminImageFragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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

        public final ImageView Imageing;
        public final ImageView deletebut;
        public Event EventForView;



        public ViewHolder(AdminImageFragmentBinding binding) {
            super(binding.getRoot());

            Imageing = binding.AdminImageView;
            deletebut = binding.ImageDeleteButton;

        }



    }
}